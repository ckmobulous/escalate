package com.escalate.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.escalate.R;
import com.escalate.databinding.RecyclerPeopleAdapterBinding;
import com.escalate.model.TopSearchResponce;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PeopleSearchAdapter extends RecyclerView.Adapter<PeopleSearchAdapter.MyViewHolder> implements Filterable {
    private PeopleAdapterListener peopleAdapterListener;
    private LayoutInflater inflater;

    private Context context;
    private List<TopSearchResponce.DataBean> peopleList;
    private List<TopSearchResponce.DataBean> peopleFilterList;

    public PeopleSearchAdapter(Context context, List<TopSearchResponce.DataBean> peopleList, PeopleAdapterListener peopleAdapterListener) {
        this.context = context;
        this.peopleList = peopleList;
        this.peopleFilterList = peopleList;
        this.peopleAdapterListener = peopleAdapterListener;
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        RecyclerPeopleAdapterBinding binding = DataBindingUtil.inflate(inflater, R.layout.recycler_people_adapter, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final String userid = peopleFilterList.get(position).getUser_id();
        String followStatus = peopleFilterList.get(position).getFollower_flag();

        holder.userNameTxt.setText(peopleFilterList.get(position).getFullname());

        if (peopleFilterList.get(position).getTopic_match().equalsIgnoreCase("0"))
            holder.tvTopic.setVisibility(View.GONE);
        else

            holder.tvTopic.setText(peopleFilterList.get(position).getTopic_match() + " similar interests");
        String image = peopleFilterList.get(position).getUser_image();
        if (image != null) {
            if (!image.isEmpty()) {
                Picasso.get().load(image)
                        .resize(100, 100).centerCrop(Gravity.CENTER).into(holder.imgViewPeople);
            }
        } else {
            holder.imgViewPeople.setImageResource(R.drawable.default_image);
        }

        //status
        if (followStatus != null && !followStatus.isEmpty()) {
            boolean s;
            s = "0".equalsIgnoreCase(followStatus);
            if (s) {
                holder.followPeopleLay.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_gradient));
                holder.txtFollowPeople.setText("Follow");
            } else {
                holder.followPeopleLay.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_follow_green_gradient));
                holder.txtFollowPeople.setText("UnFollow");
            }
        } else {
            holder.followPeopleLay.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_gradient));
            holder.txtFollowPeople.setText("UnFollow");
        }

        holder.followPeopleLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                peopleAdapterListener.followUnFollowService(position, userid);
            }
        });

        holder.imgViewPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                peopleAdapterListener.onUserClick(v, position);
            }
        });

        holder.detailLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                peopleAdapterListener.onUserClick(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return peopleFilterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    peopleFilterList = peopleList;
                } else {
//
                    ArrayList<TopSearchResponce.DataBean> filterList = new ArrayList<>();

                    for (TopSearchResponce.DataBean host : peopleList) {
                        if (host.getFullname().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(host);
                        }

                    }
                    peopleFilterList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = peopleFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                peopleFilterList = (ArrayList<TopSearchResponce.DataBean>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerPeopleAdapterBinding binding;
        TextView userNameTxt;
        TextView txtFollowPeople, tvTopic;
        RoundedImageView imgViewPeople;
        LinearLayout detailLay, followPeopleLay;

        public MyViewHolder(RecyclerPeopleAdapterBinding binding) {
            super(binding.getRoot());

            userNameTxt = (TextView) itemView.findViewById(R.id.tv_nameTopic);
            txtFollowPeople = (TextView) itemView.findViewById(R.id.txtFollowPeople);
            imgViewPeople = (RoundedImageView) itemView.findViewById(R.id.imgViewPeople);
            detailLay = (LinearLayout) itemView.findViewById(R.id.detailLay);
            tvTopic = (TextView) itemView.findViewById(R.id.tvTopic);
            followPeopleLay = (LinearLayout) itemView.findViewById(R.id.followPeopleLay);

        }
    }

    public interface PeopleAdapterListener {

        void followUnFollowService(int position, String audioid);

        void onUserClick(View v, int pos);


    }

    public List<TopSearchResponce.DataBean> getPeopleList() {
        return peopleList;
    }

    public void setPeopleList(List<TopSearchResponce.DataBean> peopleList) {
        this.peopleList = peopleList;
    }


    public List<TopSearchResponce.DataBean> getPeopleFilterList() {
        return peopleFilterList;
    }

    public void setPeopleFilterList(List<TopSearchResponce.DataBean> peopleFilterList) {
        this.peopleFilterList = peopleFilterList;
    }

    public void removeItem(int pos) {
        if (peopleFilterList != null && peopleFilterList.size() > 0) {
            try {
                peopleFilterList.remove(pos);
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}