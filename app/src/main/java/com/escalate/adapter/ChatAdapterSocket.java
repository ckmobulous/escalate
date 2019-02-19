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
import com.escalate.model.ChatDataBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class ChatAdapterSocket extends RecyclerView.Adapter<ChatAdapterSocket.ChatVHolder>{

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<ChatDataBean> arrayList;

    private int playingPosition;
    private String comment_audioFilePath = null;
    private ChatVHolder playingCurrentHolder;
    private String playingPostDuration = "00:00";
    private MediaPlayer chat_mediaPlayer = null;
    private MediaRecorder chat_mediaRecorder = null;
    private CountDownTimer comment_timer = null;
    private long comment_elapsedTime = 0L;

    //SIRI VIEW
    private SiriUpdateThread updaterThread;
    private Thread theWThread;
    private DrawView siriDView;
    private ChatPlayingListener chatPlayingListener;
    private ChatActivity.ChatActivityBioListener chatListener;


    public ChatAdapterSocket(Context context, ArrayList<ChatDataBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.playingPosition = -1;

        setHasStableIds(true);
    }

    public ChatPlayingListener getChatPlayingListener() {
        return chatPlayingListener;
    }

    public void setChatPlayingListener(ChatPlayingListener chatPlayingListener) {
        this.chatPlayingListener = chatPlayingListener;
    }

    public ChatActivity.ChatActivityBioListener getChatListener() {
        return chatListener;
    }

    public void setChatListener(ChatActivity.ChatActivityBioListener chatListener) {
        this.chatListener = chatListener;
    }



    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    public ArrayList<ChatDataBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ChatDataBean> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }

        ChatListBinding binding =

                DataBindingUtil.inflate(inflater, R.layout.chat_list,parent,false);


        return new ChatVHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatVHolder holder, int position) {
        if(arrayList!=null)
        {
            ChatDataBean dataBean = arrayList.get(position);

            String user_flag = dataBean.getUser_flag();
            String duration = dataBean.getMessage_duration();


            String time = dataBean.getMessage_time();

            if(user_flag!=null && !user_flag.isEmpty())
            {
                switch (user_flag)
                {
                    case "1":   // LOGIN USER
                        holder.binding.llLeftChat.setVisibility(View.GONE);
                        holder.binding.llRightChat.setVisibility(View.VISIBLE);
                        /*if (duration!=null){

                            String[] splitTime =duration.split(":");
                            String one=splitTime[0]; // this will contain "Fruit"
                            String two =splitTime[1];
                            String three =splitTime[2];
                            holder.binding.txtEditMY.setText(two+":"+three);

                        }*/
                        holder.binding.timeMyTxt.setText(time);
                        holder.binding.txtEditMY.setText(duration);
                        if (dataBean.isPlaying()) {
                            holder.binding.imgPlayPauseMy.setImageResource(R.drawable.chat_message_pause_white);

                        } else {
                            holder.binding.imgPlayPauseMy.setImageResource(R.drawable.chat_message_play_white);
                        }

                        break;

                    case "0":   //OTHER RECEIVER

                        holder.binding.llRightChat.setVisibility(View.GONE);
                        holder.binding.llLeftChat.setVisibility(View.VISIBLE);
                        /*if (duration!=null){

                            String[] splitTime =duration.split(":");
                            String one=splitTime[0]; // this will contain "Fruit"
                            String two =splitTime[1];
                            String three =splitTime[2];
                            holder.binding.durationTxt.setText(two+":"+three);

                        }*/
                        holder.binding.timerTxt.setText(time);
                       /* if (duration==null){
                            duration = "00:01";
                        }*/

                        holder.binding.durationTxt.setText(duration);
                        if (dataBean.isPlaying()) {
                            holder.binding.imgPlayPauseUser.setImageResource(R.drawable.create_memo_pause);

                        } else {
                            holder.binding.imgPlayPauseUser.setImageResource(R.drawable.create_memo_play);
                        }

                        break;
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


            holder.binding.imgPlayPauseUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    playPauseProcess(dataBean,holder,position,"user");

                }
            });

            holder.binding.imgPlayPauseMy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    playPauseProcess(dataBean,holder,position,"my");

                }
            });

            }

    }

    private void playPauseProcess(ChatDataBean dataBean,ChatVHolder holder,int position,String check) {

        final String audioUrl = dataBean.getAudioUrl();
        String duration = dataBean.getMessage_duration();

        if (audioUrl != null && !audioUrl.isEmpty()) {

            if (dataBean.isPlayPauseEnable()) {

                if (check.equals("my"))
                {
                    if (position != playingPosition) {
                        resetAllOtherPrevious();
                    }

                    comment_audioFilePath = audioUrl;
                    playingPosition = position;
                    playingCurrentHolder = holder;

                    /*if (duration!=null){

                        String[] splitTime =duration.split(":");
                        String one=splitTime[0]; // this will contain "Fruit"
                        String two =splitTime[1];
                        String three =splitTime[2];
//                        holder.binding.durationTxt.setText(two+":"+three);
                        playingPostDuration = two+":"+three;
                    }*/
//                    playingPostDuration = duration;
                    holder.binding.durationTxt.setText(duration);
                    //SIRI
                    siriDView = holder.binding.siriDViewMy;
                    doJustCommentsPlayStopPlay(playingCurrentHolder, dataBean,"my",duration);

                }
                else {
                    if (position != playingPosition) {
                    resetAllOtherPrevious();
                    }

                    comment_audioFilePath = audioUrl;
                    playingPosition = position;
                    playingCurrentHolder = holder;
//                    playingPostDuration = duration;
                   /* if (duration!=null){

                        String[] splitTime =duration.split(":");
                        String one=splitTime[0]; // this will contain "Fruit"
                        String two =splitTime[1];
                        String three =splitTime[2];
//                        holder.binding.durationTxt.setText(two+":"+three);
                        playingPostDuration = two+":"+three;
                    }*/
                    holder.binding.durationTxt.setText(duration);
                    //SIRI
                    siriDView = holder.binding.siriDView;
                    doJustCommentsPlayStopPlay(playingCurrentHolder, dataBean,"user",duration);
                }
            }

        } else {
            Toast.makeText(context, "Empty audio", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList != null ? arrayList.size() : 0;
    }



    //VIEW HOLDER
    class ChatVHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ChatListBinding binding;

        ChatVHolder(ChatListBinding cityBinding) {
            super(cityBinding.getRoot());

            this.binding = cityBinding;


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
               /* case R.id.rl_rootChatP:

                    break;*/
            }
        }
    }


    public void resetAllOtherPrevious() {
        try {

            for (int i = 0; i < arrayList.size(); i++) {
                ChatDataBean dataBean = arrayList.get(i);
                dataBean.setPlaying(false);
                dataBean.setPlayPauseEnable(true);
            }

            //COMMENTS IN POST LIST
            if (chat_mediaPlayer != null) {
                chat_mediaPlayer.pause();
                chat_mediaPlayer.stop();
                chat_mediaPlayer.reset();
                chat_mediaPlayer.release();
                chat_mediaPlayer = null;
            }

            if (chat_mediaRecorder != null) {
                chat_mediaRecorder.stop();
                chat_mediaRecorder.reset();
                chat_mediaRecorder.release();
                chat_mediaRecorder = null;
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


                /////////(login user) my
                ImageView img_play_pause = playingCurrentHolder.binding.imgPlayPauseMy;
                final TextView tv_duration = playingCurrentHolder.binding.txtEditMY;

                img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.chat_message_play_white));

               // tv_duration.setText("00:00");
//                tv_duration.setText(bio_time_duration);


                // other user
                ImageView img_play_pause_other = playingCurrentHolder.binding.imgPlayPauseUser;
                final TextView tv_duration_other = playingCurrentHolder.binding.durationTxt;

                /////////
                img_play_pause_other.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.create_memo_play));

            //    tv_duration_other.setText("00:00");
//                tv_duration_other.setText(bio_time_duration);

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

    public void setEnableAll() {
        for (int i = 0; i < arrayList.size(); i++) {
            ChatDataBean dataBean = arrayList.get(i);
            dataBean.setPlayPauseEnable(true);
        }

    }

    public void setDisableAll() {
        for (int i = 0; i < arrayList.size(); i++) {
            ChatDataBean dataBean = arrayList.get(i);
            dataBean.setPlayPauseEnable(false);
        }
    }




    /*
     * **************************
     * //TODO: PLAY RECORDING
     * **************************
     * */
    private void doJustCommentsPlayStopPlay(ChatVHolder holder, ChatDataBean dataBean,String check, String duration_t) {


        if (check.equals("my")){
            ImageView img_play_pause = holder.binding.imgPlayPauseMy;
            final TextView tv_duration = holder.binding.txtEditMY;
            if (!dataBean.isPlaying()) {

                playLastRecordingComments(holder, dataBean,check);

                try {
                    final int duration = chat_mediaPlayer.getDuration();

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

                            tv_duration.setText(duration_t);
//                            tv_duration.setText(playingPostDuration);

                            resetSiriView();    //reset SIRI

                            if (chatPlayingListener != null)
                                chatPlayingListener.onPostPlaying(false);
                        }
                    }.start();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.chat_message_pause_white));

                dataBean.setPlaying(true);


            } else {

                if (chat_mediaPlayer != null)
                    chat_mediaPlayer.pause();

                img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.chat_message_play_white));

                dataBean.setPlaying(false);


                tv_duration.setText(duration_t);
//                tv_duration.setText(playingPostDuration);

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
        else {
//            ImageView img_play_pauseMy = holder.binding.imgPlayPauseMy;
//            final TextView tv_durationMy = holder.binding.txtEditMY;
            ImageView img_play_pause = holder.binding.imgPlayPauseUser;
            final TextView tv_duration = holder.binding.durationTxt;

            if (!dataBean.isPlaying()) {

                playLastRecordingComments(holder, dataBean,check);

                try {
                    final int duration = chat_mediaPlayer.getDuration();

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

                            tv_duration.setText(duration_t);
//                            tv_duration.setText(playingPostDuration);

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
                /*img_play_pauseMy.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.chat_message_pause_white));*/

                dataBean.setPlaying(true);


            } else {

                if (chat_mediaPlayer != null)
                    chat_mediaPlayer.pause();

                img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.create_memo_play));
               /* img_play_pauseMy.setImageDrawable(ContextCompat.getDrawable(context,
                        R.drawable.chat_message_play_white));*/
                dataBean.setPlaying(false);


                tv_duration.setText(duration_t);
//                tv_duration.setText(playingPostDuration);

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



    }

    public static String convertSecondsToHMmSs(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
//        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);


        return String.format(Locale.getDefault(), "%02d:%02d",  minutes, seconds);
    }


    //        PLAY LAST RECORDING
    private void playLastRecordingComments(ChatVHolder holder, ChatDataBean dataBean,String check) {


        if (chat_mediaPlayer == null) {
            snippetOfPlayLastRecComments(holder, dataBean,check);
        } else {
            chat_mediaPlayer.release();
            chat_mediaPlayer = null;

            snippetOfPlayLastRecComments(holder, dataBean,check);
        }


        if (chatPlayingListener != null)
            chatPlayingListener.onPostPlaying(true);

        //SIRI LIKE VIEW
        startSetUpSiriView(false);   //COMMENT


    }


    //        PLAY LAST RECORDING snippet
    private void snippetOfPlayLastRecComments(ChatVHolder holder,
                                              final ChatDataBean dataBean,String check) {

        if (check.equals("my")){
            final ImageView img_play_pause = holder.binding.imgPlayPauseMy;
            chat_mediaPlayer = new MediaPlayer();

            try {
                chat_mediaPlayer.setDataSource(comment_audioFilePath);
                chat_mediaPlayer.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }

            chat_mediaPlayer.start();


            chat_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    chat_mediaPlayer.stop();
                    img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                            R.drawable.chat_message_play_white));

                    dataBean.setPlaying(false);

                    if (chatPlayingListener != null)
                        chatPlayingListener.onPostPlaying(false);


                    resetAllOtherPrevious();
                }
            });

        }
        else {
            final ImageView img_play_pause = holder.binding.imgPlayPauseUser;
            chat_mediaPlayer = new MediaPlayer();
            try {
                chat_mediaPlayer.setDataSource(comment_audioFilePath);
                chat_mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            chat_mediaPlayer.start();

            chat_mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    chat_mediaPlayer.stop();
                    img_play_pause.setImageDrawable(ContextCompat.getDrawable(context,
                            R.drawable.create_memo_play));

                    dataBean.setPlaying(false);

                    if (chatPlayingListener != null)
                        chatPlayingListener.onPostPlaying(false);


                    resetAllOtherPrevious();
                }
            });

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
                        (ChatActivity) ChatAdapterSocket.this.context);

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

    public interface ChatPlayingListener {

        void onPostPlaying(boolean isPostPlaying);
    }

}
