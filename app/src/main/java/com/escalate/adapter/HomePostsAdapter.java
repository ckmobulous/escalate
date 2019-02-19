package com.escalate.adapter;

import android.app.Activity;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.am.siriview.DrawView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.escalate.R;
import com.escalate.activity.HomeActivity;
import com.escalate.databinding.SingleRowHomePostBinding;
import com.escalate.fragments.HomeFragment;
import com.escalate.model.PostListResponce;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.volokh.danylo.hashtaghelper.HashTagHelper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomePostsAdapter extends RecyclerView.Adapter<HomePostsAdapter.HomePVHolder> {

    private Context context;
    private List<PostListResponce.DataBean> postList;
    private LayoutInflater layoutInflater;

    private HomeInterface listener;
    private HashTagHelper mHashTagHelper;

    private int playingPosition;
    private String playingPostDuration = "00:00";
    private HomePVHolder playingCurrentHolder;

    private HomePostPlayingListener homePostPlayingListener;

    private HomeFragment.UserHomeBioListener homeBioListener;

    public HomePostPlayingListener getHomePostPlayingListener() {
        return homePostPlayingListener;
    }

    public void setHomePostPlayingListener(HomePostPlayingListener homePostPlayingListener) {
        this.homePostPlayingListener = homePostPlayingListener;
    }

    public HomeFragment.UserHomeBioListener getHomeBioListener() {
        return homeBioListener;
    }

    public void setHomeBioListener(HomeFragment.UserHomeBioListener homeBioListener) {
        this.homeBioListener = homeBioListener;
    }

    //COMMENTS
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

    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;

    //CONSTRUCTOR
    public HomePostsAdapter(Context context, List<PostListResponce.DataBean> postList) {
        this.context = context;
        this.postList = postList;
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
    public HomePVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        SingleRowHomePostBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.single_row_home_post, parent, false);

        return new HomePVHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final HomePVHolder holder, final int position) {
        if (postList != null) {
            final PostListResponce.DataBean dataBean = postList.get(position);

            String full_name = dataBean.getFullname();
            String tags = dataBean.getTag_list();
            String desc = dataBean.getDescription();
            String like_count = dataBean.getLike_count();
            String like_status = dataBean.getLike_flag();
            String reply_count = dataBean.getReply_count();
            final String duration = dataBean.getDuration();
            String image = dataBean.getUser_image();
            String followFlag = dataBean.getFollow_flag();

            //AUDIO
            final String audioUrl = dataBean.getAudio_url();


            //LIKES
            if (like_count != null && !like_count.isEmpty()) {

                String s;
                if (like_count.equalsIgnoreCase("0")) {
                    s = "0 Likes";
                    holder.binding.txtLike.setVisibility(View.INVISIBLE);
                } else if (("1").equalsIgnoreCase(like_count)) {
                    holder.binding.txtLike.setVisibility(View.VISIBLE);
                    holder.binding.txtLike.setText(like_count + " Like");
                } else {
                    s = like_count + " Likes";
                    holder.binding.txtLike.setText(s);
                    holder.binding.txtLike.setVisibility(View.VISIBLE);
                }

            } else {
                String s;
                s = "0 Like";
                holder.binding.txtLike.setText(s);
                holder.binding.txtLike.setVisibility(View.INVISIBLE);
            }

            if (like_status != null && !like_status.isEmpty()) {
                boolean s;
                s = "0".equalsIgnoreCase(like_status);
                if (s) {
                    holder.binding.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like));
                } else {
                    holder.binding.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like_unselected));
                }
            } else {
                holder.binding.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like));
            }

            //HASH TAGS
            mHashTagHelper = HashTagHelper.Creator
                    .create(ContextCompat.getColor(context, R.color.blue),
                            null);
            mHashTagHelper.handle(holder.binding.tvCategoryHash);

            String t = "";
            if (tags != null && !tags.isEmpty())
                t = tags.replaceAll(",", " ");

            holder.binding.tvName.setText(full_name);
            holder.binding.tvCategoryHash.setText(t);
            holder.binding.txtDescription.setText(desc);

            String rc = "";
            if (reply_count != null && !reply_count.isEmpty()) {
                rc = reply_count + " Replies";

                if (("0").equalsIgnoreCase(reply_count)) {
                    //   holder.binding.imgReply.setVisibility(View.INVISIBLE);
                    holder.binding.txtReplies.setVisibility(View.GONE);
                } else if (("1").equalsIgnoreCase(reply_count)) {

                    holder.binding.txtReplies.setText(reply_count + " Reply");
                } else {
                    //  holder.binding.imgReply.setVisibility(View.VISIBLE);
                    holder.binding.txtReplies.setText(rc);
                }

            }

            holder.binding.txtTimer.setText(duration);


            if (image != null && !image.isEmpty()) {
                setProfilePic(holder, image, holder.binding.imgUser);
            } else {
                holder.binding.progressBar.setVisibility(View.GONE);
                holder.binding.imgUser.setImageResource(R.drawable.default_image);
            }


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
                holder.binding.imgPlayPause.setImageResource(R.drawable.home_pause_a);

            } else {
                holder.binding.imgPlayPause.setImageResource(R.drawable.home_play_a);
            }

            //HIDE FUNC
            if (followFlag.equalsIgnoreCase("0") && !dataBean.getUser_id().equalsIgnoreCase(SPreferenceWriter.getInstance(context).getString(SPreferenceKey.USERID))) {
                holder.binding.hideIv.setVisibility(View.VISIBLE);
            } else {
                holder.binding.hideIv.setVisibility(View.GONE);
            }

            holder.binding.imgPlayPause.setOnClickListener(new View.OnClickListener() {
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
                            playingPostDuration = duration;

                            //SIRI
                            siriDView = holder.binding.siriDView;

                            doJustCommentsPlayStopPlay(playingCurrentHolder, dataBean);

                            /*/////////****************/

//                        if (position == playingPosition) {
//                            // toggle between play/pause of audio
//                            if (comment_mediaPlayer.isPlaying()) {
//                                comment_mediaPlayer.pause();
//                            } else {
//                                comment_mediaPlayer.start();
//                            }
//                        } else {
//                            // start another audio playback
//                            playingPosition = position;
//                            if (comment_mediaPlayer != null) {
//                                if (null != playingCurrentHolder) {
//                                    updateNonPlayingView(playingCurrentHolder);     // in onBindVH Button Click  release
//                                }
//                                comment_mediaPlayer.release();
//                            }
//                            playingCurrentHolder = holder;
//                            startMediaPlayer(audioUrl);
//                        }
//
//                        updatePlayingView();
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

        return postList != null ? postList.size() : 0;
    }


    // HELPER METHODS
    private void setProfilePic(@NonNull final HomePVHolder holder,
                               final String imageUri, final ImageView imageView) {

        holder.binding.progressBar.setVisibility(View.VISIBLE);


        Glide.with(context.getApplicationContext())
                .load(imageUri)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(200, 200) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

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

    public void resetAllOtherPrevious() {
        try {

            for (int i = 0; i < postList.size(); i++) {
                PostListResponce.DataBean dataBean = postList.get(i);
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

            // SIRI RESET
            if (updaterThread != null) {
                updaterThread.setPRog(0);
                updaterThread.updatePlayingStatus(0);   //ALL RESET
                updaterThread = null;

                theWThread = null;
            }
            if (theWThread != null) {
                theWThread = null;
            }

            if (playingCurrentHolder != null && playingCurrentHolder.binding != null) {
                ImageView img_play_pause = playingCurrentHolder.binding.imgPlayPause;
                final TextView tv_duration = playingCurrentHolder.binding.txtTimer;

                /////////
                img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.create_memo_play));

                tv_duration.setText("00:00");
                tv_duration.setText(playingPostDuration);
            }

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }


            if (homePostPlayingListener != null)
                homePostPlayingListener.onPostPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEnableAll() {
        for (int i = 0; i < postList.size(); i++) {
            PostListResponce.DataBean dataBean = postList.get(i);
            dataBean.setPlayPauseEnable(true);
        }
    }

    public void setDisableAll() {
        for (int i = 0; i < postList.size(); i++) {
            PostListResponce.DataBean dataBean = postList.get(i);
            dataBean.setPlayPauseEnable(false);
        }
    }


    public List<PostListResponce.DataBean> getArrayList() {
        return postList;
    }

    public void setArrayList(List<PostListResponce.DataBean> postList) {
        this.postList = postList;
    }


    public SiriUpdateThread getUpdaterThread() {
        return updaterThread;
    }

    public void setUpdaterThread(SiriUpdateThread updaterThread) {
        this.updaterThread = updaterThread;
    }

    public Thread getTheWThread() {
        return theWThread;
    }

    public void setTheWThread(Thread theWThread) {
        this.theWThread = theWThread;
    }

    public HomeInterface getListener() {
        return listener;
    }

    public void setListener(HomeInterface listener) {
        this.listener = listener;
    }

    //VIEW HOLDER CLASS
    public class HomePVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private SingleRowHomePostBinding binding;

        HomePVHolder(SingleRowHomePostBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            this.binding.linearViewOther.setOnClickListener(this);
            this.binding.imgUser.setOnClickListener(this);
            this.binding.tvName.setOnClickListener(this);

            this.binding.imgHeart.setOnClickListener(this);
            this.binding.imgComment.setOnClickListener(this);
            this.binding.imgShare.setOnClickListener(this);
            this.binding.imgReply.setOnClickListener(this);
            this.binding.txtReplies.setOnClickListener(this);
            this.binding.hideIv.setOnClickListener(this);

        }

        public SingleRowHomePostBinding getBinding() {
            return binding;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_view_other:
                case R.id.img_user:
                case R.id.tv_name:
                    if (listener != null) {
                        listener.onUserClick(v, getAdapterPosition());
                    }
                    break;

                case R.id.img_heart:
                    if (listener != null) {
                        listener.onLikeClick(v, getAdapterPosition());
                    }
                    break;

                case R.id.img_comment:
                    if (listener != null) {
                        listener.onCommentsClick(v, getAdapterPosition());
                    }
                    break;


                case R.id.imgShare:
                    if (listener != null) {
                        listener.onShareClick(v, getAdapterPosition());
                    }

                    break;


                case R.id.img_reply:
                    if (listener != null) {
                        listener.onReplyClick(v, getAdapterPosition());
                    }
                    break;


                case R.id.txt_replies:
                    if (listener != null) {
                        listener.onRepliesTextClick(v, getAdapterPosition());
                    }
                    break;

                case R.id.hideIv:
                    if (listener != null) {
                        listener.onHideClick(v, getAdapterPosition());
                    }
                    break;
            }
        }
    }


    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(HomePVHolder holder, PostListResponce.DataBean dataBean) {
        ImageView img_play_pause = holder.binding.imgPlayPause;
        final TextView tv_duration = holder.binding.txtTimer;

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
                        tv_duration.setText(convertSecondsToHMmSs(millisUntilFinished));


                        //SIRI LIKE VIEW
                        startSetUpSiriView(false);   //COMMENT
                    }

                    public void onFinish() {

                        tv_duration.setText("00:00");
                        tv_duration.setText(playingPostDuration);

                        resetSiriView();    //reset SIRI

                        if (homePostPlayingListener != null)
                            homePostPlayingListener.onPostPlaying(false);
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

            tv_duration.setText("00:00");
            tv_duration.setText(playingPostDuration);

            if (comment_timer != null) {
                comment_timer.cancel();
                comment_timer = null;
            }


            if (homePostPlayingListener != null)
                homePostPlayingListener.onPostPlaying(false);


            resetSiriView();    //reset SIRI

            resetAllOtherPrevious();

        }
    }

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }


    //        PLAY LAST RECORDING
    private void playLastRecordingComments(HomePVHolder holder, PostListResponce.DataBean dataBean) {


        if (comment_mediaPlayer == null) {
            snippetOfPlayLastRecComments(holder, dataBean);
        } else {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;

            snippetOfPlayLastRecComments(holder, dataBean);
        }


        if (homePostPlayingListener != null)
            homePostPlayingListener.onPostPlaying(true);

        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT


    }


    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(HomePVHolder holder,
                                              final PostListResponce.DataBean dataBean) {

        final ImageView img_play_pause = holder.binding.imgPlayPause;
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

                if (homePostPlayingListener != null)
                    homePostPlayingListener.onPostPlaying(false);


                resetAllOtherPrevious();

            }
        });


    }


    /*
     * ****************
     * //TODO: SYNC SIRI VIEW
     * ****************
     * */
    private void startSetUpSiriView(boolean isBio) {

        resetSiriView();

        //SIRI LIKE VIEW
        if (updaterThread == null) {
            if (!isBio) {
                updaterThread = new
                        SiriUpdateThread(30, siriDView,
                        (HomeActivity) HomePostsAdapter.this.context);

                updaterThread.setPRog(10000);
            }
        }

        if (theWThread == null) {
            theWThread = new Thread(updaterThread);
            theWThread.start();
        }

    }

    private void resetSiriView() {
        if (updaterThread != null) {
            updaterThread.setPRog(0);
            updaterThread.updatePlayingStatus(0);
            updaterThread = null;

            theWThread = null;
        }
    }


    // Runnable  CLASS SiriUpdateThread
    class SiriUpdateThread implements Runnable {


        int REFRESH_INTERVAL_MS;
        Activity c;
        float tr = 0.0F;
        DrawView v;

        private int isPlayingStatus = -1;
        private final String TAG
                = SiriUpdateThread.class.getSimpleName();


        public SiriUpdateThread(int refreshTime, DrawView v, Activity c) {
            this.v = v;
            this.c = c;
            this.REFRESH_INTERVAL_MS = refreshTime;
            isPlayingStatus = 1;
        }

        public void setPRog(float prog) {
            this.tr = prog;
        }

        private long redraw() {
            long t = System.currentTimeMillis();
            this.display_game();
            return System.currentTimeMillis() - t;
        }

        private void display_game() {
            if (this.c != null) {
                this.c.runOnUiThread(new Runnable() {
                    public void run() {
                        if (SiriUpdateThread.this.v != null) {
                            SiriUpdateThread.this.v.setMaxAmplitude(SiriUpdateThread.this.tr);
                        }
                    }
                });
            }
        }


        @Override
        public void run() {
            while (isPlayingStatus == 1) {
                try {
                    Thread.sleep(Math.max(0L, (long) this.REFRESH_INTERVAL_MS - this.redraw()));
                } catch (Exception var2) {
                    var2.printStackTrace();
                }
            }
        }


        public void updatePlayingStatus(int isPlaying) {
            switch (isPlaying) {
                case 0:
                    isPlayingStatus = 0;

                    break;

                case 1:
                    isPlayingStatus = 1;
                    break;

                case -1:
                    isPlayingStatus = -1;
                    break;
                default:
                    Log.w(TAG, "updatePlayingStatus: not valid");
                    break;
            }

        }


    }

    //INTERFACE AS LISTENERS
    public interface HomeInterface {

        void onUserClick(View v, int pos);

        void onLikeClick(View v, int pos);

        void onCommentsClick(View v, int pos);

        void onShareClick(View v, int pos);

        void onReplyClick(View v, int pos);

        void onRepliesTextClick(View v, int pos);

        void onHideClick(View v, int pos);

    }

    public interface HomePostPlayingListener {

        void onPostPlaying(boolean isPostPlaying);
    }

}
