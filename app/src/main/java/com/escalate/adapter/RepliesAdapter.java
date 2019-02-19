package com.escalate.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.escalate.R;
import com.escalate.fragments.HomeFragment;
import com.escalate.model.FarmerList;
import com.escalate.utils.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by droid2 on 08/03/2018.
 */
public class RepliesAdapter extends RecyclerView.Adapter<RepliesAdapter.ViewHolder> {

    private final List<FarmerList> feedNewsList;
    private Context context;
    private HomeFragment tbdSingleFragment;

    public RepliesAdapter(List<FarmerList> feedNewsList, Context context) {
        this.feedNewsList = feedNewsList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public RepliesAdapter(Context context, List<FarmerList> quizzes, HomeFragment tbdSingleFragment) {
        this.context = context;
        this.feedNewsList = quizzes;
        this.tbdSingleFragment = tbdSingleFragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.recycler_replies_adapter, viewGroup, false);
        //  v.setBackgroundColor(R.color.colorAccent);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

    }

    @Override
    public int getItemCount() {
        return feedNewsList.size();
        //return  0;
    }
}

