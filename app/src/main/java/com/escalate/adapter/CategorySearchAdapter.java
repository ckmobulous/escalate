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

import com.escalate.R;
import com.escalate.databinding.SearchCategoryBinding;
import com.escalate.model.GenerResponce;

import java.util.ArrayList;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.HomeCVHolder> implements Filterable {

    private Context context;
    private LayoutInflater inflater;

    private ArrayList<GenerResponce.DataBean> arrayList;
    private ArrayList<GenerResponce.DataBean> filteredList;

    //    private static final int TYPE_HEADER = 0;
//    private static final int TYPE_ITEM = 1;
    private HomeCVListener listener;
    private String fromWhere;

    public CategorySearchAdapter(Context context, ArrayList<GenerResponce.DataBean> arrayList, String fromWhere) {
        this.context = context;
        this.arrayList = arrayList;
        this.filteredList = arrayList;
        this.fromWhere = fromWhere;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }


    @NonNull
    @Override
    public HomeCVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());

        SearchCategoryBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.search_category, parent, false);

        return new HomeCVHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCVHolder holder, int position) {

        if (filteredList != null) {
            try {
                GenerResponce.DataBean bean = filteredList.get(position);

                String name = bean.getName();
                (holder).getBinding().tvTitle.setText(name);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public int getItemCount() {
        return filteredList != null ? filteredList.size() : 0;
    }



    public ArrayList<GenerResponce.DataBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<GenerResponce.DataBean> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<GenerResponce.DataBean> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(ArrayList<GenerResponce.DataBean> filteredList) {
        this.filteredList = filteredList;
    }

    public HomeCVListener getListener() {
        return listener;
    }

    public void setListener(HomeCVListener listener) {
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredList = arrayList;
                } else {
                    ArrayList<GenerResponce.DataBean> filterList = new ArrayList<>();
                    for (GenerResponce.DataBean host : arrayList) {
                        if (host.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(host);
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
                filteredList = (ArrayList<GenerResponce.DataBean>) filterResults.values;
                notifyDataSetChanged();

                if (filteredList != null && filteredList.size() <= 0) {
                    if (listener != null)
                        listener.onEmptyFilters("No data found.");
                }

//                listenerSearch.search(filterBookModelArrayList);
            }
        };
    }

    //VIEW HOLDER
    class HomeCVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SearchCategoryBinding binding;

        HomeCVHolder(SearchCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.rlMoreP.setOnClickListener(this);
        }

        public SearchCategoryBinding getBinding() {
            return binding;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_moreP:
                    if (listener != null)
                        listener.onTheItemClick(v, getAdapterPosition());
                    break;
            }
        }


    }



    // interface
    public interface HomeCVListener {
        void onTheItemClick(View view, int pos);
        void onEmptyFilters(String data);
    }
}
