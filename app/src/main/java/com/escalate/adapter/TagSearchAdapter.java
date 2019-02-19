package com.escalate.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.escalate.R;
import com.escalate.databinding.TagSearchListBinding;
import com.escalate.model.TopSearchResponce;

import java.util.ArrayList;
import java.util.List;

public class TagSearchAdapter extends RecyclerView.Adapter<TagSearchAdapter.MyViewHolder> implements Filterable {
    private LayoutInflater inflater;
    private Context context;
    List<TopSearchResponce.DataBean> tagList;
    private List<TopSearchResponce.DataBean> tagFilterList;
    private TagAdapterListener listener;

    public List<TopSearchResponce.DataBean> getTagFilterList() {
        return tagFilterList;
    }

    public void setTagFilterList(List<TopSearchResponce.DataBean> tagFilterList) {
        this.tagFilterList = tagFilterList;
    }

    public TagAdapterListener getListener() {
        return listener;
    }

    public void setListener(TagAdapterListener listener) {
        this.listener = listener;
    }

    public TagSearchAdapter(Context context, List<TopSearchResponce.DataBean> topProfileSearch) {
        this.context = context;
        this.tagList = topProfileSearch;
        this.tagFilterList = topProfileSearch;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());
        TagSearchListBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.tag_search_list, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        String split = tagFilterList.get(position).getTag_name().trim();

        if (split.contains(",")) {
            int s = split.lastIndexOf(",");
            split = split.substring(0, s);
        }
//        String three =splitTime[2];
        holder.tagNameTxt.setText(split);
        holder.noPostTag.setText(tagFilterList.get(position).getNum_of_post());

        holder.tagLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null)
                    listener.onTagClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagFilterList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    tagFilterList = tagList;
                } else {
                    ArrayList<TopSearchResponce.DataBean> filterList = new ArrayList<>();


                    for (TopSearchResponce.DataBean host : tagList) {
                        if (host.getTag_name().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(host);
                        }
                    }
                    tagFilterList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = tagFilterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tagFilterList = (ArrayList<TopSearchResponce.DataBean>) filterResults.values;
                notifyDataSetChanged();

//                listenerSearch.search(filterBookModelArrayList);
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TagSearchListBinding binding;
        TextView tagNameTxt;
        TextView noPostTag;
        RelativeLayout tagLay;

        public MyViewHolder(TagSearchListBinding binding) {
            super(binding.getRoot());
            tagNameTxt = (TextView) itemView.findViewById(R.id.tagNameTxt);
            noPostTag = (TextView) itemView.findViewById(R.id.noPostTag);
            tagLay = (RelativeLayout) itemView.findViewById(R.id.tagLay);
        }
    }

    public interface TagAdapterListener {
        void onTagClick(View v, int pos);
    }

}