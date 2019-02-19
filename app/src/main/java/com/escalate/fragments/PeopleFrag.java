package com.escalate.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.escalate.R;
import com.escalate.activity.OtherProfileActivity;
import com.escalate.adapter.PeopleSearchAdapter;
import com.escalate.databinding.FragmentPeopleBinding;
import com.escalate.model.RecentSBean;
import com.escalate.model.SampleResponce;
import com.escalate.model.TopSearchResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.MyApplication;
import com.escalate.utils.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class PeopleFrag extends Fragment {
     private FragmentPeopleBinding binding;
    private PeopleSearchAdapter adapter;
    private List<TopSearchResponce.DataBean> topProfileSearch = null;

    public PeopleFrag() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_people, container, false);

        topProfileSearch = new ArrayList<>();

        setRefersh();

        return binding.getRoot();
    }


    public  void setRefersh(){
        binding.swipeRefreshPeople.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshPeople.setRefreshing(false);
                if (Util.showInternetAlert(getContext())) {
                    peopleListService();  //in OnCreateView
                }
            }
        });
    }

    public void peopleListService() {
        try {
            MyDialog.getInstance(getActivity()).showDialog(getActivity());

            String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<TopSearchResponce> call = apiInterface.top_profile_search(userid);

            call.enqueue(new retrofit2.Callback<TopSearchResponce>() {
                @Override
                public void onResponse(@NonNull Call<TopSearchResponce> call, Response<TopSearchResponce> response) {
                    if (response.isSuccessful()) {
                        TopSearchResponce replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                            topProfileSearch = replyPostList.getData();

                            recyclerView(topProfileSearch);

                        } else {
                            // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    MyDialog.getInstance(getActivity()).hideDialog();
                }

                @Override
                public void onFailure(Call<TopSearchResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getActivity()).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void recyclerView(List<TopSearchResponce.DataBean> topProfileSearch) {

        final String userLoginId =
                SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.USERID);
        if (adapter == null) {

            adapter = new PeopleSearchAdapter(getContext(), topProfileSearch, new PeopleSearchAdapter.PeopleAdapterListener()
            {
                @Override
                public void followUnFollowService(int position, String audioid) {

                    if (adapter.getPeopleFilterList() != null
                            && adapter.getPeopleFilterList().size() > 0) {
                        String followerId = audioid;
                        fUnfServiceInFollowers(position, followerId); //SERVICE IN FOLLOWERS
                    }
                }

                @Override
                public void onUserClick(View v, int pos) {
                    if (adapter.getPeopleFilterList() != null && adapter.getPeopleFilterList().size() > 0) {
                        TopSearchResponce.DataBean
                                bean = adapter.getPeopleFilterList().get(pos);

                        if (bean != null) {

//                            MyApplication.putSearchDataBean("people",bean);
                            bean.setRecent_search_item_type(0);
//                            MyApplication.addSearchDataBean(bean);
                            RecentSBean recentSBean = new RecentSBean();
                            // people
                            recentSBean.setCommon_name(bean.getFullname());
                            recentSBean.setRecent_search_item_type(bean.getRecent_search_item_type());
                            recentSBean.setTag_name(bean.getTag_name());
                            recentSBean.setTag_id(bean.getTag_id());
                            recentSBean.setTopic_id(bean.getTopic_id());
                            recentSBean.setTopic_match(bean.getTopic_match());
                            recentSBean.setTopic_name(bean.getTopic_name());
                            recentSBean.setFullname(bean.getFullname());
                            recentSBean.setUser_name(bean.getUser_name());
                            recentSBean.setUser_image(bean.getUser_image());
                            recentSBean.setUser_id(bean.getUser_id());
                            recentSBean.setFollower_flag(bean.getFollower_flag());
                            recentSBean.setIcon(bean.getIcon());
                            recentSBean.setNum_of_post(bean.getNum_of_post());
                            recentSBean.setNumber_of_post(bean.getNumber_of_post());

                            MyApplication.addRecentSBean(recentSBean);
                            String id = bean.getUser_id();
//                            String post_id = bean.getPost_id();
                            String position = String.valueOf(pos);
                            //LOGIN USER CHECK
                            if (id != null && !id.isEmpty()) {
                                if (userLoginId.equalsIgnoreCase(id)) {
                                    // don't go

                                } else {
                                    //  go
                                    dispatchOtherUserAct(id, position);
                                }
                            } else {
                                //  go
                                dispatchOtherUserAct(id, position);
                            }
                        }
                    }
                }
                });
        }

        binding.recViewPeople.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recViewPeople.setAdapter(adapter);

        }


    public PeopleSearchAdapter getAdapter() {
        return adapter;
    }


    private void fUnfServiceInFollowers(final int position, String followId) {
        try {

            String userId = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);
            MyDialog.getInstance(getContext()).showDialog(getActivity());
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<SampleResponce> call = apiInterface.update_follower(userId, followId, token);

            call.enqueue(new retrofit2.Callback<SampleResponce>() {
                @Override
                public void onResponse(Call<SampleResponce> call, Response<SampleResponce> response) {
                    MyDialog.getInstance(getContext()).hideDialog();
                    if (response.isSuccessful()) {
                        SampleResponce sampleResponce = response.body();

                        //FOLLOWERS
                        if (sampleResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            if (adapter != null) {
                                String status = adapter.getPeopleFilterList().get(position).getFollower_flag();
                                if (status.equalsIgnoreCase("1")) {
                                    status = "0";
                                } else {
                                    status = "1";
                                }

                                TopSearchResponce.DataBean model = adapter.getPeopleFilterList().get(position);
                                model.setFollower_flag(status);
                                adapter.notifyDataSetChanged();

                                if (adapter.getPeopleFilterList() != null
                                        && adapter.getPeopleFilterList().size() == 1) {
                                    TopSearchResponce.DataBean bean = adapter.getPeopleFilterList().get(0);
                                    if (bean.getFollower_flag().equalsIgnoreCase("0")) {
                                        adapter.removeItem(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                /*if (Util.showInternetAlert(getActivity())) {
                                    followingListService();  //again
                                }

                                if (Util.showInternetAlert(getActivity())) {
                                    followerListService();  //again
                                }*/


                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SampleResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getContext()).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dispatchOtherUserAct(String userId, String pos) {
        Intent intent = new Intent(getActivity(), OtherProfileActivity.class);
        intent.putExtra("user_id", userId);
        intent.putExtra("post_id", "");
        intent.putExtra("position", pos);
        startActivity(intent);
    }

}
