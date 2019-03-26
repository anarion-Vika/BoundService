package com.example.vika.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbProgressBar;
    private boolean binded = false;
    private ProgressBarService progressBarService;

    private TextView textView;
    ServiceConnection progressBarServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ProgressBarService.ProgressBarBinder binder = (ProgressBarService.ProgressBarBinder) service;
            progressBarService = binder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            binded = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ProgressBarService.class);
        this.bindService(intent, progressBarServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (binded) {
            this.unbindService(progressBarServiceConnection);
            binded = false;
        }
    }

    private void AddProgress() {
        int progress = this.progressBarService.setProgress(pbProgressBar);
        pbProgressBar.setProgress(progress);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pbProgressBar = findViewById(R.id.progressBar);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        pbProgressBar.setProgress(0);

    }

    public void progressClick(View view) {
        AddProgress();
    }
}
