package edu.asu.bscs.mcmathe1.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import mcmathe1.bscs.asu.edu.lifecycle.R;

/**
 * Copyright 2016 Michael Mathews
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author   Michael Mathews    mailto:Michael.C.Mathews@asu.edu
 * @version Jan 26, 2016
 */
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

	protected void handleButtonPress() {
		Log.w(this.getClass().getSimpleName(), "Ok button pressed");

		finish();
	}

    private void logMethodName(String methodName) {
        Log.w(this.getClass().getSimpleName(), methodName);
    }
}
