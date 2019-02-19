package com.escalate.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
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
import com.escalate.activity.CommentAct;
import com.escalate.databinding.CommentListHomeBinding;
import com.escalate.model.ReplyPostList;
import com.escalate.model.ReplyPostList;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentVHolder> {

    private Context context;
    private List<ReplyPostList.DataBean> postList;
    private MediaRecorder comment_mediaRecorder = null;
    private MediaPlayer comment_mediaPlayer = null;
    private CountDownTimer comment_timer = null;
    private String playingPostDuration="00:00";
    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;
    private CommentVHolder playingCurrentHolder;
    private CommentPlayingListener commentPlayingListener;

    private int playingPosition;
    private String comment_audioFilePath = null;
    private LayoutInflater layoutInflater;
    private long comment_elapsedTime = 0L;

    public CommentAdapter(Context context, List<ReplyPostList.DataBean> postList) {
        this.context = context;
        this.postList =postList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            //binding=DataBindingUtil.bind(itemView);
        }
    }

    @NonNull
    @Override
    public CommentVHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        CommentListHomeBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.comment_list_home, parent, false);

        return new CommentVHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentVHolder holder, int position) {

        if (postList != null) {
            final ReplyPostList.DataBean dataBean = postList.get(position);

//            String full_name = dataBean.getFullname();
//            String user_id_name = dataBean.getUsername();
//            String desc = dataBean.getDescription();
//            final String duration = dataBean.getMsg_duration();
//            String image = dataBean.getUser_image();

            //AUDIO

            String full_name = dataBean.getFullname();
            String tags = dataBean.getDescription();
            String desc = dataBean.getDescription();
//            String like_count = dataBean.getLike_count();
//            String reply_count = dataBean.getReply_count();
            String duration = dataBean.getMsg_duration();
            String image = dataBean.getUser_image();

            //AUDIO
            final String audioUrl = dataBean.getMessage();


//            String t = "";
//            if (tags != null && !tags.isEmpty())
//                t = tags.replaceAll(",", " ");

            holder.binding.tvName.setText(full_name);
            holder.binding.txtDescriptionCmt.setText(desc);
            holder.binding.tvCategoryComnt.setText(tags);
            holder.binding.txtTimerList.setText(duration);

            if (image != null && !image.isEmpty()) {
                setProfilePic(holder, image, holder.binding.imgUserList);
            } else {
                holder.binding.progressBar.setVisibility(View.GONE);
                holder.binding.imgUserList.setImageResource(R.drawable.default_image);
            }

            holder.binding.imgPlayPauseCmtList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dataBean.isPlayPauseEnable()) {

                        if (position != playingPosition) {
                            resetAllOtherPrevious();
                        }

                        comment_audioFilePath = audioUrl;
                        playingPosition = position;
                        playingCurrentHolder = holder;

                        //SIRI
                        siriDView = holder.binding.siriDViewCmt;

                        doJustCommentsPlayStopPlay(playingCurrentHolder, dataBean);

                    }

                 else {
                    Toast.makeText(context, "Empty audio", Toast.LENGTH_SHORT).show();

                }
                }
            });

        }


    }
    @Override
    public int getItemCount() {
        return postList.size();

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

            if(playingCurrentHolder!=null && playingCurrentHolder.binding.imgPlayPauseCmtList!=null) {
                ImageView img_play_pause = playingCurrentHolder.binding.imgPlayPauseCmtList;
                final TextView tv_duration = playingCurrentHolder.binding.txtTimerList;

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


            if (commentPlayingListener != null)
                commentPlayingListener.onPostPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
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
                = ProfilePostsAdapter.SiriUpdateThread.class.getSimpleName();


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
                        if (CommentAdapter.SiriUpdateThread.this.v != null) {
                            CommentAdapter.SiriUpdateThread.this.v.setMaxAmplitude(CommentAdapter.SiriUpdateThread.this.tr);
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

    // HELPER METHODS
    private void setProfilePic(@NonNull final CommentAdapter.CommentVHolder holder,
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

                        setProfilePic(holder, imageUri, imageView);
                    }
                });
    }



    //VIEW HOLDER CLASS
    public class CommentVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private CommentListHomeBinding binding;

        CommentVHolder(CommentListHomeBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

            this.binding.linearViewOther.setOnClickListener(this);
            this.binding.imgUserList.setOnClickListener(this);
            this.binding.tvName.setOnClickListener(this);



        }

        public CommentListHomeBinding getBinding() {
            return binding;
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.linear_view_other:
                case R.id.img_user:
               /* case R.id.tv_name:
                    if (listener != null) {
                        listener.onUserClick(v, getAdapterPosition());
                    }*/
                    break;

                case R.id.img_heart:
                  /*  if (listener != null) {
                        listener.onLikeClick(v, getAdapterPosition());
                    }*/
                    break;


                case R.id.img_comment:
                   /* if (listener != null) {
                        listener.onCommentsClick(v, getAdapterPosition());
                    }*/
                    break;


                case R.id.imgShare:
                    /*if (listener != null) {
                        listener.onShareClick(v, getAdapterPosition());
                    }*/

                    break;


                case R.id.img_reply:
                   /* if (listener != null) {
                        listener.onReplyClick(v, getAdapterPosition());
                    }*/
                    break;


                case R.id.txt_replies:
                   /* if (listener != null) {
                        listener.onRepliesTextClick(v, getAdapterPosition());
                    }*/
                    break;


            }
        }
    }

    public interface CommentPlayingListener {

        void onPostPlaying(boolean isPostPlaying);
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

    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(CommentVHolder holder, ReplyPostList.DataBean dataBean)
    {
        ImageView img_play_pause = holder.binding.imgPlayPauseCmtList;
        final TextView tv_duration = holder.binding.txtTimerList;

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

                        if (commentPlayingListener != null)
                            commentPlayingListener.onPostPlaying(false);
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


            if (commentPlayingListener != null)
                commentPlayingListener.onPostPlaying(false);


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
    private void playLastRecordingComments(CommentVHolder holder, ReplyPostList.DataBean dataBean) {


        if (comment_mediaPlayer == null) {
            snippetOfPlayLastRecComments(holder, dataBean);
        } else {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;

            snippetOfPlayLastRecComments(holder, dataBean);
        }


        if (commentPlayingListener != null)
            commentPlayingListener.onPostPlaying(true);

        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT


    }

    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(CommentVHolder holder,
                                              final ReplyPostList.DataBean dataBean) {

        final ImageView img_play_pause = holder.binding.imgPlayPauseCmtList;
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

                if (commentPlayingListener != null)
                    commentPlayingListener.onPostPlaying(false);


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

        try {
            resetSiriView();

            //SIRI LIKE VIEW
            if (updaterThread == null) {
                if (!isBio) {

                    updaterThread = new SiriUpdateThread(30, siriDView,
                            (CommentAct) CommentAdapter.this.context);
                    updaterThread.setPRog(10000);


                }
            }

            if (theWThread == null) {
                theWThread = new Thread(updaterThread);
                theWThread.start();
            }
        }catch (Exception e)
        {e.printStackTrace();}



    }

    private void resetSiriView() {
        if (updaterThread != null) {
            updaterThread.setPRog(0);
            updaterThread.updatePlayingStatus(0);
            updaterThread = null;

            theWThread = null;
        }
    }

    public List<ReplyPostList.DataBean> getPostList() {
        return postList;
    }

    public void setPostList(List<ReplyPostList.DataBean> postList) {
        this.postList = postList;
    }
}

