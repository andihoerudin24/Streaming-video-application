package com.tutorkomputer.stremingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tutorkomputer.stremingapp.R;
import com.tutorkomputer.stremingapp.data.Constant;
import com.tutorkomputer.stremingapp.data.model.Video;
import com.tutorkomputer.stremingapp.utils.DateConverter;
import com.tutorkomputer.stremingapp.utils.ImgDownload;

import java.util.List;

public class MainAdapter extends ArrayAdapter<Video> {

    public MainAdapter(@NonNull Context context, @NonNull List<Video> objects) {
        super(context,0 ,objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Video  video =getItem(position);

         if (convertView == null){
             convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_main,parent,false);
         }

        TextView txtTitle =convertView.findViewById(R.id.txtTitle);
        TextView txtcategory =convertView.findViewById(R.id.txtcategory);
        TextView txtDate =convertView.findViewById(R.id.txtDate);
        ImageView imageView =convertView.findViewById(R.id.imageView);


        txtTitle.setText(video.getTitle());
        txtcategory.setText(video.getCategory());
        txtDate.setText(DateConverter.convert(video.getCreated()));
        ImgDownload.picasso(Constant.COVER_PATH + video.getCover(),imageView);

        return convertView;
    }
}
