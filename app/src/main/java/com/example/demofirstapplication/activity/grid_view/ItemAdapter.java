package com.example.demofirstapplication.activity.grid_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demofirstapplication.R;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {
    private final Activity context;
    private final int layoutId;
    private final List<Item> items;
    
    public ItemAdapter(Activity context, int layoutId, List<Item> items) {
        super(context, layoutId, items);
        this.context = context;
        this.layoutId = layoutId;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        Item item = items.get(position);
        ImageView imageView = convertView.findViewById(R.id.imageView3);
        imageView.setImageResource(item.getImageId());
        TextView name = convertView.findViewById(R.id.textView);
        TextView price = convertView.findViewById(R.id.textView2);
        name.setText(item.getName());
        price.setText("Price" + String.valueOf(item.getPrice()));
        return convertView;
    }
}




































