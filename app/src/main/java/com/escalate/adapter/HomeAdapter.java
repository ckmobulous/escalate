package com.escalate.adapter;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.am.siriview.DrawView;
import com.am.siriview.UpdaterThread;
import com.escalate.R;
import com.escalate.activity.HomeActivity;
import com.escalate.fragments.HomeFragment;
import com.escalate.model.FarmerList;
import com.escalate.model.GenerResponce;
import com.escalate.model.PostListResponce;
import com.escalate.utils.CircleImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.changer.audiowife.AudioWife;
import rm.com.audiowave.AudioWaveView;
import rm.com.audiowave.OnProgressListener;
import rm.com.audiowave.OnSamplingListener;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.facebook.FacebookSdk.getCacheDir;

/**
 * Created by droid2 on 08/03/2018.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Handler.Callback, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    private Handler myHandler = new Handler();
    private Context context;
    private HomeFragment tbdSingleFragment;
    HomeAdapterListener homeAdapterListener;
    List<PostListResponce.DataBean> postList;
    public ImageView img_play_pause;
    private static final int MSG_UPDATE_SEEK_BAR = 1845;
    private MediaPlayer mediaPlayer = null;
    private Handler uiUpdateHandler;
    private int playingPosition;
    private ViewHolder playingHolder;
    public static int oneTimeOnly = 0;
    OnProgressListener progressListenerGlobal;
    rm.com.audiowave.AudioWaveView waveViewGlobal;
    ArrayList<OnProgressListener> listenerArrayList;
    ArrayList<rm.com.audiowave.AudioWaveView> waveViewArrayList;

    private HashTagHelper mHashTagHelper;
    private long multiplierDivider = 10L;
    private long REFRESH_INTERVAL_MS = 30;
    private Handler siriUpdateHandler;
    private SiriUpdateThread updaterThread;
    private Thread theWThread;


    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        if (playingPosition == holder.getAdapterPosition()) {
            // view holder displaying playing audio cell is being recycled
            // change its state to non-playing
            updateNonPlayingView(holder);   // in onViewRecycled
            playingHolder = null;
        }
    }

    /**
     * Changes the view to non playing state
     * - icon is changed to play arrow
     * - seek bar disabled
     * - remove seek bar updater, if needed
     *
     * @param holder ViewHolder whose state is to be chagned to non playing
     */
    private void updateNonPlayingView(ViewHolder holder) {
        if (holder == playingHolder) {
            uiUpdateHandler.removeMessages(MSG_UPDATE_SEEK_BAR);
        }

        holder.sbProgress.setEnabled(false);
        holder.sbProgress.setProgress(0);
        holder.ivPlayPause.setImageResource(R.drawable.home_play_a);
    }

    /**
     * Changes the view to playing state
     * - icon is changed to pause
     * - seek bar enabled
     * - start seek bar updater, if needed
     */
    private void updatePlayingView() {
        Log.w("HomeAdapter", " mediaPlayer.getDuration(): " + mediaPlayer.getDuration());

        multiplierDivider = getDivided(mediaPlayer.getDuration());
        Log.w("HomeAdapter", " getDivided: " + multiplierDivider);

        playingHolder.sbProgress.setMax(mediaPlayer.getDuration());
        playingHolder.sbProgress.setProgress((int) (mediaPlayer.getCurrentPosition() * multiplierDivider));
        playingHolder.sbProgress.setEnabled(true);

        if (mediaPlayer.isPlaying()) {
            uiUpdateHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 100);
            playingHolder.ivPlayPause.setImageResource(R.drawable.home_pause_a);
        } else {
            uiUpdateHandler.removeMessages(MSG_UPDATE_SEEK_BAR);
            playingHolder.ivPlayPause.setImageResource(R.drawable.home_play_a);
        }


        //SIRI LIKE VIEW

        if (updaterThread != null) {
            updaterThread.setPRog(0);
            updaterThread.updatePlayingStatus(0);
            updaterThread = null;

            theWThread = null;
        }

        updaterThread = new SiriUpdateThread(30, playingHolder.siriDView, (HomeActivity) context);
        playingHolder.siriDView.setMaxAmplitude(mediaPlayer.getDuration());
        updaterThread.setPRog(10000);
//        updaterThread.start();

        if (theWThread == null) {
            theWThread = new Thread(updaterThread);
            theWThread.start();
        }

    }

    void stopPlayer() {
        if (null != mediaPlayer) {
            releaseMediaPlayer();
        }
    }

    /*SIRI VIEW*/
//    private void siriUpdate()
//    {
//        if(siriUpdateHandler!=null) {
//            siriUpdateHandler = null;
//        }
//
//        siriUpdateHandler = new Handler();
//        siriUpdateHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                while (((HomeActivity)context)!=null) {
//                    try {
//                        playingHolder.siriDView.setMaxAmplitude(HomeAdapter.this.REFRESH_INTERVAL_MS);
////                        Thread.sleep( Math.max(0, HomeAdapter.this.REFRESH_INTERVAL_MS - redraw()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//
//    }

//    private long redraw() {
//        long t = System.currentTimeMillis();
////        display_game();
//        return System.currentTimeMillis() - t;
//    }

//    private void display_game() {
//        ((HomeActivity)context).runOnUiThread(new Runnable() {
//            public void run() {
//                playingHolder.siriDView.setVisibility(View.VISIBLE);
//                playingHolder.siriDView.setMaxAmplitude(mediaPlayer.getDuration());
//            }
//        });
//    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_SEEK_BAR: {

                try {
                    playingHolder.sbProgress.setProgress(mediaPlayer.getCurrentPosition());
                    playingHolder.sbProgress.setProgress((int) (mediaPlayer.getCurrentPosition() * multiplierDivider));
                    uiUpdateHandler.sendEmptyMessageDelayed(MSG_UPDATE_SEEK_BAR, 100);


                } catch (Exception e) {
                    e.printStackTrace();
                }


                return true;
            }
        }
        return false;
    }


    //CONSTRUCTOR
    public HomeAdapter(Context context, List<PostListResponce.DataBean> postList) {
        this.context = context;
        this.postList = postList;
        this.playingPosition = -1;
        uiUpdateHandler = new Handler((Handler.Callback) this);
        this.listenerArrayList = new ArrayList<>(postList.size());
        this.waveViewArrayList = new ArrayList<>(postList.size());
    }

    public List<PostListResponce.DataBean> getArrayList() {

        return postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.recycler_home_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        playingHolder = viewHolder;

        //HASH TAGS
        mHashTagHelper = HashTagHelper.Creator
                .create(ContextCompat.getColor(context, R.color.blue),
                        null);
        mHashTagHelper.handle(viewHolder.txtCategory);
        String tags = postList.get(position).getTag_list();
        String t = "";
        if (tags != null && !tags.isEmpty())
            t = tags.replaceAll(",", " ");


//        playingHolder.siriDView.setMaxAmplitude(100);
//        playingHolder.siriDView.setNumberOfWaves(1);


        viewHolder.txtFullName.setText(postList.get(position).getFullname());
        viewHolder.txtCategory.setText(t);


        viewHolder.txtDescription.setText(postList.get(position).getDescription());
        viewHolder.txtLike.setText(postList.get(position).getLike_count() + " Likes");
        viewHolder.txtReplies.setText(postList.get(position).getReply_count() + " Replies");
        viewHolder.txtTimer.setText(postList.get(position).getDuration());
        //wave
        progressListenerGlobal = setLatestListener(viewHolder);
        waveViewGlobal = viewHolder.waveView;
        viewHolder.waveView.setExpansionAnimated(true);
        viewHolder.waveView.setOnProgressListener(progressListenerGlobal);

//        postList.get(position).setProgressListenerGlobal(progressListenerGlobal);
//        postList.get(position).setWaveViewGlobal(waveViewGlobal);

        this.listenerArrayList.add(position, progressListenerGlobal);
        this.waveViewArrayList.add(position, waveViewGlobal);


        if (postList.get(position).getReply_count().equals("0")) {
            viewHolder.imgReply.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imgReply.setVisibility(View.VISIBLE);

        }


        String image = postList.get(position).getUser_image();
        if (image != null) {
            if (!image.isEmpty()) {
                Picasso.get().load(image)
                        .resize(100, 100).centerCrop(Gravity.CENTER).into(viewHolder.roundedImageView);
            }
        } else {
            viewHolder.roundedImageView.setImageResource(R.drawable.default_image);
        }


        final String like_count = postList.get(position).getLike_count();
        String like_status = postList.get(position).getLike_flag();

        if (like_count != null && !like_count.isEmpty()) {

            String s;
            if (like_count.equalsIgnoreCase("0")) {
                s = "0 Likes";
            } else {
                s = like_count + " Likes";
            }
            viewHolder.txtLike.setText(s);
        } else {
            String s;
            s = "0 Likes";
            viewHolder.txtLike.setText(s);
        }

        if (like_status != null && !like_status.isEmpty()) {
            boolean s;
            s = "0".equalsIgnoreCase(like_status);
            if (s) {
                viewHolder.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like));
            } else {
                viewHolder.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like_unselected));
            }
        } else {
            viewHolder.imgHeart.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.home_like));
        }

        viewHolder.imgHeart.setOnClickListener(new View.OnClickListener() {
            int count = Integer.parseInt(postList.get(position).getLike_count());

            @Override
            public void onClick(View v) {
                if (postList.get(position).getLike_flag().equalsIgnoreCase("0")) {
                    homeAdapterListener.postLikeService(position, postList.get(position).getPost_id());
                } else {
                    homeAdapterListener.postLikeService(position, postList.get(position).getPost_id());
                }
            }
        });


        //listeners
        viewHolder.linearOtherProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapterListener.startOtherUserProfile(postList.get(position).getUser_id(), postList.get(position).getPost_id(), String.valueOf(position));
            }
        });

        viewHolder.imgReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapterListener.showAudioMicDialog(context, postList.get(position).getUsername(), postList.get(position).getUser_image(), postList.get(position).getTopic_name(), postList.get(position).getPost_id());
            }
        });

        viewHolder.commentImgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapterListener.showAudioMicDialog(context, postList.get(position).getUsername(), postList.get(position).getUser_image(), postList.get(position).getTopic_name(), postList.get(position).getPost_id());

            }
        });

        viewHolder.txtReplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdapterListener.startReplyActivity(postList.get(position).getPost_id());
            }
        });


        viewHolder.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Escalate");
                String sAux = "Audio URL: " + postList.get(position).getAudio_url();
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                context.startActivity(Intent.createChooser(i, "Choose one"));
            }
        });


        if (position == playingPosition) {
            playingHolder = viewHolder;
            // this view holder corresponds to the currently playing audio cell
            // update its view to show playing progress
            updatePlayingView();
        } else {
            // and this one corresponds to non playing
            updateNonPlayingView(viewHolder);   // in onBindVH
        }

        viewHolder.ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == playingPosition) {
                    // toggle between play/pause of audio
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                } else {
                    // start another audio playback
                    playingPosition = position;
                    if (mediaPlayer != null) {
                        if (null != playingHolder) {
                            updateNonPlayingView(playingHolder);     // in onBindVH Button Click  release
                        }
                        mediaPlayer.release();
                    }
                    playingHolder = viewHolder;
                    startMediaPlayer(postList.get(playingPosition).getAudio_url());
                }

                updatePlayingView();
            }
        });


        viewHolder.sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {

                    try {
                        Log.e("onProgressChanged", "progress " + progress);
//                mediaPlayer.seekTo(progress);

                        REFRESH_INTERVAL_MS = progress;
                        float p = progress / multiplierDivider;


//                        HomeAdapter.this.getWaveViewArrayList().get(position).setProgress(p);
//                        HomeAdapter.this.getListenerArrayList().get(position).onStartTracking(p);


                        if (progress == 0) {
                            if (updaterThread != null) {
                                updaterThread.setPRog(0);
                                updaterThread.updatePlayingStatus(0);
                                updaterThread = null;

                                theWThread = null;
                            }
                        }


                        //SIRI LIKE VIEW
                        if (updaterThread == null) {
                            updaterThread = new SiriUpdateThread(30, viewHolder.siriDView, (HomeActivity) context);
                            updaterThread.setPRog(10000);
//                            updaterThread.start();

                        }
                        if (theWThread == null) {
                            theWThread = new Thread(updaterThread);
                            theWThread.start();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void setListener(HomeAdapterListener homeAdapterListener) {
        this.homeAdapterListener = homeAdapterListener;
    }

    private void startMediaPlayer(String audioResId) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {

            mediaPlayer.setDataSource(audioResId);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        mediaPlayer = MediaPlayer.create(context, Uri.parse(audioResId));

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
                playingHolder.ivPlayPause.setImageResource(R.drawable.home_play_a);

            }
        });

    }

    private void releaseMediaPlayer() {
        if (null != playingHolder) {
            updateNonPlayingView(playingHolder);    // in releaseMediaPlayer
        }
        mediaPlayer.release();
        mediaPlayer = null;
        playingPosition = -1;
    }


    private OnProgressListener setLatestListener(final ViewHolder viewHolder) {
        final Random random = new Random();
        final OnProgressListener progressListener = new OnProgressListener() {
            @Override
            public void onStartTracking(float v) {
                final byte[] data = new byte[(int) (1024 * v)];
                random.nextBytes(data);
                viewHolder.waveView.setChunkHeight(40);
                viewHolder.waveView.setMinChunkHeight(10);
                Log.e("DBG", "onStartTracking");
                // if the data is a raw big byte array, you can use setRawData
                viewHolder.waveView.setRawData(data, new OnSamplingListener() {
                    @Override
                    public void onComplete() {
                        Log.e("DBG", "Finished downsampling");
                    }
                });
            }

            @Override
            public void onStopTracking(float v) {
                // audioWaveView.setRawData(new byte[]{1,2,3,2,34,23,4,34,24,2,34,2,34,23,43,2,4,2,34,2,34,23,42,34,23,4,12,4,53,6,54,7,6,98,90,7,9,5,66,34,123,41,42,53,56,46,8,5,69,6,74,56,2,34,14,2,56,4,8,79,5,66,2,34,2,53,46,4,7,5,74,63,45,2,14,26});

                viewHolder.waveView.setChunkHeight(10);
                viewHolder.waveView.setMinChunkHeight(2);
                Log.e("DBG", "onStopTracking");
            }

            @Override
            public void onProgressChanged(float v, boolean b) {
                Log.e("DBG", "onProgressChanged");
            }
        };

        return progressListener;
    }

    public OnProgressListener getProgressListenerGlobal() {
        return progressListenerGlobal;
    }

    public AudioWaveView getWaveViewGlobal() {
        return waveViewGlobal;
    }

    public void setWaveViewGlobal(AudioWaveView waveViewGlobal) {
        this.waveViewGlobal = waveViewGlobal;
    }

    public void setProgressListenerGlobal(OnProgressListener progressListenerGlobal) {
        this.progressListenerGlobal = progressListenerGlobal;
    }

    public void animateProgression(AudioWaveView seekBar, float progress) {
        final ObjectAnimator animation = ObjectAnimator.ofFloat(seekBar, "progress", 0, progress);
        animation.setDuration(1000);
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
//        seekBar.clearAnimation();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

//        mediaPlayer.start();
    }

    //VIEW HOLDER CLASS
    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView circleImageView;
        ViewDataBinding binding;
        public LinearLayout linearOtherProfile;
        public ImageView imgReply, commentImgV, ivPlayPause, imgHeart, imgShare;
        public TextView txtFullName, txtCategory, txtDescription, txtLike, txtTimer, txtReplies;
        public SeekBar sbProgress;
        public RoundedImageView roundedImageView;
        rm.com.audiowave.AudioWaveView waveView;
        ViewHolder playingHolder;

        DrawView siriDView;

        public ViewHolder(View itemView) {
            super(itemView);

            linearOtherProfile = itemView.findViewById(R.id.linear_view_other);
            imgReply = itemView.findViewById(R.id.img_reply);
            commentImgV = itemView.findViewById(R.id.commentIv);
            ivPlayPause = itemView.findViewById(R.id.img_play_pause);
            txtFullName = itemView.findViewById(R.id.tv_name);
            txtCategory = itemView.findViewById(R.id.tv_);
            txtDescription = itemView.findViewById(R.id.txt_discription);
            txtLike = itemView.findViewById(R.id.txt_like);
            txtTimer = itemView.findViewById(R.id.txt_timer);
            txtReplies = itemView.findViewById(R.id.txt_replies);
            sbProgress = itemView.findViewById(R.id.SeekBar01);
            imgHeart = itemView.findViewById(R.id.img_heart);
            roundedImageView = itemView.findViewById(R.id.img_user);
            waveView = itemView.findViewById(R.id.wave);
            imgShare = itemView.findViewById(R.id.imgShare);
            siriDView = (DrawView) itemView.findViewById(R.id.siriDView);


        }


    }


    private long getDivided(long maxDuration) {


        long div = 100L;

        String dur = String.valueOf(maxDuration);
        int s = dur.length();

        if (s < 3) {
            div = 10;
        }
        if (s >= 3) {
            div = 100;
        }
        if (s >= 4) {
            div = 1000;
        }
        if (s >= 6) {
            div = 10000;
        }

        return div;

    }

    public ArrayList<OnProgressListener> getListenerArrayList() {
        return listenerArrayList;
    }

    public void setListenerArrayList(ArrayList<OnProgressListener> listenerArrayList) {
        this.listenerArrayList = listenerArrayList;
    }

    public ArrayList<AudioWaveView> getWaveViewArrayList() {
        return waveViewArrayList;
    }

    public void setWaveViewArrayList(ArrayList<AudioWaveView> waveViewArrayList) {
        this.waveViewArrayList = waveViewArrayList;
    }


    public interface HomeAdapterListener {

        void startOtherUserProfile(String userID, String post_id, String pos);

        void startReplyActivity(String audioid);

        void showAudioMicDialog(Context context, String username, String uImg, String topicName, String postId);

        void postLikeService(int position, String audioid);

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
            this.c.runOnUiThread(new Runnable() {
                public void run() {
                    SiriUpdateThread.this.v.setMaxAmplitude(SiriUpdateThread.this.tr);
                }
            });
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
                    Log.w(TAG,"updatePlayingStatus: not valid");
                    break;
            }

        }


    }

}

