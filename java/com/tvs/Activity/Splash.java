package com.tvs.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tvs.R;

import Shared.Config;

public class Splash extends Activity {
    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean isLoggedin=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTheme(R.style.splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sharedPreferences = Splash.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    isLoggedin = sharedPreferences.getBoolean("KEY_isLoggedin", false);
                    if (isLoggedin) {
                        Intent intent = new Intent(Splash.this, Home.class);
                        startActivity(intent);
                    } else {
                        Intent mainIntent = new Intent(Splash.this, Login.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }
                //else {
                //  Intent mainIntent = new Intent(Splash_Activity.this, Registration_Activity.class);
                //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //      startActivity(mainIntent);
                //    finish();
                //}

                /* Create an Intent that will start the Menu-Activity. */

                //}
            }, SPLASH_DISPLAY_LENGTH);



    }

}

