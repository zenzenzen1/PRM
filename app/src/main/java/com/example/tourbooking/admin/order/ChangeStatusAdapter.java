package com.example.tourbooking.admin.order;

import android.content.Context;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.tourbooking.Entity.Status;
import com.example.tourbooking.R;

import java.util.List;

public class ChangeStatusAdapter extends ArrayAdapter<Status> {

    public ChangeStatusAdapter(Context context, List<Status> status) {
        super(context, android.R.layout.simple_spinner_dropdown_item, status);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(getItem(position).getStatusName());  // Set only name
        textView.setTextColor(ContextCompat.getColor(getContext(), com.example.tourbooking.R.color.black));
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, // Width
                ViewGroup.LayoutParams.WRAP_CONTENT  // Height
        );
        params.setMargins(0, 0, 10, 0);
        textView.setLayoutParams(params);
        textView.setPadding(0, 0, 3, 0);
        textView.setGravity(Gravity.START);
        
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
//        params.setMargins(0, 0, 0, 0);
//        textView.setLayoutParams(params);
        return textView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView = (TextView) super.getDropDownView(position, convertView, parent);
        textView.setText(getItem(position).getStatusName());  // Set only name
        return textView;
    }
}