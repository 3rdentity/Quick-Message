package com.ellie.bin.mymessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by bin on 6/15/2015.
 */
public class BCReceiver extends BroadcastReceiver {

    // Broadcast receiver to start service when boot completed.

    boolean userSet;
    boolean now;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            SharedPreferences spr = context.getSharedPreferences("settings", 1);
            // Get the user settings and check if he wants the service on
            userSet = spr.getBoolean("userSet", false);
            //  Get current state
            now = spr.getBoolean("now", false);
            if (userSet) {
                if (!now) {
                    context.startService(new Intent(context, ChatHeadService.class));
                    Toast.makeText(context, context.getString(R.string.on), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
