package com.ellie.bin.mymessage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private boolean userSet;
    private boolean now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences spr = getSharedPreferences("settings", 1);
        userSet = spr.getBoolean("userSet", false);
        now = spr.getBoolean("now", false);
        if(userSet){
            if(!now){
                startService(new Intent(getApplicationContext(), ChatHeadService.class));
                Toast.makeText(getApplicationContext(), getString(R.string.on), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void click(View v){
        SharedPreferences sp = getSharedPreferences("settings", 1);
        SharedPreferences.Editor edit = sp.edit();
        switch (v.getId()){
            case R.id.on:
                startService(new Intent(getApplicationContext(), ChatHeadService.class));
                Toast.makeText(getApplicationContext(), getString(R.string.on), Toast.LENGTH_LONG).show();
                edit.putBoolean("userSet", true);
                edit.putBoolean("now", true);
                edit.apply();
                break;
            case R.id.off:
                stopService(new Intent(getApplicationContext(), ChatHeadService.class));
                Toast.makeText(getApplicationContext(), getString(R.string.off), Toast.LENGTH_LONG).show();
                edit.putBoolean("userSet", false);
                edit.putBoolean("now", false);
                edit.apply();
                break;
            default:
                break;
        }
    }
}
