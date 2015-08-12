package com.ellie.bin.mymessage;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

public class ChatHeadService extends Service {

    // Service to display three dots on the side of the screen to open dialog and write message and share it via Whatsapp

    private WindowManager windowManager;
    private ImageView chatHead;

    @Override
    public IBinder onBind(Intent intent) {
        // Not used
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Add element to window
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Create new imageView
        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.whatsapp);

        // Set params for imageView
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        // Set the gravity (the spot on the screen)
        params.gravity = Gravity.CENTER | Gravity.START;
        params.x = 15;
        params.y = 0;
        // onTouch = open dialog with EditText to write new message
        chatHead.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        final EditText input = new EditText(getApplicationContext());
                        AlertDialog.Builder builder = new AlertDialog.Builder(ChatHeadService.this)
                                .setTitle("New message")
                                .setView(input)
                                // Share message text via Whatsapp
                                .setPositiveButton("Send", new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                                        whatsappIntent.setType("text/plain");
                                        whatsappIntent.setPackage("com.whatsapp");
                                        whatsappIntent.putExtra(Intent.EXTRA_TEXT, input.getText().toString());
                                        whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        try {
                                            startActivity(whatsappIntent);
                                        } catch (android.content.ActivityNotFoundException ex) {

                                        }
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog x = builder.create();
                        x.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                        WindowManager.LayoutParams wmlp = x.getWindow().getAttributes();
                        wmlp.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                        wmlp.x = 0;   //x position
                        wmlp.y = 100;   //y position
                        x.show();
                        return true;
                }
                return false;
            }
        });
        windowManager.addView(chatHead, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getPackageName(), "");
    }
}
