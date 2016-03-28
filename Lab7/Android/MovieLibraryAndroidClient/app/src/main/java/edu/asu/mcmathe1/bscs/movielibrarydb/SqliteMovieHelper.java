package edu.asu.mcmathe1.bscs.movielibrarydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
