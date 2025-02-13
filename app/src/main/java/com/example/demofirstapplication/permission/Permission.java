package com.example.demofirstapplication.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class Permission {
    public static boolean checkPermission(Context context, Activity activity, String permission){
        if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
            return false;
        }
        return true;
    }
}
