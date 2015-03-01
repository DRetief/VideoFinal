package com.sweng.zharif20.videofinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Created by zharif20 on 2/20/15.
 */
public class VideoPlayer extends Activity {

    //variables
    ProgressDialog pgDialog;
    VideoView videoview;
    private int position = 0;

    //insert url video
    String vidUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    //https://www.youtube.com/watch?v=1uyM4_a9t6o
    //http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
    //http://www.androidbegin.com/tutorial/AndroidCommercial.3gp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get layout from video.xml
        setContentView(R.layout.video_player);
        //find your videoview from video_main xml layout
        videoview = (VideoView) findViewById(R.id.videoView);

        //execute streamvideo asyntax

        //create a progressbar
        pgDialog = new ProgressDialog(VideoPlayer.this);

        //setprogressbar title
        pgDialog.setTitle("Android Video");
        //set progressbar message
        pgDialog.setMessage("Buffering");
        pgDialog.setIndeterminate(false);
        pgDialog.setCancelable(false);
        //show progressbar
        pgDialog.show();

        try {
            //start the mediacontroller
            MediaController mediaController = new MediaController(VideoPlayer.this);
            mediaController.setAnchorView(videoview);
            //get the url from string videourl
            Uri video = Uri.parse(vidUrl);
            videoview.setMediaController(mediaController);
            videoview.setVideoURI(video);

            //get duration for the video
            /*videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i(vidUrl, "Duration = " + videoview.getDuration());
                }
            });*/

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            //close the progressbar and play the video
            @Override
            public void onPrepared(MediaPlayer mp) {
                pgDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                videoview.seekTo(position);
                if (position == 0) {
                    videoview.start();
                } else {
                    //if from the resume activity, video playback will be paused
                    videoview.pause();
                }

            }
        });
    }


        @Override
        protected void onSaveInstanceState (Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            //use onSaveInstanceState in order to store the video playback position for orientation change
            savedInstanceState.putInt("Position", videoview.getCurrentPosition());
            videoview.pause();
        }


        @Override
        protected void onRestoreInstanceState (Bundle savedInstanceState){
            super.onRestoreInstanceState(savedInstanceState);
            //use onRestoreInstanceState in order to play the video playback from the stored position
            position = savedInstanceState.getInt("Position");
            videoview.seekTo(position);

        }

}









