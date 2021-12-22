package com.example.sendrecbroadcastpriority;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    BroadcastReceiver myReceiver;
    IntentFilter intentFilter;
    Button SDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SDB = (Button)findViewById(R.id.SB);
        intentFilter = new IntentFilter("MY_SPECIFIC_ACTION");
        SDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent("MY_SPECIFIC_ACTION");
                i.putExtra("key","some value from intent");
                //sendBroadcast(i);
                //---allows broadcast to be aborted---
                //---allows broadcast receivers to set priority---
                sendOrderedBroadcast(i, null);
            }
        });

        myReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent i) {
                Toast.makeText(context,"Received broadcast in myReceiver, " + " value received: " + i.getStringExtra("key"),Toast.LENGTH_LONG).show();

            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        intentFilter.setPriority(10);
        registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(myReceiver);
    }
}