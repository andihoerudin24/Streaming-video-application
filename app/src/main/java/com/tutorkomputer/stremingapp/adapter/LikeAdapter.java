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
import com.tutorkomputer.stremingapp.data.model.Like;
import com.tutorkomputer.stremingapp.utils.DateConverter;

import java.util.List;

public class LikeAdapter extends ArrayAdapter<Like>{


    public LikeAdapter(@NonNull Context context, @NonNull List<Like> objects) {
        super(context,0,objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Like like =getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_list,parent,false);
        }

        TextView txtListId =convertView.findViewById(R.id.txtListId);
        TextView txtTitle  =convertView.findViewById(R.id.txtTitle);
        TextView txtViews  =convertView.findViewById(R.id.txtViews);
        TextView txtDate   =convertView.findViewById(R.id.txtDate);

        txtListId.setText(like.getList_id());
        txtTitle.setText(like.getTitle());
        txtViews.setText(like.getView() + "views");
        txtDate.setText(DateConverter.convert(like.getLike_created()));

        return convertView;
    }
}
