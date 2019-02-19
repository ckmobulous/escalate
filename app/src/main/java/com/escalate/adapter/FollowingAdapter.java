package com.escalate.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.escalate.R;
import com.escalate.activity.OtherProfileActivity;
import com.escalate.databinding.RecyclerFollowerAdapterBinding;
import com.escalate.fragments.UserProfileFragment;
import com.escalate.model.FollowReponce;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by droid2 on 08/03/2018.
 */
public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.ViewHolder> {

    private FollowingAdapterListener followingAdapterListener;
    private List<FollowReponce.DataBean> followingList;
    private String userLoginId = "";

    private Context context;
    private LayoutInflater layoutInflater;

    private int playingPosition;
    private ViewHolder playingCurrentHolder;

    private FFUserBioPlayingListener ffUserBioPlayingListener;

    private UserProfileFragment.UserProfileBioListener profileBioListener;
    private OtherProfileActivity.UserProfileBioListener otherBioListener;

    //getter , setter
    public FFUserBioPlayingListener getFfUserBioPlayingListener() {
        return ffUserBioPlayingListener;
    }

    public void setFfUserBioPlayingListener(FFUserBioPlayingListener ffUserBioPlayingListener) {
        this.ffUserBioPlayingListener = ffUserBioPlayingListener;
    }


    public UserProfileFragment.UserProfileBioListener getProfileBioListener() {
        return profileBioListener;
    }

    public void setProfileBioListener(UserProfileFragment.UserProfileBioListener profileBioListener) {
        this.profileBioListener = profileBioListener;
    }

    public OtherProfileActivity.UserProfileBioListener getOtherBioListener() {
        return otherBioListener;
    }

    public void setOtherBioListener(OtherProfileActivity.UserProfileBioListener otherBioListener) {
        this.otherBioListener = otherBioListener;
    }

    //COMMENTS
    TextView tv_comment_timer;

    private String comment_audioFilePath = null;
    private File comment_audioFile = null;
    private MediaRecorder comment_mediaRecorder = null;
    private MediaPlayer comment_mediaPlayer = null;
    private long comment_startTime = 0L;

    private boolean isCommentRecording = false;
    private boolean isCommentPlayCheck = false;
    private static final int REQUEST_CODE_ASK_ALL_PERMISSIONS = 1010;
    private Handler comment_handler = null;

    private long comment_timeInMilliseconds = 0L;
    private long comment_timeSwapBuff = 0L;
    private long comment_updatedTime = 0L;
    private int comment_min, comment_sec, comment_milliseconds;

    private CountDownTimer comment_timer = null;
    private long comment_elapsedTime = 0L;
    private String comment_convertedTime = "";

    //CONSTRUCTOR
    public FollowingAdapter(Context context, List<FollowReponce.DataBean> followingList, FollowingAdapterListener followingAdapterListener1) {
        this.followingList = followingList;
        this.context = context;
        this.followingAdapterListener = followingAdapterListener1;
        this.userLoginId = SPreferenceWriter.getInstance(context).getString(SPreferenceKey.USERID);

        this.playingPosition = -1;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        RecyclerFollowerAdapterBinding binding =
                DataBindingUtil.inflate(layoutInflater,
                        R.layout.recycler_follower_adapter, parent, false);

        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (followingList != null) {

            final FollowReponce.DataBean dataBean = followingList.get(position);
            final String userid = dataBean.getUser_id();
            //AUDIO
            final String audioUrl = dataBean.getBio();

            String f_name = dataBean.getFullname();
            String image = dataBean.getUser_image();
            String followingStatus = followingList.get(position).getFollower_flag();

            //LOGIN USER CHECK
            if (userid != null && !userid.isEmpty()) {
                if (userLoginId.equalsIgnoreCase(userid)) {
                    holder.binding.linearFollowBack.setVisibility(View.GONE);

                } else {
                    holder.binding.linearFollowBack.setVisibility(View.VISIBLE);
                }
            } else {
                holder.binding.linearFollowBack.setVisibility(View.GONE);
            }

            //NAME ,IMAGE
            holder.binding.tvName.setText(f_name);
            if (image != null && !image.isEmpty()) {
                setProfilePic(holder, image, holder.binding.imgUser);
            } else {
                holder.binding.progressBar.setVisibility(View.GONE);
                holder.binding.imgUser.setImageResource(R.drawable.default_image);
            }

            //status
            if (followingStatus != null && !followingStatus.isEmpty()) {
                boolean s;
                s = "0".equalsIgnoreCase(followingStatus);
                if (s) {
                    holder.binding.linearFollowBack.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_gradient));
                    holder.binding.txtFollow.setText("Follow");
                } else {
                    holder.binding.linearFollowBack.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_follow_green_gradient));
                    holder.binding.txtFollow.setText("UnFollow");
                }
            } else {
                holder.binding.linearFollowBack.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_gradient));
                holder.binding.txtFollow.setText("UnFollow");
            }


            holder.binding.linearFollowBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    followingAdapterListener.followingUnFollowService(position, followingList.get(position).getUser_id());
                }
            });

            holder.binding.imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    followingAdapterListener.folowingProfileService(position, followingList.get(position).getUser_id());

                }
            });

            holder.binding.detailLay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    followingAdapterListener.folowingProfileService(position, followingList.get(position).getUser_id());

                }
            });


            holder.binding.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    followingAdapterListener.onUserClick(position,followingList.get(position));

                }
            });

            //AUDIO
            if (position == playingPosition) {
                playingCurrentHolder = holder;
                // this view holder corresponds to the currently playing audio cell
                // update its view to show playing progress
//                updatePlayingView();
            } else {
                // and this one corresponds to non playing
//                updateNonPlayingView(holder);   // in onBindVH
            }

            if (dataBean.isPlaying()) {
                holder.binding.imgVPlayPause.setImageResource(R.drawable.home_pause_a);

            } else {
                holder.binding.imgVPlayPause.setImageResource(R.drawable.home_play_a);
            }

            holder.binding.imgVPlayPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (audioUrl != null && !audioUrl.isEmpty()) {

                        if (dataBean.isPlayPauseEnable()) {

                            if (position != playingPosition) {
                                resetAllOtherPrevious();
                            }

                            comment_audioFilePath = audioUrl;
                            playingPosition = position;
                            playingCurrentHolder = holder;

                            doJustCommentsPlayStopPlay(playingCurrentHolder, dataBean);

                            /*/////////****************/

                        }

                    } else {
                        Toast.makeText(context, "Empty audio", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }


    @Override
    public int getItemCount() {
        return followingList != null ? followingList.size() : 0;
    }


    public List<FollowReponce.DataBean> getArrayList() {

        return followingList;
    }

    public void setArrayList(List<FollowReponce.DataBean> list) {

        this.followingList = list;
        notifyDataSetChanged();
    }


    // HELPER METHODS
    private void setProfilePic(@NonNull final ViewHolder holder,
                               final String imageUri, final ImageView imageView) {

        holder.binding.progressBar.setVisibility(View.VISIBLE);


        Glide.with(context.getApplicationContext())
                .load(imageUri)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(200, 200) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {

                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(resource);

                        holder.binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        holder.binding.progressBar.setVisibility(View.GONE);

                        setProfilePic(holder, imageUri, imageView); //again
                    }
                });
    }


    public void removeItem(int pos) {
        if (followingList != null && followingList.size() > 0) {
            followingList.remove(pos);
            notifyDataSetChanged();
        }
    }

    public void resetAllOtherPrevious() {
        try {

            for (int i = 0; i < followingList.size(); i++) {
                FollowReponce.DataBean dataBean = followingList.get(i);
                dataBean.setPlaying(false);
                dataBean.setPlayPauseEnable(true);
            }

            //COMMENTS IN POST LIST
            if (comment_mediaPlayer != null) {
                comment_mediaPlayer.pause();
                comment_mediaPlayer.stop();
                comment_mediaPlayer.reset();
                comment_mediaPlayer.release();
                comment_mediaPlayer = null;
            }

            if (comment_mediaRecorder != null) {
                comment_mediaRecorder.stop();
                comment_mediaRecorder.reset();
                comment_mediaRecorder.release();
                comment_mediaRecorder = null;
            }

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }


            if (playingCurrentHolder != null && playingCurrentHolder.binding != null) {
                ImageView img_play_pause = playingCurrentHolder.binding.imgVPlayPause;
//                final TextView tv_duration = playingCurrentHolder.binding.txtTimer;

                /////////
                img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.create_memo_play));

//                tv_duration.setText("00:00:00");
//                tv_duration.setText(playingPostDuration);
            }

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }


            if (ffUserBioPlayingListener != null)
                ffUserBioPlayingListener.onUserBioPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setEnableAll() {
        for (int i = 0; i < followingList.size(); i++) {
            FollowReponce.DataBean dataBean = followingList.get(i);
            dataBean.setPlayPauseEnable(true);
        }

    }

    public void setDisableAll() {
        for (int i = 0; i < followingList.size(); i++) {
            FollowReponce.DataBean dataBean = followingList.get(i);
            dataBean.setPlayPauseEnable(false);
        }
    }


    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(ViewHolder holder, FollowReponce.DataBean dataBean) {
        ImageView img_play_pause = holder.binding.imgVPlayPause;
//        final TextView tv_duration = holder.binding.txtTimer;

        if (!dataBean.isPlaying()) {

            playLastRecordingComments(holder, dataBean);

            try {
                final int duration = comment_mediaPlayer.getDuration();

                if (comment_timer != null) {
                    comment_timer.cancel();
                    comment_timer = null;
                }

                comment_timer = new CountDownTimer(duration, 1000) {

                    public void onTick(long millisUntilFinished) {
                        comment_elapsedTime = millisUntilFinished;
//                        tv_duration.setText(convertSecondsToHMmSs(millisUntilFinished));

                    }

                    public void onFinish() {

//                        tv_duration.setText("00:00:00");
//                        tv_duration.setText(playingPostDuration);


                        if (ffUserBioPlayingListener != null)
                            ffUserBioPlayingListener.onUserBioPlaying(false);
                    }
                }.start();


            } catch (Exception e) {
                e.printStackTrace();
            }

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.create_memo_pause));

            dataBean.setPlaying(true);


        } else {

            if (comment_mediaPlayer != null)
                comment_mediaPlayer.pause();

            img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.create_memo_play));

            dataBean.setPlaying(false);


//            tv_duration.setText("00:00:00");
//            tv_duration.setText(playingPostDuration);

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }


            if (ffUserBioPlayingListener != null)
                ffUserBioPlayingListener.onUserBioPlaying(false);


            resetAllOtherPrevious();

        }
    }

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d",  minutes, seconds);
    }


    //        PLAY LAST RECORDING
    private void playLastRecordingComments(ViewHolder holder, FollowReponce.DataBean dataBean) {


        if (comment_mediaPlayer == null) {
            snippetOfPlayLastRecComments(holder, dataBean);
        } else {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;

            snippetOfPlayLastRecComments(holder, dataBean);
        }


        if (ffUserBioPlayingListener != null)
            ffUserBioPlayingListener.onUserBioPlaying(true);


    }


    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(ViewHolder holder, final FollowReponce.DataBean dataBean) {

        final ImageView img_play_pause = holder.binding.imgVPlayPause;
        comment_mediaPlayer = new MediaPlayer();

        try {
            comment_mediaPlayer.setDataSource(comment_audioFilePath);
            comment_mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        comment_mediaPlayer.start();

        comment_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                comment_mediaPlayer.stop();
                img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.create_memo_play));

                dataBean.setPlaying(false);

                if (ffUserBioPlayingListener != null)
                    ffUserBioPlayingListener.onUserBioPlaying(false);


                resetAllOtherPrevious();

            }
        });


    }


    //VIEW HOLDER CLASS
    public class ViewHolder extends RecyclerView.ViewHolder {

        //        private RoundedImageView roundedImageView;
//        private TextView txtFullname, txtFollow;
//        private LinearLayout linearFollowBack;
//
        private RecyclerFollowerAdapterBinding binding;

        public ViewHolder(RecyclerFollowerAdapterBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
//
//            txtFullname = itemView.findViewById(R.id.tv_name);
//            txtFollow = itemView.findViewById(R.id.txt_follow);
//            roundedImageView = itemView.findViewById(R.id.img_user);
//            linearFollowBack = itemView.findViewById(R.id.linear_follow_back);
        }
    }


    public interface FollowingAdapterListener {

        void followingUnFollowService(int position, String audioid);

        void folowingProfileService(int position, String audioid);

        void onUserClick(int position,FollowReponce.DataBean data);


    }

    public interface FFUserBioPlayingListener {

        void onUserBioPlaying(boolean isUserBioPlaying);
    }

}

