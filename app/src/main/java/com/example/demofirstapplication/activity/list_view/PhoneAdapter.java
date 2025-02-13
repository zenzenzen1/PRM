package com.example.demofirstapplication.activity.list_view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demofirstapplication.R;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends ArrayAdapter<Phone> {
    Activity context;
    int layoutId;
    ArrayList<Phone> phones;

    public PhoneAdapter(@NonNull Activity context, int resource, @NonNull List<Phone> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutId = resource;
        this.phones = (ArrayList<Phone>) objects;
    }
    
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        Phone phone = phones.get(position);
        ImageView img = convertView.findViewById(R.id.imageViewPhone);
        img.setImageResource(phone.getImageId());

        TextView textView = convertView.findViewById(R.id.textViewPhone2);
        textView.setText(phone.getName());
        return convertView;
    }
}





















