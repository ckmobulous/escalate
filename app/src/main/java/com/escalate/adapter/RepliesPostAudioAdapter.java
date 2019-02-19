package com.escalate.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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
import com.escalate.activity.ReplyActivity;
import com.escalate.databinding.SingleRowPostsBinding;
import com.escalate.databinding.SingleRowRepliesPostBinding;
import com.escalate.fragments.UserProfileFragment;
import com.escalate.model.PostListResponce;
import com.escalate.model.ReplyPostList;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by droid2 on 08/03/2018.
 */
public class RepliesPostAudioAdapter extends RecyclerView.Adapter<RepliesPostAudioAdapter.ViewHolder> {

//    private MediaPlayer mediaPlayer;
//    private int playingPosition;
//    private RepliesPostAudioAdapter playingHolder;
//    private Handler uiUpdateHandler;



    private Context context;
    private List<ReplyPostList.DataBean> postList;
    private LayoutInflater layoutInflater;

    private ProfilePostsAdapter.ProfileInterface listener;
    private HashTagHelper mHashTagHelper;


    private int playingPosition;
    private String playingPostDuration = "00:00";
    private ViewHolder playingCurrentHolder;

    private ReplyPostPlayingListener replyPostPlayingListener;

    private ReplyActivity.UserProfileBioListener profileBioListener;


    public ReplyPostPlayingListener getReplyPostPlayingListener() {
        return replyPostPlayingListener;
    }

    public void setReplyPostPlayingListener(ReplyPostPlayingListener replyPostPlayingListener) {
        this.replyPostPlayingListener = replyPostPlayingListener;
    }

    public ReplyActivity.UserProfileBioListener getProfileBioListener() {
        return profileBioListener;
    }

    public void setProfileBioListener(ReplyActivity.UserProfileBioListener profileBioListener) {
        this.profileBioListener = profileBioListener;
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

    //SIRI VIEW

    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;

    //CONTRUCTOR
    public RepliesPostAudioAdapter( Context context, List<ReplyPostList.DataBean> postList) {
        this.postList = postList;
        this.context = context;
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

        SingleRowRepliesPostBinding binding =
                DataBindingUtil.inflate(layoutInflater,
                        R.layout.single_row_replies_post, parent, false);


        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        if (postList != null) {
            final ReplyPostList.DataBean dataBean = postList.get(position);


            String full_name = dataBean.getFullname();
            String user_id_name = dataBean.getUsername();
            String desc = dataBean.getDescription();
            final String duration = dataBean.getMsg_duration();
            String image = dataBean.getUser_image();

            //AUDIO
            final String audioUrl = dataBean.getMessage();
            

            holder.binding.tvName.setText(full_name);
            holder.binding.tvCategoryHash.setText(user_id_name);
            holder.binding.txtDescription.setText(desc);
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

    public void resetAllOtherPrevious() {
        try {

            for (int i = 0; i < postList.size(); i++) {
                ReplyPostList.DataBean dataBean = postList.get(i);
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


            if (replyPostPlayingListener != null)
                replyPostPlayingListener.onPostPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setEnableAll() {
        for (int i = 0; i < postList.size(); i++) {
            ReplyPostList.DataBean dataBean = postList.get(i);
            dataBean.setPlayPauseEnable(true);
        }

    }

    public void setDisableAll() {
        for (int i = 0; i < postList.size(); i++) {
            ReplyPostList.DataBean dataBean = postList.get(i);
            dataBean.setPlayPauseEnable(false);
        }
    }

    public void removeItem(int pos) {
        if (postList != null && postList.size() > 0) {
            postList.remove(pos);
            notifyDataSetChanged();
        }
    }



    public List<ReplyPostList.DataBean> getArrayList() {
        return postList;
    }

    public void setArrayList(List<ReplyPostList.DataBean> postList) {
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



    //  VIEW HOLDER CLASS
    public class ViewHolder extends RecyclerView.ViewHolder {
//        public ImageView playPauseIv, imgChat, img_play_pause, userIv;
//        public TextView txtFullName, txtCategory, txtDescription, txtLike, txtTimer;
//
        private SingleRowRepliesPostBinding binding;
        public ViewHolder(SingleRowRepliesPostBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

//            ButterKnife.bind(this,itemView);
//
//            txtFullName = itemView.findViewById(R.id.nameTxt);
//            txtCategory = itemView.findViewById(R.id.tvHashTag);
//            userIv = itemView.findViewById(R.id.userIv);
//            txtDescription = itemView.findViewById(R.id.descptnTxt);
//            playPauseIv = itemView.findViewById(R.id.playPauseIv);


        }
    }




    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(ViewHolder holder, ReplyPostList.DataBean dataBean) {
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

                        if (replyPostPlayingListener != null)
                            replyPostPlayingListener.onPostPlaying(false);
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


            if (replyPostPlayingListener != null)
                replyPostPlayingListener.onPostPlaying(false);


            resetSiriView();    //reset SIRI

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
    private void playLastRecordingComments(ViewHolder holder, ReplyPostList.DataBean dataBean) {


        if (comment_mediaPlayer == null) {
            snippetOfPlayLastRecComments(holder, dataBean);
        } else {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;

            snippetOfPlayLastRecComments(holder, dataBean);
        }


        if (replyPostPlayingListener != null)
            replyPostPlayingListener.onPostPlaying(true);

        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT


    }


    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(ViewHolder holder, final ReplyPostList.DataBean dataBean) {

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

                if (replyPostPlayingListener != null)
                    replyPostPlayingListener.onPostPlaying(false);


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
                updaterThread = new SiriUpdateThread(30,
                        siriDView, (ReplyActivity) RepliesPostAudioAdapter.this.context);

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

    public interface ReplyPostPlayingListener {

        void onPostPlaying(boolean isPostPlaying);
    }



//
//
//    private void startMediaPlayer(String audioResId) {
//        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.hangouts_message);
//       /* try {
//            mediaPlayer.setDataSource(audioResId);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }*/
//        mediaPlayer = MediaPlayer.create(context, Uri.parse(audioResId));
//
//
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                releaseMediaPlayer();
//
//            }
//        });
//        mediaPlayer.start();
//    }
//
//
//    private void releaseMediaPlayer() {
////        if (null != playingHolder) {
////            updateNonPlayingView(playingHolder);
////        }
//        mediaPlayer.release();
//        mediaPlayer = null;
//        playingPosition = -1;
//    }
//
////    private void updateNonPlayingView(ViewHolder holder) {
////        if (holder == playingHolder) {
////            uiUpdateHandler.removeMessages(MSG_UPDATE_SEEK_BAR);
////        }
////
////
////    }


}

