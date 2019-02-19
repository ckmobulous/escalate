package com.escalate.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.easing.linear.Linear;
import com.escalate.R;
import com.escalate.activity.TopicDetailAct;
import com.escalate.databinding.TopicSearchAdapterBinding;
import com.escalate.model.TopSearchResponce;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopicSearchAdapter extends RecyclerView.Adapter<TopicSearchAdapter.MyViewHolder> implements Filterable {

    private LayoutInflater inflater;

    Context context;
    List<TopSearchResponce.DataBean> topicList;
    List<TopSearchResponce.DataBean> topicFilterList;
    private TopicsListener listener;

    public TopicSearchAdapter(Context context, List<TopSearchResponce.DataBean> topicSearch) {
        this.context = context;
        this.topicList = topicSearch;
        this.topicFilterList = topicSearch;
    }

    public List<TopSearchResponce.DataBean> getTopicFilterList() {
        return topicFilterList;
    }

    public void setTopicFilterList(List<TopSearchResponce.DataBean> topicFilterList) {
        this.topicFilterList = topicFilterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {

            inflater = LayoutInflater.from(parent.getContext());
        }
        TopicSearchAdapterBinding binding = DataBindingUtil.inflate(inflater, R.layout.topic_search_adapter, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.userNameTxt.setText(topicFilterList.get(position).getTopic_name());

        String image = topicFilterList.get(position).getIcon();
        if (image != null) {
            if (!image.isEmpty()) {
                Picasso.get().load(image)
                        .resize(100, 100).centerCrop(Gravity.CENTER).into(holder.imgViewTopic);
            }
        } else {
            holder.imgViewTopic.setImageResource(R.drawable.default_image);
        }

        holder.num_of_postTxt.setText(topicFilterList.get(position).getNum_of_post());

        holder.topicDetailLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null)
                    listener.onTopicClick(v,position);

//                Intent intentTopic = new Intent(context, TopicDetailAct.class);
//                intentTopic.putExtra("topic_id",topicFilterList.get(position).getTopic_id());
//                context.startActivity(intentTopic);
            }
        });


    }

    @Override
    public int getItemCount() {
        return topicFilterList.size();
    }

    public TopicsListener getListener() {
        return listener;
    }

    public void setListener(TopicsListener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    topicFilterList = topicList;
                } else {
//                    List<TopSearchResponce.DataBean> peopleList;
//
                    ArrayList<TopSearchResponce.DataBean> filterList = new ArrayList<>();

                    for (TopSearchResponce.DataBean host : topicList) {
                        if (host.getTopic_name().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(host);
                        }

                    }
                    topicFilterList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = topicFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                topicFilterList = (ArrayList<TopSearchResponce.DataBean>) filterResults.values;
                notifyDataSetChanged();
                }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
           TopicSearchAdapterBinding binding;
        TextView userNameTxt;
        ImageView imgViewTopic;
        TextView num_of_postTxt;
        LinearLayout topicDetailLay;

        public MyViewHolder(TopicSearchAdapterBinding binding) {
            super(binding.getRoot());
            userNameTxt = itemView.findViewById(R.id.topic_nameTxt);
            imgViewTopic = (ImageView) itemView.findViewById(R.id.imgViewTopic);
            num_of_postTxt = (TextView)itemView.findViewById(R.id.num_of_postTxt);
            topicDetailLay = (LinearLayout)itemView.findViewById(R.id.topicDetailLay);
        }
    }

    public interface TopicsListener
    {
        void onTopicClick(View v, int pos);
    }

}
