package edu.asu.mcmathe1.bscs.movielibrarydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.List;

public class SqlLiteMovieLibraryDaoImpl extends SQLiteOpenHelper implements MovieLibraryDao {

    public SqlLiteMovieLibraryDaoImpl(Context ctx) {

    }

    @Override
    public boolean add(MovieDescription movie) throws IOException {
        return false;
    }

    @Override
    public boolean edit(String title, MovieDescription movie) throws IOException {
        return false;
    }

    @Override
    public boolean remove(String title) throws IOException {
        return false;
    }

    @Override
    public MovieDescription get(String title) throws IOException {
        return null;
    }

    @Override
    public List<String> getTitles() throws IOException {
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }
}
