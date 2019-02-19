package com.escalate.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import com.escalate.activity.ChatActivity;
import com.escalate.databinding.RecyclerChatListAdapterBinding;

import java.util.List;

/**
 * Created by droid2 on 08/03/2018.
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private final List<ChatHistoryModel.DataBean> chatHistoryList;
    private Context context;
    private LayoutInflater inflater;

    public ChatListAdapter(List<ChatHistoryModel.DataBean> chatHistoryList, Context context) {
        this.chatHistoryList = chatHistoryList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        RecyclerChatListAdapterBinding binding =

                DataBindingUtil.inflate(inflater, R.layout.recycler_chat_list_adapter, parent, false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        holder.binding.nameTxtChat.setText(chatHistoryList.get(position).getFullname());
        holder.binding.tvTime.setText(chatHistoryList.get(position).getFftime());
        String image = chatHistoryList.get(position).getImage();
        if (image != null && !image.isEmpty()) {
            setProfilePic(holder, image, holder.binding.imgUser);
        } else {
            holder.binding.imgUser.setImageResource(R.drawable.default_image);
        }

        holder.binding.detailLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChat =new Intent(context, ChatActivity.class);
                intentChat.putExtra("user_img",image);
                intentChat.putExtra("firbase_chat_user_id","123546");
                intentChat.putExtra("user_name",chatHistoryList.get(position).getFullname());
                intentChat.putExtra("user_id",chatHistoryList.get(position).getSender_id());
                context.startActivity(intentChat);
            }
        });


    }

    @Override
    public int getItemCount() {
        return chatHistoryList != null ? chatHistoryList.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerChatListAdapterBinding binding;

        ViewHolder(RecyclerChatListAdapterBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }

    // HELPER METHODS
    private void setProfilePic(@NonNull final ChatListAdapter.ViewHolder holder,
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

