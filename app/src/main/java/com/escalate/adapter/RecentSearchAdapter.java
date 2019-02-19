package com.escalate.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.siriview.DrawView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.escalate.R;
import com.escalate.activity.HomeActivity;
import com.escalate.fragments.RecentFrag;
import com.escalate.model.PostListResponce;
import com.escalate.model.TopSearchResponce;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.MyViewHolder> implements Filterable {

    private HashTagHelper mHashTagHelper;
    private LayoutInflater inflater;
    private RecentSearchAdapter.RecentInterface listener;
    private Context context;
    RecentSearchAdapter peopleAdapterListener;
    List<PostListResponce.DataBean> recentList;
    List<PostListResponce.DataBean> filterrecentList;
    private int playingPosition;
    private String comment_audioFilePath = null;
    private String playingPostDuration = "00:00";
    private RecentSearchAdapter.MyViewHolder playingCurrentHolder;
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;
    private MediaRecorder comment_mediaRecorder = null;
    private MediaPlayer comment_mediaPlayer = null;
    private CountDownTimer comment_timer = null;
    private long comment_elapsedTime = 0L;

    private RecentPostPlayingListener recentPostPlayingListener;

    private RecentFrag.UserReentBioListener reentBioListener;

    public RecentPostPlayingListener getRecentPostPlayingListener() {
        return recentPostPlayingListener;
    }

    public void setRecentPostPlayingListener(RecentPostPlayingListener recentPostPlayingListener) {
        this.recentPostPlayingListener = recentPostPlayingListener;
    }

    public RecentFrag.UserReentBioListener getReentBioListener() {
        return reentBioListener;
    }

    public void setReentBioListener(RecentFrag.UserReentBioListener reentBioListener) {
        this.reentBioListener = reentBioListener;
    }

    public RecentSearchAdapter(Context context, List<PostListResponce.DataBean> recentList) {
        this.context = context;
        this.recentList = recentList;
        this.filterrecentList =recentList;
        this.playingPosition = -1;
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecentSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_list_home, parent, false);

        if (inflater == null)
            inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.recent_search_list, parent, false);

        return new RecentSearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecentSearchAdapter.MyViewHolder holder, final int position) {

       /* holder.userNameTxt.setText(filterrecentList.get(position).getTopic_name());
        String image = recentList.get(position).getUser_image();
        if (image != null) {
            if (!image.isEmpty()) {
                Picasso.get().load(image)
                        .resize(100, 100).centerCrop(Gravity.CENTER).into(holder.roundedImageView);
            }
        } else {
            holder.roundedImageView.setImageResource(R.drawable.default_image);
        }*/

        if (filterrecentList != null) {
            final PostListResponce.DataBean dataBean = filterrecentList.get(position);


            String full_name = dataBean.getFullname();
            String tags = dataBean.getTag_list();
            String desc = dataBean.getDescription();
            String like_count = dataBean.getLike_count();
            String like_status = dataBean.getLike_flag();
            String reply_count = dataBean.getReply_count();
            final String duration = dataBean.getDuration();
            String image = dataBean.getUser_image();

            //AUDIO
            final String audioUrl = dataBean.getAudio_url();


            //LIKES
            if (like_count != null && !like_count.isEmpty()) {

                String s;
                if (like_count.equalsIgnoreCase("0")) {
                    s = "0 Likes";
                } else {
                    s = like_count + " Likes";
                }
                holder.txtLike.setText(s);
            } else {
                String s;
                s = "0 Likes";
                holder.txtLike.setText(s);
            }

            if (like_status != null && !like_status.isEmpty()) {
                boolean s;
                s = "0".equalsIgnoreCase(like_status);
                if (s) {
                    holder.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like));
                } else {
                    holder.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like_unselected));
                }
            } else {
                holder.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like));
            }


            //HASH TAGS
            mHashTagHelper = HashTagHelper.Creator
                    .create(ContextCompat.getColor(context, R.color.blue),
                            null);
            mHashTagHelper.handle(holder.txtCatHash);

            String t = "";
            if (tags != null && !tags.isEmpty())
                t = tags.replaceAll(",", " ");


            holder.userNameTxt.setText(full_name);
            holder.txtCatHash.setText(t);
            holder.txtDescption.setText(desc);

            String rc = "";
            if (reply_count != null && !reply_count.isEmpty()) {
                rc = reply_count + " replies";

                if (("0").equalsIgnoreCase(reply_count)) {
                    holder.iViewRply.setVisibility(View.INVISIBLE);
                    holder.txtViewRply.setVisibility(View.GONE);
                } else {
                    holder.iViewRply.setVisibility(View.VISIBLE);
                }

            }

            holder.txtViewRply.setText(rc);
            holder.txtTimer.setText(duration);


            if (image != null && !image.isEmpty()) {
                setProfilePic(holder, image, holder.roundedImageView);
            } else {
                holder.progressBar.setVisibility(View.GONE);
                holder.roundedImageView.setImageResource(R.drawable.default_image);
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
                holder.imgPlayPause.setImageResource(R.drawable.home_pause_a);

            } else {
                holder.imgPlayPause.setImageResource(R.drawable.home_play_a);
            }

            holder.imgHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        listener.onLikeClick(v, position);
                    }
                }
            });

            holder.commentIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onCommentsClick(v, position);
                    }
                }
            });

            holder.imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onShareClick(v, position);
                    }

                }
            });

            holder.iViewRply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onReplyClick(v, position);
                    }
                }
            });

            holder.txtViewRply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onRepliesTextClick(v, position);
                    }
                }
            });

            holder.roundedImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onUserClick(v, position);
                    }
                }
            });

            holder.userNameTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onUserClick(v, position);
                    }
                }
            });

            holder.imgPlayPause.setOnClickListener(new View.OnClickListener() {
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
                            siriDView = holder.siriDView;

                            doJustCommentsPlayStopPlay(playingCurrentHolder, dataBean);

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
        return filterrecentList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterrecentList = recentList;
                } else {
//                    List<TopSearchResponce.DataBean> peopleList;
//
                    ArrayList<PostListResponce.DataBean> filterList = new ArrayList<>();


                    for (PostListResponce.DataBean host : recentList) {
                        if (host.getFullname().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(host);
                        }

                    }
                    filterrecentList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterrecentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterrecentList = (ArrayList<PostListResponce.DataBean>) filterResults.values;
                notifyDataSetChanged();

//                listenerSearch.search(filterBookModelArrayList);
            }
        };
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nameTxtRecent)
        TextView userNameTxt;
        @BindView(R.id.txtLike) TextView txtLike;
        @BindView(R.id.tv_category_hash)TextView txtCatHash;
        @BindView(R.id.txt_description)TextView txtDescption;
        @BindView(R.id.txt_replies)TextView txtViewRply;
        @BindView(R.id.txt_timer)TextView txtTimer;
        @BindView(R.id.img_heart)ImageView imgHeart;
        @BindView(R.id.commentIv)ImageView commentIv;
        @BindView(R.id.imgShare)ImageView imgShare;
        @BindView(R.id.img_reply)ImageView iViewRply;
        @BindView(R.id.imgUserRecent)RoundedImageView roundedImageView;
        @BindView(R.id.progressBar)ProgressBar progressBar;
        @BindView(R.id.img_play_pause)ImageView imgPlayPause;
        @BindView(R.id.siriDView)com.am.siriview.DrawView siriDView;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    // HELPER METHODS
    private void setProfilePic(@NonNull final RecentSearchAdapter.MyViewHolder holder,
                               final String imageUri, final ImageView imageView) {

        holder.progressBar.setVisibility(View.VISIBLE);


        Glide.with(context.getApplicationContext())
                .load(imageUri)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(200, 200) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {

                        imageView.setVisibility(View.VISIBLE);
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


    //INTERFACE AS LISTENERS
    public interface RecentInterface {

        void onUserClick(View v, int pos);

        void onLikeClick(View v, int pos);

        void onCommentsClick(View v, int pos);

        void onShareClick(View v, int pos);

        void onReplyClick(View v, int pos);

        void onRepliesTextClick(View v, int pos);

    }

    public interface RecentPostPlayingListener {

        void onPostPlaying(boolean isPostPlaying);
    }

    public RecentInterface getListener() {
        return listener;
    }

    public void setListener(RecentInterface listener) {
        this.listener = listener;
    }

    public List<PostListResponce.DataBean> getRecentList() {
        return filterrecentList;
    }

    public void setRecentList(List<PostListResponce.DataBean> recentList) {
        this.filterrecentList = recentList;
    }

    public void setEnableAll() {
        for (int i = 0; i < filterrecentList.size(); i++) {
            PostListResponce.DataBean dataBean = filterrecentList.get(i);
            dataBean.setIsplayPauseEnableRecent(true);
        }

    }

    public void setDisableAll() {
        for (int i = 0; i < filterrecentList.size(); i++) {
            PostListResponce.DataBean dataBean = filterrecentList.get(i);
            dataBean.setIsplayPauseEnableRecent(false);
        }
    }


    public void resetAllOtherPrevious() {
        try {

            for (int i = 0; i < filterrecentList.size(); i++) {
                PostListResponce.DataBean dataBean = filterrecentList.get(i);
                dataBean.setPlaying(false);
                dataBean.setIsplayPauseEnableRecent(true);
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

            if (playingCurrentHolder != null && playingCurrentHolder != null) {
                ImageView img_play_pause = playingCurrentHolder.imgPlayPause;
                final TextView tv_duration = playingCurrentHolder.txtTimer;

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


            if (recentPostPlayingListener != null)
                recentPostPlayingListener.onPostPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(MyViewHolder holder, PostListResponce.DataBean dataBean) {
        ImageView img_play_pause = holder.imgPlayPause;
        final TextView tv_duration = holder.txtTimer;

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

                        if (recentPostPlayingListener != null)
                            recentPostPlayingListener.onPostPlaying(false);
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


            if (recentPostPlayingListener != null)
                recentPostPlayingListener.onPostPlaying(false);


            resetSiriView();    //reset SIRI

            resetAllOtherPrevious();

        }
    }


    //        PLAY LAST RECORDING
    private void playLastRecordingComments(RecentSearchAdapter.MyViewHolder holder, PostListResponce.DataBean dataBean) {


        if (comment_mediaPlayer == null) {
            snippetOfPlayLastRecComments(holder, dataBean);
        } else {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;

            snippetOfPlayLastRecComments(holder, dataBean);
        }


        if (recentPostPlayingListener != null)
            recentPostPlayingListener.onPostPlaying(true);

        //SIRI LIKE VIEW
//        startSetUpSiriView(false);   //COMMENT

    }

    private void resetSiriView() {
        if (updaterThread != null) {
            updaterThread.setPRog(0);
            updaterThread.updatePlayingStatus(0);
            updaterThread = null;

            theWThread = null;
        }
    }
    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(RecentSearchAdapter.MyViewHolder holder,
                                              final PostListResponce.DataBean dataBean) {

        final ImageView img_play_pause = holder.imgPlayPause;
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

                if (recentPostPlayingListener != null)
                    recentPostPlayingListener.onPostPlaying(false);


                resetAllOtherPrevious();

            }
        });

    }
    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d",  minutes, seconds);
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
                        (HomeActivity) RecentSearchAdapter.this.context);

                updaterThread.setPRog(10000);
            }
        }

        if (theWThread == null) {
            theWThread = new Thread(updaterThread);
            theWThread.start();
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

}