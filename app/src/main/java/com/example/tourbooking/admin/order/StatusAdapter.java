package com.example.tourbooking.admin.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tourbooking.Entity.Status;

import java.util.List;

public class StatusAdapter extends ArrayAdapter<Status> {

    public StatusAdapter(Context context, List<Status> categories) {
        super(context, android.R.layout.simple_spinner_dropdown_item, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(getItem(position).getStatusName());  // Set only name
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(getItem(position).getStatusName());  // Set only name
        return textView;
    }
}
