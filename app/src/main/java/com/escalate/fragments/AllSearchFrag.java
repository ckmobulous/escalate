package com.escalate.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.escalate.R;
import com.escalate.adapter.PeopleSearchAdapter;
import com.escalate.adapter.RecentSearchAdapter;
import com.escalate.adapter.TagSearchAdapter;
import com.escalate.adapter.TopicSearchAdapter;
import com.escalate.model.PostListResponce;
import com.escalate.model.TopSearchResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;


public class AllSearchFrag extends Fragment {
    View view;
    String type = "", itemType = "";
    private int indexing = 0;
    @BindView(R.id.recViewPeople)
    RecyclerView recViewPeople;
    private PeopleSearchAdapter adapter;
    TopicSearchAdapter topicSearchAdapter;
    RecentSearchAdapter recentSearchAdapter;
    TagSearchAdapter tagSearchAdapter;
    private List<TopSearchResponce.DataBean> topProfileSearch;
    private List<PostListResponce.DataBean> postListResponceList;

    public AllSearchFrag() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_people, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null) {
            try {
                type = getArguments().getString("type");
                itemType = getArguments().getString("itemtype");

                indexing = getArguments().getInt("index");


                setUpAllData();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    private void setUpAllData() {
        switch (indexing) {
            case 0:
                //People
                peopleListService();
                break;

            case 1:
                //Topics
                topicListService();
                break;

            case 2:
                //Recent
                recentService();
                break;
            case 3:
                //Tags
                tagsService();
                break;
        }

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
                    MyDialog.getInstance(getActivity()).hideDialog();

                    if (response.isSuccessful()) {
                        TopSearchResponce replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                            topProfileSearch = replyPostList.getData();
//                            recyclerView(topProfileSearch);
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


    /*private void recyclerView(List<TopSearchResponce.DataBean> topProfileSearch) {

        if (adapter == null) {

            adapter = new PeopleSearchAdapter(getActivity(), topProfileSearch);

        }

        recViewPeople.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewPeople.setAdapter(adapter);
    }*/

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
                            topProfileSearch = replyPostList.getData();
//                            recyclerViewTopic(topProfileSearch);
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

   /* private void recyclerViewTopic(List<TopSearchResponce.DataBean> topProfileSearch) {


        if (topicSearchAdapter == null) {

            topicSearchAdapter = new TopicSearchAdapter(getActivity(), topProfileSearch);

        }

        recViewTopic.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewTopic.setAdapter(topicSearchAdapter);
    }*/

    public void recentService() {
        try {
            MyDialog.getInstance(getActivity()).showDialog(getActivity());

            String userid = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.USERID);
            String token = SPreferenceWriter.getInstance(getContext()).getString(SPreferenceKey.TOKEN);

            String url = "postlist" + "/" + userid;

            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<PostListResponce> call = apiInterface.post_listHome(url, token, userid);

            call.enqueue(new retrofit2.Callback<PostListResponce>() {
                @Override
                public void onResponse(Call<PostListResponce> call, Response<PostListResponce> response) {
                    MyDialog.getInstance(getActivity()).hideDialog();

                    if (response.isSuccessful()) {
                        PostListResponce replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                            postListResponceList = replyPostList.getData();
//                            recyclerViewRecent(postListResponceList);
                        } else {
                            // Toast.makeText(this, "" + replyPostList.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PostListResponce> call, Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(getActivity()).hideDialog();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* private void recyclerViewRecent(List<PostListResponce.DataBean> recentSearchList) {


        if (recentSearchAdapter == null) {

            recentSearchAdapter = new RecentSearchAdapter(getActivity(), recentSearchList);

        }

        recViewRecent.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewRecent.setAdapter(recentSearchAdapter);
    }
*/
    public void tagsService() {
        try {
            MyDialog.getInstance(getActivity()).showDialog(getActivity());


            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<TopSearchResponce> call = apiInterface.tagRequest();

            call.enqueue(new retrofit2.Callback<TopSearchResponce>() {
                @Override
                public void onResponse(Call<TopSearchResponce> call, Response<TopSearchResponce> response) {
                    MyDialog.getInstance(getActivity()).hideDialog();

                    if (response.isSuccessful()) {
                        TopSearchResponce replyPostList = response.body();
                        Log.e("response", "" + replyPostList.toString());
                        if (replyPostList.getStatus().equalsIgnoreCase("SUCCESS")) {
                            topProfileSearch = replyPostList.getData();
//                            recyclerViewTag(topProfileSearch);
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

   /* private void recyclerViewTag(List<TopSearchResponce.DataBean> recentSearchList) {

        if (tagSearchAdapter == null) {

            tagSearchAdapter = new TagSearchAdapter(getActivity(), recentSearchList);

        }

        recViewTag.setLayoutManager(new LinearLayoutManager(getContext()));
        recViewTag.setAdapter(tagSearchAdapter);
    }*/

    /*public void getAdapter(int pos) {

        switch (pos) {
            case 0:

                adapter = AllSearchFrag.this.adapter;
                break;

            case 1:

                topicSearchAdapter = AllSearchFrag.this.topicSearchAdapter;
                break;

            case 2:

                recentSearchAdapter = AllSearchFrag.this.recentSearchAdapter;
                break;

            case 3:
                tagSearchAdapter = AllSearchFrag.this.tagSearchAdapter;
                break;

        }


    }*/

    public PeopleSearchAdapter getAllSearchAdapter() {

        return adapter;

    }

    public TopicSearchAdapter getTopicSearchAdapter(){

        return topicSearchAdapter;
    }

    public RecentSearchAdapter getRecentSearchAdapter() {
        return recentSearchAdapter;
    }

    public TagSearchAdapter getTagSearchAdapter() {
        return tagSearchAdapter;
    }


}
