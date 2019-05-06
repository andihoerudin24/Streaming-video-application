package com.tutorkomputer.stremingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tutorkomputer.stremingapp.adapter.LikeAdapter;
import com.tutorkomputer.stremingapp.data.model.CallResponse;
import com.tutorkomputer.stremingapp.data.model.Like;
import com.tutorkomputer.stremingapp.rest.Api;
import com.tutorkomputer.stremingapp.rest.ApiInterface;
import com.tutorkomputer.stremingapp.utils.MenuDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeActivity extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    TextView textinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        listView   = findViewById(R.id.listView);
        progressBar= findViewById(R.id.progressBar);
        textinfo   = findViewById(R.id.txtInfo);

        getSupportActionBar().setTitle("Like");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getlist();
    }

    @Override
    public void onResume(){
        super.onResume();
        getlist();
    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return  true;
    }


    private void getlist(){
        ApiInterface apiInterface= Api.getUrl().create(ApiInterface.class);
        Call<CallResponse> call = apiInterface.getLike(MainActivity.ANDROID_ID);
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                final List<Like> likes = response.body().getLikes();
                if (likes.size()>0){
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);

                    listView.setAdapter(new LikeAdapter(LikeActivity.this,likes));
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            MenuDialog dialog = new MenuDialog();
                            dialog.showDialog(
                                    LikeActivity.this,likes.get(position).getList_id(),likes.get(position).getFilename(),false
                            );
                        }
                    });
                }else {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {

            }
        });
    }

}


