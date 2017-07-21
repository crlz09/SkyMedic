package mssolutions.skymedic1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by marci on 24/5/2017.
 */

public class SplashScreenActivity extends Activity {


    private static final long SPLASH_SCREEN_DELAY = 2500;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestWindowFeature(Window.FEATURE_NO_TITLE);



        setContentView(R.layout.splash_screen);

                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {

                        // Start the next activity
                        Intent mainIntent = new Intent().setClass(
                                SplashScreenActivity.this, MainActivity.class);
                        startActivity(mainIntent);

                        // Close the activity so the user won't able to go back this
                        // activity pressing Back button
                        finish();
                    }
                };

                // Simulate a long loading process on application startup.
                Timer timer = new Timer();
                timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

}


