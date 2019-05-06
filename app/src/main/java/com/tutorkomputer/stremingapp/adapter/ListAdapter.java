package com.tutorkomputer.stremingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tutorkomputer.stremingapp.R;
import com.tutorkomputer.stremingapp.data.model.ListVideo;
import com.tutorkomputer.stremingapp.utils.DateConverter;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ListVideo> {


    public ListAdapter(@NonNull Context context, @NonNull List<ListVideo> objects){
        super(context,0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListVideo listVideo=getItem(position);

        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.adapter_list,parent,false);

        }
        TextView txtListId =convertView.findViewById(R.id.txtListId);
        TextView txtTitle  =convertView.findViewById(R.id.txtTitle);
        TextView txtViews  =convertView.findViewById(R.id.txtViews);
        TextView txtDate   =convertView.findViewById(R.id.txtDate);

        txtListId.setText(listVideo.getList_id());
        txtTitle.setText(listVideo.getTitle());
        txtViews.setText(listVideo.getView() + "views");
        txtDate.setText(DateConverter.convert(listVideo.getCreated()));

        return convertView;
    }
}
