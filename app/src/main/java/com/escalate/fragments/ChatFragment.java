package com.escalate.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.adapter.ChatHistoryModel;
import com.escalate.adapter.ChatListAdapter;
import com.escalate.databinding.FragmentChatBinding;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ChatFragment extends Fragment {
    FragmentChatBinding binding;
    private ChatListAdapter adapter;

    private List<ChatHistoryModel.DataBean> chatHistoryList;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false);

        chatHistoryService();

        setRefersh();
        return binding.getRoot();

    }

    public void setRefersh() {
        binding.swipeRefreshChatt.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeRefreshChatt.setRefreshing(false);
                chatHistoryService();  //in OnCreateView
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        chatHistoryService();
    }

    public void chatHistoryService() {
        try {
            MyDialog.getInstance(getActivity()).showDialog(getActivity());
            String userid = SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(getActivity()).getString(SPreferenceKey.TOKEN);

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<ChatHistoryModel> call = apiInterface.requestChatpage(userid, token);

            call.enqueue(new retrofit2.Callback<ChatHistoryModel>() {
                @Override
                public void onResponse(@NonNull Call<ChatHistoryModel> call, @NonNull Response<ChatHistoryModel> response) {
                    MyDialog.getInstance(getActivity()).hideDialog();

                    if (response.isSuccessful()) {
                        ChatHistoryModel replyPostList = response.body();
                        if (replyPostList != null) {
                            if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                                chatHistoryList = replyPostList.getData();

                                if (chatHistoryList.size() > 1) {
                                    recyclerView(chatHistoryList);
                                    binding.noDataTxt.setVisibility(View.GONE);
                                    binding.recViewHistory.setVisibility(View.VISIBLE);
                                } else {
                                    binding.noDataTxt.setVisibility(View.VISIBLE);
                                    binding.recViewHistory.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(getActivity(), "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ChatHistoryModel> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getActivity()).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void recyclerView(List<ChatHistoryModel.DataBean> chatHistoryList) {

        if (adapter == null) {

            adapter = new ChatListAdapter(chatHistoryList, getActivity());

        }
        adapter = new ChatListAdapter(chatHistoryList, getActivity());

        binding.recViewHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recViewHistory.setAdapter(adapter);

    }

}
