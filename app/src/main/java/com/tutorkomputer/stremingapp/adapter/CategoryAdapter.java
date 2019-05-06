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
import com.tutorkomputer.stremingapp.data.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    public CategoryAdapter(@NonNull Context context, @NonNull List<Category> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
        Category category =getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_category,parent,false);

        }
        TextView txtId       =convertView.findViewById(R.id.txtId);
        TextView txtCategory =convertView.findViewById(R.id.txtCategory);

        txtId.setText(category.getCat_id());
        txtCategory.setText(category.getCategory());

        return convertView;
    }
}
