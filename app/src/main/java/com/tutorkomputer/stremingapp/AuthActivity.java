package com.tutorkomputer.stremingapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tutorkomputer.stremingapp.data.model.CallResponse;
import com.tutorkomputer.stremingapp.rest.Api;
import com.tutorkomputer.stremingapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private String ANDROID_ID;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        progressBar=findViewById(R.id.progresbar);
        //MENGAMBIL EMAI ANDROID
        ANDROID_ID= Settings.Secure.getString(this.getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        Log.e("log_androidid",ANDROID_ID);
        Auth();
    }

    @Override
    protected void onResume(){
        super.onResume();
        finish();
    }


    private void Auth(){
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);

        Call<CallResponse> call = apiInterface.postAuth(ANDROID_ID);
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                if (response.body().getResponse().equals("success")){
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(AuthActivity.this,MainActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
              Log.e("_logE",t.toString());
            }
        });
    }
}
