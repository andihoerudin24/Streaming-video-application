package com.tutorkomputer.stremingapp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorkomputer.stremingapp.LikeActivity;
import com.tutorkomputer.stremingapp.MainActivity;
import com.tutorkomputer.stremingapp.PlayActivity;
import com.tutorkomputer.stremingapp.R;
import com.tutorkomputer.stremingapp.data.model.CallResponse;
import com.tutorkomputer.stremingapp.rest.Api;
import com.tutorkomputer.stremingapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuDialog {

    public void showDialog(final Context context, final String list_id, final String filename, final boolean like){
        LayoutInflater inflater=LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_menu,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setView(view);

        final AlertDialog dialog = builder.create();

        TextView txtPlay = view.findViewById(R.id.txtPlay);
        TextView txtLike = view.findViewById(R.id.txtLike);

        txtPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayActivity.class);
                intent.putExtra("LIST_ID",list_id);
                intent.putExtra("FILENAME",filename);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });
        if (like){
            txtLike.setText("Like");
        }else{
            txtLike.setText("UnLike");
        }

        txtLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);

                Call<CallResponse> call;
                if (like){
                    call = apiInterface.postLike(MainActivity.ANDROID_ID,list_id);
                 }
                else{
                    call = apiInterface.postUnLike(MainActivity.ANDROID_ID,list_id);
                }
                call.enqueue(new Callback<CallResponse>() {
                    @Override
                    public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {
                         if (response.body().getResponse().equals("success")){
                             if (like){
                                 Toast.makeText(context,"liked",Toast.LENGTH_LONG).show();
                             }else{
                                 Toast.makeText(context,"Unliked",Toast.LENGTH_LONG).show();
                                 ((LikeActivity)context).onResume();
                             }

                         }
                    }

                    @Override
                    public void onFailure(Call<CallResponse> call, Throwable t) {

                    }
                });
                dialog.dismiss();
            }
        });


        dialog.show();
    }
}
