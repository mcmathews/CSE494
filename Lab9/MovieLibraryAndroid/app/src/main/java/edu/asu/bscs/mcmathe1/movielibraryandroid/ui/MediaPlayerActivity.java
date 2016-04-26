package edu.asu.bscs.mcmathe1.movielibraryandroid.ui;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import edu.asu.bscs.mcmathe1.movielibraryandroid.R;

/**
 * Copyright 2016 Michael Mathews
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Michael Mathews    mailto:Michael.C.Mathews@asu.edu
 * @version 4/26/16
 */
public class MediaPlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_player);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			throw new IllegalArgumentException("extras a required");
		}

		String filename = extras.getString(LibraryActivity.MOVIE_FILENAME_KEY);
		if (filename == null) {
			throw new IllegalArgumentException("filename cannot be null");
		}

		videoView = (VideoView) findViewById(R.id.video_player);
		videoView.setVideoPath("http://" + getString(R.string.host) + ":" + getString(R.string.media_port) + "/" + filename);

		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		videoView.setMediaController(mediaController);

		videoView.setOnPreparedListener(this);
	}

	public void onPrepared(MediaPlayer mp){
		Log.w(this.getClass().getSimpleName(), "Video Duration: " + videoView.getDuration());
		videoView.start();
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}
}
