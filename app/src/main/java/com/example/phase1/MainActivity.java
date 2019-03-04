package com.example.phase1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout getContainer;
    NotificationCenter notificationCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getContainer = findViewById(R.id.get_container);

        Button getBtn = findViewById(R.id.get_btn);
        Button clearBtn = findViewById(R.id.clear_btn);
        Button refreshBtn = findViewById(R.id.refresh_btn);

        notificationCenter = new NotificationCenter();
//        notificationCenter.register(this);


        getBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<TextView> akbar = new ArrayList<>();
                for(int i = 0; i < 5; i++){
                    TextView test = new TextView(getApplicationContext());
                    test.setText("salam");
                    akbar.add(test);
                }

                for(TextView t: akbar){
                    getContainer.addView(t);
                }
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                getContainer.removeAllViews();
            }
        });


        refreshBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });
    }

}
