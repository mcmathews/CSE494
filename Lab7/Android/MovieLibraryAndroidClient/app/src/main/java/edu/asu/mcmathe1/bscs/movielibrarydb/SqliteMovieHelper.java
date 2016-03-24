package edu.asu.mcmathe1.bscs.movielibrarydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Scanner;

public class SqliteMovieHelper extends SQLiteOpenHelper {

    private Context ctx;

    public SqliteMovieHelper(Context ctx) {
        super(ctx, "movies.db", null, 1);

        this.ctx = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(new Scanner(ctx.getResources().openRawResource(R.raw.movies)).useDelimiter("\\A").next());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
