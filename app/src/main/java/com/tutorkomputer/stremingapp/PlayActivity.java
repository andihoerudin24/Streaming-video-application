package com.tutorkomputer.stremingapp;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.tutorkomputer.stremingapp.data.Constant;
import com.tutorkomputer.stremingapp.data.model.CallResponse;
import com.tutorkomputer.stremingapp.rest.Api;
import com.tutorkomputer.stremingapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayActivity extends AppCompatActivity {

    VideoView videoView;
    RelativeLayout backgroundLayout;
    ProgressBar progressBar;
    MediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Bundle bundle = getIntent().getExtras();
        Uri uri       =Uri.parse(Constant.VIDEO_PATH + bundle.getString("FILENAME"));

        videoView       =findViewById(R.id.videoView);
        backgroundLayout=findViewById(R.id.backgroundLayout);
        progressBar     =findViewById(R.id.ProgressBar);


        if (controller == null){
            controller = new MediaController(this);
            controller.setAnchorView(videoView);
        }

        videoView.start();
        videoView.requestFocus();
        videoView.setMediaController(controller);
        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                backgroundLayout.setVisibility(View.GONE);
            }
        });

        postView(bundle.getString("LIST_ID"));

    }

    private void postView(String list_id){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);

        Call<CallResponse> call = apiInterface.postView(list_id);
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {

            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {

            }
        });
    }
}
