package com.escalate.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.escalate.R;
import com.escalate.model.TopSearchResponce;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rajesh Bisht [a.bisht.uk@gmail.com] on 9/10/18.
 */
public class RecentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Map<Integer, TopSearchResponce.DataBean> map = null;
    private Context context;
    private LayoutInflater inflater;

    private List<TopSearchResponce.DataBean> list;
    private List<TopSearchResponce.DataBean> filteredList;

    public static final int ITEM_TYPE_PEOPLE = 0;
    public static final int ITEM_TYPE_TOPICS = 1;
    public static final int ITEM_TYPE_TAGS = 3;
    private RecentSListener listener;

    public List<TopSearchResponce.DataBean> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<TopSearchResponce.DataBean> filteredList) {
        this.filteredList = filteredList;
    }

    public RecentSListener getListener() {
        return listener;
    }

    public void setListener(RecentSListener listener) {
        this.listener = listener;
    }

    public RecentAdapter(Context context, List<TopSearchResponce.DataBean> list) {
        this.list = list;
        this.filteredList = list;
        this.context = context;
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        int r = 0;
        if (this.filteredList != null) {
            if (this.filteredList.get(position) != null) {
                int r_s_type = this.filteredList.get(position).getRecent_search_item_type();
                switch (r_s_type) {
                    case 0:
                        r = ITEM_TYPE_PEOPLE;
                        break;

                    case 1:
                        r = ITEM_TYPE_TOPICS;
                        break;

                    case 2:
                        r = 2;
                        break;

                    case 3:
                        r = ITEM_TYPE_TAGS;
                        break;

                    default:
                        r = 0;
                        break;
                }
            }
            return r;
        } else
            return r;

    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case ITEM_TYPE_PEOPLE:

                view = inflater.inflate(R.layout.recycler_people_adapter, parent, false);
                viewHolder = new RPeopleVH(view);

                break;

            case ITEM_TYPE_TOPICS:
                view = inflater.inflate(R.layout.topic_search_adapter, parent, false);
                viewHolder = new RTopicsVH(view);
                break;

            case 2:

                break;

            case ITEM_TYPE_TAGS:
                view = inflater.inflate(R.layout.tag_search_list, parent, false);
                viewHolder = new RTagsVH(view);
                break;

        }

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.filteredList != null) {
            TopSearchResponce.DataBean dataBean = filteredList.get(position);

            if (holder instanceof RPeopleVH) // PEOPLES
            {
                ((RPeopleVH) holder).userNameTxt.setText(dataBean.getFullname());

                ((RPeopleVH) holder).tvTopic.setText(dataBean.getTopic_match() + " topics of interest are common");

                String image = dataBean.getUser_image();
                if (image != null) {
                    if (!image.isEmpty()) {
                        Picasso.get()
                                .load(image)
                                .resize(100, 100)
                                .centerCrop(Gravity.CENTER)
                                .into(((RPeopleVH) holder).imgViewPeople);
                    }
                } else {
                    ((RPeopleVH) holder).imgViewPeople.setImageResource(R.drawable.default_image);
                }

                //status
                String followStatus = dataBean.getFollower_flag();

                if (followStatus != null && !followStatus.isEmpty()) {
                    boolean s;
                    s = "0".equalsIgnoreCase(followStatus);
                    if (s) {
                        ((RPeopleVH) holder).followPeopleLay.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_gradient));
                        ((RPeopleVH) holder).txtFollowPeople.setText("Follow");
                    } else {
                        ((RPeopleVH) holder).followPeopleLay.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_follow_green_gradient));
                        ((RPeopleVH) holder).txtFollowPeople.setText("UnFollow");
                    }
                } else {
                    ((RPeopleVH) holder).followPeopleLay.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_gradient));
                    ((RPeopleVH) holder).txtFollowPeople.setText("UnFollow");
                }

                ((RPeopleVH) holder).followPeopleLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (listener != null)
                            listener.onFollowUnfollowClick(v, position);
                    }
                });


                View.OnClickListener userClick = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (listener != null)
                            listener.onPeopleClick(v, position);
                    }
                };

                ((RPeopleVH) holder).imgViewPeople.setOnClickListener(userClick);
                ((RPeopleVH) holder).userNameTxt.setOnClickListener(userClick);

            } else if (holder instanceof RTopicsVH) {   // TOPICS

                ((RTopicsVH) holder).userNameTxt.setText(dataBean.getTopic_name());

                String image = dataBean.getIcon();
                if (image != null) {
                    if (!image.isEmpty()) {
                        Picasso.get()
                                .load(image)
                                .resize(100, 100)
                                .centerCrop(Gravity.CENTER)
                                .into(((RTopicsVH) holder).imgViewTopic);
                    }
                } else {
                    ((RTopicsVH) holder).imgViewTopic.setImageResource(R.drawable.default_image);
                }

                ((RTopicsVH) holder).num_of_postTxt.setText(dataBean.getNum_of_post());

                ((RTopicsVH) holder).topicDetailLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (listener != null)
                            listener.onTopicClick(v, position);
                    }
                });


            } else if (holder instanceof RTagsVH) {   // TAGS


                String split = dataBean.getTag_name().trim();

                if (split.contains(",")) {
                    int s = split.lastIndexOf(",");
                    split = split.substring(0, s);
                }


                ((RTagsVH) holder).tagNameTxt.setText(split);
                ((RTagsVH) holder).noPostTag.setText(dataBean.getNum_of_post());

                ((RTagsVH) holder).tagLay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (listener != null)
                            listener.onTagsClick(v, position);

                    }
                });


            }


        }
    }

    @Override
    public int getItemCount() {

//        if (this.filteredList != null) {
//            int s = this.filteredList.size();
//            if (s > 5) {
//                return 5;
//            } else {
//                return this.filteredList.size();
//            }
//
//        } else {
//            return 0;
//        }

        return this.filteredList != null ? this.filteredList.size() : 0;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = list;
                } else {
                    ArrayList<TopSearchResponce.DataBean> filterList = new ArrayList<>();
                    for (TopSearchResponce.DataBean host : list) {

                        int r_s_type = host.getRecent_search_item_type();
                        switch (r_s_type) {
                            case 0: //ITEM_TYPE_PEOPLE
                                if (host.getFullname().toLowerCase().contains(charString.toLowerCase())) {
                                    filterList.add(host);
                                }
                                break;

                            case 1: //ITEM_TYPE_TOPICS
                                if (host.getTopic_name().toLowerCase().contains(charString.toLowerCase())) {
                                    filterList.add(host);
                                }
                                break;

                            case 2:

                                break;

                            case 3: //ITEM_TYPE_TAGS
                                if (host.getTag_name().toLowerCase().contains(charString.toLowerCase())) {
                                    filterList.add(host);
                                }
                                break;

                            default:

                                break;
                        }

                    }
                    filteredList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<TopSearchResponce.DataBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public void removeItem(int pos) {
        if (filteredList != null && filteredList.size() > 0) {
            try {
                filteredList.remove(pos);
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    class RPeopleVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_nameTopic)
        TextView userNameTxt;
        @BindView(R.id.txtFollowPeople)
        TextView txtFollowPeople;
        @BindView(R.id.imgViewPeople)
        RoundedImageView imgViewPeople;
        @BindView(R.id.detailLay)
        LinearLayout detailLay;
        @BindView(R.id.tvTopic)
        TextView tvTopic;
        @BindView(R.id.followPeopleLay)
        LinearLayout followPeopleLay;

        //        same as PeopleSearchAdapter.MyViewHolder
        public RPeopleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class RTopicsVH extends RecyclerView.ViewHolder {

        @BindView(R.id.topic_nameTxt)
        TextView userNameTxt;
        @BindView(R.id.imgViewTopic)
        ImageView imgViewTopic;
        @BindView(R.id.num_of_postTxt)
        TextView num_of_postTxt;
        @BindView(R.id.topicDetailLay)
        LinearLayout topicDetailLay;


        //        same as TopicSearchAdapter.MyViewHolder
        public RTopicsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    class RTagsVH extends RecyclerView.ViewHolder {

        @BindView(R.id.tagNameTxt)
        TextView tagNameTxt;
        @BindView(R.id.noPostTag)
        TextView noPostTag;
        @BindView(R.id.tagLay)
        RelativeLayout tagLay;

        //        same as TagSearchAdapter.MyViewHolder
        public RTagsVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecentSListener {

        void onPeopleClick(View v, int pos);

        void onFollowUnfollowClick(View v, int pos);

        void onTopicClick(View v, int pos);

        void onTagsClick(View v, int pos);
    }

}
