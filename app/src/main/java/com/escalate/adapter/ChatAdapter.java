package com.escalate.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
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
import com.escalate.R;
import com.escalate.activity.ChatActivity;
import com.escalate.databinding.ChatListBinding;
import com.escalate.model.ChatAudioMsz;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static com.escalate.utils.MyApplication.getContext;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatVHolder> {

    public ChatAdapter() {

    }

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ChatAudioMsz> chatAudioMszArrayList;
    private String userLoginId = "";
    private int playingPosition;
    private String playingPostDuration = "00:00";
    private ChatVHolder playingCurrentHolder;
    private String comment_audioFilePath = null;   //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;
    private DrawView siriDViewMy;
    private MediaRecorder comment_mediaRecorder = null;
    private MediaPlayer comment_mediaPlayer = null;
    private CountDownTimer comment_timer = null;
    private long comment_elapsedTime = 0L;
    private ChatPlayingListener chatPlayingListener;
    private String myFirebaseUid="";
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;


    public ChatAdapter(Context context, ArrayList<ChatAudioMsz> arrayList) {
        this.context = context;
        this.chatAudioMszArrayList = arrayList;
        this.playingPosition = -1;
        this.userLoginId = SPreferenceWriter.getInstance(context).getString(SPreferenceKey.ID);
        myFirebaseUid = SPreferenceWriter.getInstance(context)
                .getString(SPreferenceKey.FIREBASE_UID);
        setHasStableIds(true);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ChatVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ChatListBinding binding = DataBindingUtil.inflate(inflater, R.layout.chat_list, parent, false);
        return new ChatVHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatVHolder holder, int position) {
//         llRightChat = Me
//         llLeftChat = Receiver

        /*databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();*/

        if (chatAudioMszArrayList != null) {
            final ChatAudioMsz chatAudioMsz = chatAudioMszArrayList.get(position);

            final String audioUrl = chatAudioMsz.getAudioUrl();
            final String duration = chatAudioMsz.getDuration();

            holder.binding.txtEditMY.setText(duration);

            String sendUserId = chatAudioMszArrayList.get(position).getSenderId();
            String reciverUserId = chatAudioMszArrayList.get(position).getReceiverId();

            if (sendUserId != null) {

                if (sendUserId.equalsIgnoreCase(reciverUserId)) {
                    holder.binding.llLeftChat.setVisibility(View.GONE);
                    holder.binding.llRightChat.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.llLeftChat.setVisibility(View.VISIBLE);
                    holder.binding.llRightChat.setVisibility(View.GONE);
                }
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

            if (chatAudioMsz.isPlaying()) {
                holder.binding.imgPlayPauseMy.setImageResource(R.drawable.chat_message_pause_white);

            } else {
                holder.binding.imgPlayPauseMy.setImageResource(R.drawable.chat_message_play_white);
            }

            if (chatAudioMsz.isPlaying()) {
                holder.binding.imgPlayPauseUser.setImageResource(R.drawable.home_pause_a);

            } else {
                holder.binding.imgPlayPauseUser.setImageResource(R.drawable.home_play_a);
            }


            holder.binding.imgPlayPauseMy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (audioUrl != null && !audioUrl.isEmpty()) {

                        if (chatAudioMsz.isPlayPauseEnable()) {

                            if (position != playingPosition) {
                                resetAllOtherPrevious();
                            }

                            comment_audioFilePath = audioUrl;
                            playingPosition = position;
                            playingCurrentHolder = holder;
                            playingPostDuration = duration;

                            //SIRI
                            siriDViewMy = holder.binding.siriDViewMy;

                            doJustCommentsPlayStopPlayMy(playingCurrentHolder, chatAudioMsz);

                        }

                    } else {
                        Toast.makeText(context, "Empty audio", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            holder.binding.imgPlayPauseUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (audioUrl != null && !audioUrl.isEmpty()) {

                        if (chatAudioMsz.isPlayPauseEnable()) {

                            if (position != playingPosition) {
                                resetAllOtherPrevious();
                            }

                            comment_audioFilePath = audioUrl;
                            playingPosition = position;
                            playingCurrentHolder = holder;
                            playingPostDuration = duration;

                            //SIRI
                            siriDView = holder.binding.siriDView;

                            doJustCommentsPlayStopPlay(playingCurrentHolder, chatAudioMsz);

                        }

                    } else {
                        Toast.makeText(context, "Empty audio", Toast.LENGTH_SHORT).show();
                    }

                }
            });



            /*View.OnClickListener clickListener =   new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (audioUrl != null && !audioUrl.isEmpty()) {

                        if (chatAudioMsz.isPlayPauseEnable()) {

                            if (position != playingPosition) {
                                resetAllOtherPrevious();
                            }

                            comment_audioFilePath = audioUrl;
                            playingPosition = position;
                            playingCurrentHolder = holder;
                            playingPostDuration = duration;

                            //SIRI
                            siriDView = holder.binding.siriDView;

                            doJustCommentsPlayStopPlay(playingCurrentHolder, chatAudioMsz);

                        }

                    } else {
                        Toast.makeText(context, "Empty audio", Toast.LENGTH_SHORT).show();
                    }
                }
            };


            holder.binding.imgPlayPauseMy.setOnClickListener(clickListener);

            holder.binding.imgPlayPauseUser.setOnClickListener(clickListener);
        }*/


        }

    }

    @Override
    public int getItemCount() {
        if (chatAudioMszArrayList != null)
            return chatAudioMszArrayList.size();
        else
            return 0;
    }

    class ChatVHolder extends RecyclerView.ViewHolder {

        private ChatListBinding binding;

        ChatVHolder(ChatListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }

        public ChatListBinding getBinding() {
            return binding;
        }


    }

    public void resetAllOtherPrevious() {
        try {

            for (int i = 0; i < chatAudioMszArrayList.size(); i++) {
                ChatAudioMsz dataBean = chatAudioMszArrayList.get(i);
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
                ImageView img_play_pause = playingCurrentHolder.binding.imgPlayPauseUser;
                final TextView tv_duration = playingCurrentHolder.binding.timerTxt;

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


            if (chatPlayingListener != null)
                chatPlayingListener.onPostPlaying(false);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*
     * ****************
     * //TODO: SYNC SIRI VIEW
     * ****************
     * */
    private void startSetUpSiriViewMy(boolean isBio) {

        resetSiriView();

        //SIRI LIKE VIEW
        if (updaterThread == null) {
            if (!isBio) {
                updaterThread = new
                        SiriUpdateThread(30, siriDViewMy,
                        (ChatActivity) ChatAdapter.this.context);

                updaterThread.setPRog(10000);
            }
        }

        if (theWThread == null) {
            theWThread = new Thread(updaterThread);
            theWThread.start();
        }

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
                        (ChatActivity) ChatAdapter.this.context);

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

    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlayMy(ChatVHolder holder, ChatAudioMsz dataBean) {
        ImageView img_play_pause = holder.binding.imgPlayPauseMy;
        final TextView tv_duration = holder.binding.txtEditMY;

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
                        startSetUpSiriViewMy(false);   //COMMENT
                    }

                    public void onFinish() {

                        tv_duration.setText("00:00");
                        tv_duration.setText(playingPostDuration);

                        resetSiriView();    //reset SIRI

                        if (chatPlayingListener != null)
                            chatPlayingListener.onPostPlaying(false);
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


            if (chatPlayingListener != null)
                chatPlayingListener.onPostPlaying(false);


            resetSiriView();    //reset SIRI

            resetAllOtherPrevious();

        }
    }


    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(ChatVHolder holder, ChatAudioMsz dataBean) {
        ImageView img_play_pause = holder.binding.imgPlayPauseUser;
        final TextView tv_duration = holder.binding.timerTxt;

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

                        if (chatPlayingListener != null)
                            chatPlayingListener.onPostPlaying(false);
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


            if (chatPlayingListener != null)
                chatPlayingListener.onPostPlaying(false);


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
    private void playLastRecordingComments(ChatVHolder holder, ChatAudioMsz dataBean) {


        if (comment_mediaPlayer == null) {
            snippetOfPlayLastRecComments(holder, dataBean);
        } else {
            comment_mediaPlayer.release();
            comment_mediaPlayer = null;

            snippetOfPlayLastRecComments(holder, dataBean);
        }


        if (chatPlayingListener != null)
            chatPlayingListener.onPostPlaying(true);

        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT


    }


    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(ChatVHolder holder,
                                              final ChatAudioMsz dataBean) {

        final ImageView img_play_pause = holder.binding.imgPlayPauseUser;
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

                if (chatPlayingListener != null)
                    chatPlayingListener.onPostPlaying(false);


                resetAllOtherPrevious();

            }
        });


    }


    public interface ChatPlayingListener {

        void onPostPlaying(boolean isPostPlaying);
    }

}
