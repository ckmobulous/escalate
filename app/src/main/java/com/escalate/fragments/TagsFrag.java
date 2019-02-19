package com.escalate.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.escalate.R;
import com.escalate.activity.TagDetailAct;
import com.escalate.adapter.TagSearchAdapter;
import com.escalate.databinding.FragmentTagsBinding;
import com.escalate.model.RecentSBean;
import com.escalate.model.TopSearchResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class TagsFrag extends Fragment {
    private FragmentTagsBinding binding;

    private TagSearchAdapter tagSearchAdapter = null;
    private List<TopSearchResponce.DataBean> topProfileSearch;

    public TagsFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tags, container, false);

        topProfileSearch = new ArrayList<>();
        setRefersh();
        return binding.getRoot();
    }


    public void setRefersh() {
        binding.swipeRefreshTags.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshTags.setRefreshing(false);
                tagsService();  //in OnCreateView
            }
        });
    }

    public void tagsService() {
        try {
            MyDialog.getInstance(getActivity()).showDialog(getActivity());


            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<TopSearchResponce> call = apiInterface.tagRequest();

            call.enqueue(new retrofit2.Callback<TopSearchResponce>() {
                @Override
                public void onResponse(@NonNull Call<TopSearchResponce> call, @NonNull Response<TopSearchResponce> response) {
                    if (response.isSuccessful()) {
                        TopSearchResponce replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                            topProfileSearch = replyPostList.getData();
                            recyclerViewTag(topProfileSearch);
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

    private void recyclerViewTag(List<TopSearchResponce.DataBean> recentSearchList) {

        if (recentSearchList.size() < 1) {
            binding.noDataTxtTags.setVisibility(View.VISIBLE);
        } else {
            tagSearchAdapter = new TagSearchAdapter(getActivity(), recentSearchList);
            binding.noDataTxtTags.setVisibility(View.GONE);
            binding.recViewTags.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recViewTags.setAdapter(tagSearchAdapter);

            tagSearchAdapter.setListener(new TagSearchAdapter.TagAdapterListener() {
                @Override
                public void onTagClick(View v, int pos) {

//                MyApplication.putSearchDataBean("tags",tagSearchAdapter.getTagFilterList().get(pos));
//                MyApplication.putSearchDataBean(3,tagSearchAdapter.getTagFilterList().get(pos));

                    if (tagSearchAdapter.getTagFilterList() != null && tagSearchAdapter.getTagFilterList().size() > 0) {

                        TopSearchResponce.DataBean
                                bean = tagSearchAdapter.getTagFilterList().get(pos);

                        if (bean != null) {
                            bean.setRecent_search_item_type(3);
//                        MyApplication.addSearchDataBean(tagSearchAdapter.getTagFilterList().get(pos));

                            RecentSBean recentSBean = new RecentSBean();
                            // tags
                            recentSBean.setCommon_name(bean.getTag_name());
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

                            Intent intentTag = new Intent(getActivity(), TagDetailAct.class);
                            intentTag.putExtra("tag_id", tagSearchAdapter.getTagFilterList().get(pos).getTag_id());
                            startActivity(intentTag);
                        }
                    }

                }
            });
        }



    }

    public TagSearchAdapter getTagSearchAdapter() {
        return tagSearchAdapter;
    }


}
