package edu.asu.bscs.mcmathe1.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mcmathe1.bscs.asu.edu.lifecycle.R;

public class AlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        logMethodName("onCreate()");
    }

    @Override
         protected void onRestart() {
        super.onRestart();

        logMethodName("onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();

        logMethodName("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        logMethodName("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        logMethodName("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        logMethodName("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        logMethodName("onDestroy()");
    }

    private void logMethodName(String methodName) {
        Log.w(this.getClass().getSimpleName(), methodName);
    }
}
