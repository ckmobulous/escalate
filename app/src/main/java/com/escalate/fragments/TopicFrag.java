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
import com.escalate.activity.TopicDetailAct;
import com.escalate.adapter.TopicSearchAdapter;
import com.escalate.databinding.FragmentTopicBinding;
import com.escalate.model.RecentSBean;
import com.escalate.model.TopSearchResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;


public class TopicFrag extends Fragment {
    private FragmentTopicBinding binding;

    private List<TopSearchResponce.DataBean> topicSearch = null;

    public TopicFrag() {
        // Required empty public constructor
    }

    private TopicSearchAdapter topicSearchAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_topic,container,false);

        topicSearch = new ArrayList<>();
        setRefersh();
        return binding.getRoot();
    }

    public void setRefersh() {
        binding.swipeRefreshTopic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshTopic.setRefreshing(false);
                topicListService();  //in OnCreateView
            }
        });
    }

    public void topicListService() {
        try {
            MyDialog.getInstance(getActivity()).showDialog(getActivity());

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<TopSearchResponce> call = apiInterface.topicRequest();

            call.enqueue(new retrofit2.Callback<TopSearchResponce>() {
                @Override
                public void onResponse(Call<TopSearchResponce> call, Response<TopSearchResponce> response) {
                    MyDialog.getInstance(getActivity()).hideDialog();

                    if (response.isSuccessful()) {
                        TopSearchResponce replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                            topicSearch = replyPostList.getData();
                            recyclerViewTopic(topicSearch);
                        } else {
                            // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
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


    private void recyclerViewTopic(List<TopSearchResponce.DataBean> topProfileSearch) {

        if (topProfileSearch.size() < 1) {
            binding.noDataTxtTopic.setVisibility(View.VISIBLE);
            binding.recViewTopic.setVisibility(View.GONE);
        }
        else {
            binding.noDataTxtTopic.setVisibility(View.GONE);
            binding.recViewTopic.setVisibility(View.VISIBLE);
            topicSearchAdapter = new TopicSearchAdapter(getActivity(), topProfileSearch);

            binding.recViewTopic.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.recViewTopic.setAdapter(topicSearchAdapter);

            topicSearchAdapter.setListener(new TopicSearchAdapter.TopicsListener() {
                @Override
                public void onTopicClick(View v, int pos) {

                    if (topicSearchAdapter.getTopicFilterList() != null && topicSearchAdapter.getTopicFilterList().size() > 0) {


                        TopSearchResponce.DataBean
                                bean = topicSearchAdapter.getTopicFilterList().get(pos);

                        if (bean != null) {

                            bean.setRecent_search_item_type(1);
//                        MyApplication.addSearchDataBean(topicSearchAdapter.getTopicFilterList().get(pos));



                            RecentSBean recentSBean = new RecentSBean();

                            // topic
                            recentSBean.setCommon_name(bean.getTopic_name());
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


                            Intent intentTopic = new Intent(getActivity(), TopicDetailAct.class);
                            intentTopic.putExtra("topic_id",topicSearchAdapter.getTopicFilterList().get(pos).getTopic_id());
                            startActivity(intentTopic);
                        }
                    }
                    }
            });
        }

    }

    public TopicSearchAdapter getTopicSearchAdapter() {
        return topicSearchAdapter;
    }


}
