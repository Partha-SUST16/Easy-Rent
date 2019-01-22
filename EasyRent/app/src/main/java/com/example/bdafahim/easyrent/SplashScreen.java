package com.example.bdafahim.easyrent;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    private final int Splash_Display_Time = 3000;
    private ProgressBar progressBar;
    private int progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this,UserCatagory.class);
                startActivity(intent);
                finish();

            }
        } , Splash_Display_Time);*/

       progressBar = (ProgressBar) findViewById(R.id.progressBarid);
       Thread thread = new Thread(new Runnable() {
           @Override
           public void run() {
               doWork();
               startApp();
           }
       });

       thread.start();


    }

    public void doWork()
    {
        for(progress=20;progress<=100;progress+=20)
        {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void startApp()
    {
        Intent intent = new Intent(SplashScreen.this,UserCatagory.class);
        startActivity(intent);
        finish();
    }
}
