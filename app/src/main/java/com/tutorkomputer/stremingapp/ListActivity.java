package com.tutorkomputer.stremingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorkomputer.stremingapp.adapter.CategoryAdapter;
import com.tutorkomputer.stremingapp.adapter.ListAdapter;
import com.tutorkomputer.stremingapp.adapter.MainAdapter;
import com.tutorkomputer.stremingapp.data.Constant;
import com.tutorkomputer.stremingapp.data.model.CallResponse;
import com.tutorkomputer.stremingapp.data.model.Category;
import com.tutorkomputer.stremingapp.data.model.ListVideo;
import com.tutorkomputer.stremingapp.data.model.Video;
import com.tutorkomputer.stremingapp.rest.Api;
import com.tutorkomputer.stremingapp.rest.ApiInterface;
import com.tutorkomputer.stremingapp.utils.ImgDownload;
import com.tutorkomputer.stremingapp.utils.MenuDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView txtVideos,txtSummary,txtInfo,txtShow;
    ImageView imageView;
    ListView listView;

    private void getList(){
        ApiInterface apiInterface= Api.getUrl().create(ApiInterface.class);
        //Log.e("_logVideoId", bundle.getString("VIDEO_ID"));
        Call<CallResponse> call  = apiInterface.getList(bundle.getString("VIDEO_ID"));
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
               // Log.e("_logCat", response.toString());
                final List<ListVideo> list = response.body().getList();
                if (list.size() > 0){
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(new ListAdapter(ListActivity.this,list));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MenuDialog dialog = new MenuDialog();
                            dialog.showDialog(ListActivity.this,list.get(position).getList_id(),list.get(position).getFilename(),true
                            );
                        }
                    });


                    txtVideos.setText(String.valueOf(list.size()) + "videos");
                }else{
                    progressBar.setVisibility(View.GONE);
                    txtInfo.setVisibility(View.VISIBLE);
                }

                final List<Video> videoList= response.body().getVideos();

                for (int i=0;i< videoList.size(); i++ ){
                    txtSummary.setText(videoList.get(i).getSummary());
                    ImgDownload.picasso(Constant.COVER_PATH + videoList.get(i).getCover(),imageView);
                }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
             //   Log.e("_TAG", t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle     =getIntent().getExtras();
        progressBar=findViewById(R.id.progressBar);
        txtVideos  =findViewById(R.id.txtVideos);
        txtSummary =findViewById(R.id.txtSummary);
        txtInfo    =findViewById(R.id.txtInfo);
        txtShow    =findViewById(R.id.txtShow);
        imageView    =findViewById(R.id.imageviewplaceholder);
        listView    =findViewById(R.id.listVideo);

        txtShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtShow.getText().toString().equals("More")){
                    txtShow.setText("Less");
                    txtSummary.setSingleLine(false);
                  }else{
                    txtShow.setText("More");
                    txtSummary.setSingleLine(true);
                 }
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setTitle(bundle.getString("VIDEO_TITLE"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getList();
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return  true;
    }
}
