package com.example.phase1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Observer {

    LinearLayout getContainer;
    NotificationCenter notificationCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("uuuuuuuuuuuuuuuuuuu", "4");
        StorageManager.getStorageManager().createFileDir(getBaseContext(), false );
        NotificationCenter.getNotificationCenter().registerForData(this);

        getContainer = findViewById(R.id.get_container);

        Button getBtn = findViewById(R.id.get_btn);
        Button clearBtn = findViewById(R.id.clear_btn);
        Button refreshBtn = findViewById(R.id.refresh_btn);
        notificationCenter = NotificationCenter.getNotificationCenter();
        showData();

        getBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("uuuuuuuuuuuuuuuuuuu", "3");
                MessageController.getMessageController().fetch(false, getBaseContext());
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                getContainer.removeAllViews();
                MessageController.getMessageController().list.clear();
            }
        });


        refreshBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MessageController.getMessageController().fetch(true, getBaseContext());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NotificationCenter.getNotificationCenter().unRegisterForData(this);
    }

    @SuppressLint("SetTextI18n")
    public void showData(){
        Log.i("uuuuuuuuuuuuuuuuuuu", "1");
        ArrayList<Integer> integers = MessageController.getMessageController().list;
        Log.i("uuuuuuuuuuuuuuuuuuu", ">>>>> " + MessageController.getMessageController().list.size() + " " + integers.size());
        ArrayList<TextView> akbar = new ArrayList<>();
        for(int i : integers){
            TextView test = new TextView(getApplicationContext());
            test.setText(Integer.toString(i));
            akbar.add(test);
        }
        TextView test = new TextView(getApplicationContext());
        test.setText("\n");
        akbar.add(test);

        getContainer.removeAllViews();
        for(TextView t: akbar){
            getContainer.addView(t);
        }
    }

    @Override
    public void update() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showData();
            }
        });
    }
}
