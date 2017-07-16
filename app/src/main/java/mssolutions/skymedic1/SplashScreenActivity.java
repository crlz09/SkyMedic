package mssolutions.skymedic1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
//import app.AppController;
import java.util.Timer;
import java.util.TimerTask;



//yeison//


//IMPORTS NUEVOS PARA VOLLEY Y MAIL
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//fin yeison import
/**
 * Created by marci on 24/5/2017.
 */

public class SplashScreenActivity extends Activity {


    private static final long SPLASH_SCREEN_DELAY = 3000;


    /**
     * Method to make json array request where response starts with [
     * */


    // Progress dialog
    private ProgressDialog pDialog;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestWindowFeature(Window.FEATURE_NO_TITLE);



        setContentView(R.layout.splash_screen);

        //aqui llamo  Json

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


