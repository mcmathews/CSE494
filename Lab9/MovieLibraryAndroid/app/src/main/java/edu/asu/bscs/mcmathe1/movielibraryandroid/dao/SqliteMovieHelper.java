package edu.asu.bscs.mcmathe1.movielibraryandroid.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
public class SqliteMovieHelper extends SQLiteOpenHelper {

    private Context ctx;

    public SqliteMovieHelper(Context ctx) {
        super(ctx, "movies.db", null, 1);

        this.ctx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
	    try {
		    List<String> createStatements = new ArrayList<>();
		    StringBuilder sqlBuilder = new StringBuilder();
		    try (BufferedReader r = new BufferedReader(new InputStreamReader(ctx.getResources().openRawResource(R.raw.movies)))) {
			    String line;
			    while ((line = r.readLine()) != null) {
				    sqlBuilder.append(line).append('\n');

				    if (line.endsWith(";")) {
					    Log.w(getClass().getSimpleName(), "Sql statement: " + sqlBuilder.toString());
					    createStatements.add(sqlBuilder.toString());
					    sqlBuilder = new StringBuilder();
				    }
			    }
		    }

		    for (String statement : createStatements) {
			    db.execSQL(statement);
		    }

	    } catch (IOException e) {
		    throw new RuntimeException(e);
	    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);

		Log.w(getClass().getSimpleName(), "onOpen called");
	}
}
