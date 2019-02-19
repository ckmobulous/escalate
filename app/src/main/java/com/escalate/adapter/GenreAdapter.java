package com.escalate.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.escalate.R;
import com.escalate.fragments.HomeFragment;
import com.escalate.model.GenerResponce;
import com.escalate.utils.CircleImageView;
import com.escalate.utils.Contains;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by droid2 on 08/03/2018.
 */
public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    private List<GenerResponce.DataBean> list;
    private Context context;

    private GenreAdapterListener genreAdapterListener;
    private ArrayList<GenerResponce.DataBean> checkList = new ArrayList<>();

    public GenreAdapter(Context context, List<GenerResponce.DataBean> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.recycler_gener_adapter, viewGroup, false);
        //  v.setBackgroundColor(R.color.colorAccent);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        if (list != null) {
            final GenerResponce.DataBean dataBean = list.get(position);

            String name = dataBean.getName();
            String image = dataBean.getIcon();
           // String status = dataBean.getStatus();
            boolean isSelected = dataBean.isSelected();

            viewHolder.txtGener.setText(name);
           viewHolder.relative_background.setBackground(ContextCompat.getDrawable(context, Contains.drawerColour[position]));
//            viewHolder.imgGener.setImageDrawable(ContextCompat.getDrawable(context, Contains.drawerGenre[position]));

            if (image != null && !image.isEmpty()) {
                setProfilePic(viewHolder, image, viewHolder.imgGener);
            } else {
               viewHolder.progressBar.setVisibility(View.GONE);
               // viewHolder.imgGener.setImageResource(R.drawable.default_image);
            }
            if(isSelected)
            {
                viewHolder.imgTick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.tick));

            }else {
                viewHolder.imgTick.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.categories_untick));
            }


//
//            if (checkList.contains(dataBean)) {
//                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_tick_yellow));
//                Log.e("onBindViewHolder", " contains: " + checkList);
//
//            } else {
//                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_untick));
//                Log.e("onBindViewHolder", " not contains: " + checkList);
//
//            }

//            viewHolder.relative_background.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (position) {
//                        case 0:
//                        case 4:
//                        case 8:
//                            if (checkList.contains(dataBean)) {
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_untick));
//                                checkList.remove(dataBean);
//                                genreAdapterListener.onItemClick(v, checkList, dataBean);
//                            } else {
//                                checkList.add(dataBean);
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_tick_yellow));
//                                genreAdapterListener.onItemClick(v, checkList, dataBean);
//                            }
//
//                            break;
//
//                        case 1:
//                        case 5:
//                        case 9:
//                            if (checkList.contains(dataBean)) {
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_untick));
//                                checkList.remove(dataBean);
//                                genreAdapterListener.onItemClick(v, checkList, dataBean);
//                            } else {
//                                checkList.add(dataBean);
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_tickpurple));
//                                genreAdapterListener.onItemClick(v, checkList, dataBean);
//                            }
//                            break;
//
//
//                        case 2:
//                        case 6:
//                        case 10:
//                            if (checkList.contains(dataBean)) {
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_untick));
//                                checkList.remove(dataBean);
//                                genreAdapterListener.onItemClick(v, checkList, dataBean);
//                            } else {
//                                checkList.add(dataBean);
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_tickblue));
//                                genreAdapterListener.onItemClick(v, checkList, dataBean);
//                            }
//                            break;
//
//                        case 3:
//                        case 7:
//                        case 11:
//                            if (checkList.contains(dataBean)) {
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_untick));
//                                checkList.remove(dataBean);
//                                genreAdapterListener.onItemClick(v, checkList, dataBean);
//                            } else {
//                                checkList.add(dataBean);
//                                viewHolder.imgTick.setBackground(ContextCompat.getDrawable(context, R.drawable.categories_tickgreen));
//                            }
//                            break;
//
//                    }
//                }
//            });
        }

    }



    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;

    }


    public boolean isAnyOneSelected() {
        boolean r = false;

        for (GenerResponce.DataBean bean :
                list) {
            if (bean.isSelected())
                r = true;
        }

        return r;

    }

    public String buildSelectedItemTopicIdsString() {

        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < list.size(); ++i) {
            GenerResponce.DataBean bean = list.get(i);
            if (bean.isSelected()) {
                if (foundOne) {
                    sb.append(",");
                }
                foundOne = true;

                sb.append(bean.getTopic_id());
            }
        }

        return sb.toString();
    }

    public String buildSelectedItemNamesString() {

        StringBuilder sb = new StringBuilder();
        boolean foundOne = false;

        for (int i = 0; i < list.size(); ++i) {
            GenerResponce.DataBean bean = list.get(i);
            if (bean.isSelected()) {
                if (foundOne) {
                    sb.append(",");
                }
                foundOne = true;

                sb.append(bean.getName());
            }
        }

        return sb.toString();
    }


    public List<GenerResponce.DataBean> getList() {
        return list;
    }

    public void setList(List<GenerResponce.DataBean> list) {
        this.list = list;
    }

    //View holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView circleImageView;
        private RelativeLayout relative_background;
        ImageView imgTick, imgGener;
        TextView txtGener;
        private ProgressBar progressBar;

        ViewHolder(View itemView) {
            super(itemView);

            txtGener = itemView.findViewById(R.id.txt_generic_name);
            relative_background = itemView.findViewById(R.id.relative_background);
            imgGener = itemView.findViewById(R.id.img_generic);
            imgTick = itemView.findViewById(R.id.img_tick);
            progressBar =itemView.findViewById(R.id.progressBar);

            relative_background.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.relative_background:
                    if(genreAdapterListener!=null)
                    {
                        genreAdapterListener.onItemTap(v,getAdapterPosition());
                    }
                    break;

               /* case R.id.linear_view_other:
                    tbdSingleFragment.startOtherUserActvity();
                    break;
                case R.id.img_reply:
                    tbdSingleFragment.startReplyActvity();
                    break;
                case R.id.img_chat:
                    tbdSingleFragment.showAudioMicDialog(context);
                    break;*/
            }
        }
    }


    public void setListener(GenreAdapterListener genreAdapterListener) {
        this.genreAdapterListener = genreAdapterListener;
    }

    // HELPER METHODS
    private void setProfilePic(@NonNull final GenreAdapter.ViewHolder holder,
                               final String imageUri, final ImageView imageView) {

       // holder.imgGener.setVisibility(View.VISIBLE);


        Glide.with(context.getApplicationContext())
                .load(imageUri)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(200, 200) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                       // imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(resource);

                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        holder.progressBar.setVisibility(View.GONE);

                        setProfilePic(holder, imageUri, imageView); //again
                    }
                });
    }

    public interface GenreAdapterListener {

        void onItemTap(View v,int pos);
    }
}

