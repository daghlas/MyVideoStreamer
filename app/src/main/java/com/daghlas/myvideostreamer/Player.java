package com.daghlas.myvideostreamer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class Player extends AppCompatActivity {

    VideoView videoView;
    TextView videoTitle, videoDescription;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //extract passed intent data in this new activity
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        VideoModel video = (VideoModel) data.getSerializable("videoData");

        videoView = findViewById(R.id.videoView);
        videoTitle = findViewById(R.id.videoTitle);
        videoDescription = findViewById(R.id.videoDescription);
        progressBar = findViewById(R.id.progressBar);

        videoTitle.setText(video.getTitle());
        videoDescription.setText(video.getDescription());
        Uri videoUrl = Uri.parse(video.getVideoUri());
        videoView.setVideoURI(videoUrl);
        //media controls
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        videoView.setOnPreparedListener(mp -> {
            videoView.start();
            progressBar.setVisibility(View.GONE);
        });


        //action bar back button to exit activity
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(video.getTitle());
        }

    }

    //action bar back button implementation
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        this.finish();
        return true;
    }
}