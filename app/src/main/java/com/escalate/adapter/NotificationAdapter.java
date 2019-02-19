package com.escalate.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.escalate.R;
import com.escalate.databinding.RecyclerNotificationAdapterBinding;
import com.escalate.fragments.HomeFragment;
import com.escalate.model.NotificationModel;
import com.escalate.model.NotificationModel;
import com.escalate.utils.CircleImageView;

import java.util.List;

/**
 * Created by droid2 on 08/03/2018.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<NotificationModel.DataBean> notificationModelList;
    private Context context;
    private HomeFragment tbdSingleFragment;
    private LayoutInflater inflater;

    public NotificationAdapter(List<NotificationModel.DataBean> notificationModelList, Context context) {
        this.notificationModelList = notificationModelList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        RecyclerNotificationAdapterBinding binding =

                DataBindingUtil.inflate(inflater, R.layout.recycler_notification_adapter,parent,false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {


        viewHolder.binding.tvNotification.setText(notificationModelList.get(position).getMsg());
        viewHolder.binding.tvTime.setText(notificationModelList.get(position).getTime());

        String image = notificationModelList.get(position).getImage();
        if (image != null && !image.isEmpty()) {
            setProfilePic(viewHolder, image, viewHolder.binding.imgUser);
        } else {
            viewHolder.binding.imgUser.setImageResource(R.drawable.default_image);
        }


    }

    @Override
    public int getItemCount() {
        return notificationModelList.size();
        //return  0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerNotificationAdapterBinding binding;

        ViewHolder(RecyclerNotificationAdapterBinding binding) {
            super(binding.getRoot());

            this.binding = binding;


        }
    }

    // HELPER METHODS
    private void setProfilePic(@NonNull final NotificationAdapter.ViewHolder holder,
                               final String imageUri, final ImageView imageView) {



        Glide.with(context.getApplicationContext())
                .load(imageUri)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(200, 200) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(resource);

                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);

                        setProfilePic(holder, imageUri, imageView); //again
                    }
                });
    }

}

