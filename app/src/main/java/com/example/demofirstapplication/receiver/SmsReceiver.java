package com.example.demofirstapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        processSms(context, intent);
        
    }

    private void processSms(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String message = "";
        String body = "";
        String address = "";
        if(bundle != null){
            Object[] sms = (Object[]) bundle.get("pdus");
            for(int i = 0; i < sms.length; i++){
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                body += smsMessage.getMessageBody();
                address = smsMessage.getOriginatingAddress();
            }
        }
        message = "From: " + address + "\n" + "Message: " + body;
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


}
















