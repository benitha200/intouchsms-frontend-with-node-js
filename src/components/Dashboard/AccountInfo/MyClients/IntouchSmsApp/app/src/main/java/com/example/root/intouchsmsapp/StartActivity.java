package com.example.root.intouchsmsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.root.intouchsmsapp.Activities.LoginActivity;
import com.example.root.intouchsmsapp.Activities.SendSms;

import static com.example.root.intouchsmsapp.Activities.LoginActivity.SHARED_PREFS;
import static com.example.root.intouchsmsapp.Activities.LoginActivity.TOKEN;

public class StartActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView imageView;
    public static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        imageView = findViewById(R.id.loadImage);

        // below for animation as well
        /*imageView.animate().alpha(4000).setDuration(0);*/

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.maintransition);
        imageView.startAnimation(myanim);

        /*handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dsp = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(dsp);
                finish();
            }
        }, 4000);*/


        final Intent intent = new Intent(this, LoginActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

                    if (prefs.getString(TOKEN, "None").equals("None")){
                        startActivity(intent);
                    } else {
                        openSendSmsActivty();
                    }

                    finish();
                }
            }
        };
        timer.start();
    }

    private void openSendSmsActivty(){
        Intent intent = new Intent(this, SendSms.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public static void showProgressDialog(String progresstxt, Context context) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(progresstxt);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog=null;
    }
}
