package edu.asu.mcmathe1.bscs.movielibrarydb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlLiteMovieLibraryDaoImpl implements MovieLibraryDao {

    private SqliteMovieHelper sqliteHelper;

    public SqlLiteMovieLibraryDaoImpl(Context ctx) {
        sqliteHelper = new SqliteMovieHelper(ctx);
    }

    @Override
    public void add(MovieDescription movie) throws IOException {
    }

    @Override
    public void edit(String title, MovieDescription movie) throws IOException {
    }

    @Override
    public void remove(String title) throws IOException {
    }

    @Override
    public MovieDescription get(String title) throws IOException {
        return null;
    }

    @Override
    public List<String> getTitles() throws IOException {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = { "title" };
        Cursor cursor = db.query("movies", columns, null, null, null, null, null);
        cursor.moveToFirst();

        List<String> titles = new ArrayList<>();
        while (cursor.moveToNext()) {
            titles.add(cursor.getString());
        }
    }

    protected SQLiteDatabase getReadableDatabase() {
        return sqliteHelper.getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDatabase() {
        return sqliteHelper.getWritableDatabase();
    }
}
