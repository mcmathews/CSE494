package edu.asu.mcmathe1.bscs.movielibrarydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlliteMovieLibraryDaoImpl implements MovieLibraryDao {

    private SqliteMovieHelper sqliteHelper;

    public SqlliteMovieLibraryDaoImpl(SqliteMovieHelper sqliteHelper) {
        this.sqliteHelper = sqliteHelper;
    }

    @Override
    public void add(MovieDescription movie) throws IOException {
	    SQLiteDatabase db = null;

	    try {
			db = getWritableDatabase();
		    db.beginTransaction();

		    ContentValues cv = new ContentValues();
		    cv.put("title", movie.getTitle());
		    cv.put("year", movie.getYear());
		    cv.put("rated", movie.getRated());
		    cv.put("released", movie.getReleased());
		    cv.put("runtime", movie.getRuntime());
		    cv.put("plot", movie.getPlot());

		    db.insert("movies", null, cv);

		    for (String genre : movie.getGenres()) {
			    cv = new ContentValues();
			    cv.put("title", movie.getTitle());
			    cv.put("genre", genre);

			    db.insert("movie_genres", null, cv);
		    }

		    for (String actor : movie.getActors()) {
			    cv = new ContentValues();
			    cv.put("title", movie.getTitle());
			    cv.put("actor", actor);

			    db.insert("movie_actors", null, cv);
		    }

		    db.setTransactionSuccessful();

	    } catch (Exception e) {
		    throw new RuntimeException(e);
	    } finally {
		    cleanup(db, null);
	    }
    }

    @Override
    public void edit(String title, MovieDescription movie) throws IOException {
	    SQLiteDatabase db = null;

	    try {
		    db = getWritableDatabase();
		    db.beginTransaction();

		    ContentValues cv = new ContentValues();
		    cv.put("title", movie.getTitle());
		    cv.put("year", movie.getYear());
		    cv.put("rated", movie.getRated());
		    cv.put("released", movie.getReleased());
		    cv.put("runtime", movie.getRuntime());
		    cv.put("plot", movie.getPlot());

		    db.update("movies", cv, "title = ?", new String[]{title});

		    db.delete("movie_genres", "title = ?", new String[]{title});
		    for (String genre : movie.getGenres()) {
			    cv = new ContentValues();
			    cv.put("title", movie.getTitle());
			    cv.put("genre", genre);

			    db.insert("movie_genres", null, cv);
		    }

		    db.delete("movie_actors", "title = ?", new String[]{title});
		    for (String actor : movie.getActors()) {
			    cv = new ContentValues();
			    cv.put("title", movie.getTitle());
			    cv.put("actor", actor);

			    db.insert("movie_actors", null, cv);
		    }

		    db.setTransactionSuccessful();

	    } catch (Exception e) {
		    throw new RuntimeException(e);
	    } finally {
		    cleanup(db, null);
	    }
    }

    @Override
    public void remove(String title) throws IOException {
	    SQLiteDatabase db = null;

	    try {
			db = getWritableDatabase();
		    db.beginTransaction();

		    db.delete("movies", "title = ?", new String[] { title });
		    db.delete("movie_genres", "title = ?", new String[] { title });
		    db.delete("movie_actors", "title = ?", new String[] { title });

		    db.setTransactionSuccessful();

	    } catch (Exception e) {
		    throw new RuntimeException(e);
	    } finally {
		    cleanup(db, null);
	    }
    }

    @Override
    public MovieDescription get(String title) throws IOException {
	    SQLiteDatabase db = null;
	    Cursor cursor = null;

	    try {
		    db = getReadableDatabase();

		    cursor = db.query("movies", null, "title = ?", new String[] { title }, null, null, null);

		    MovieDescription movie = new MovieDescription();
		    if (!cursor.moveToFirst()) {
			    throw new RuntimeException("Movie with title [" + title + "] not found");
		    }
		    Log.w(getClass().getSimpleName(), "Movie with title [" + title + "] found");

		    movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
		    movie.setYear(cursor.getInt(cursor.getColumnIndexOrThrow("year")));
		    movie.setRated(cursor.getString(cursor.getColumnIndexOrThrow("rated")));
		    movie.setReleased(cursor.getString(cursor.getColumnIndexOrThrow("released")));
		    movie.setRuntime(cursor.getString(cursor.getColumnIndexOrThrow("runtime")));
		    movie.setPlot(cursor.getString(cursor.getColumnIndexOrThrow("plot")));

		    cursor.close();

		    cursor = db.query("movie_genres", new String[] { "genre" }, "title = ?", new String[] { title }, null, null, null);
		    List<String> genres = new ArrayList<>();
		    if (cursor.moveToFirst()) {
			    do {
				    genres.add(cursor.getString(cursor.getColumnIndexOrThrow("genre")));
			    } while (cursor.moveToNext());
		    }
		    Log.w(getClass().getSimpleName(), "Found genres for title [" + title + "]: " + genres);
		    movie.setGenres(genres);

		    cursor.close();

		    cursor = db.query("movie_actors", new String[] { "actor" }, "title = ?", new String[] { title }, null, null, null);
		    List<String> actors = new ArrayList<>();
		    if (cursor.moveToFirst()) {
			    do {
				    actors.add(cursor.getString(cursor.getColumnIndexOrThrow("actor")));
			    } while (cursor.moveToNext());
		    }
		    Log.w(getClass().getSimpleName(), "Found actors for title [" + title + "]: " + genres);
		    movie.setActors(actors);

		    return movie;

	    } catch (Exception e) {
		    throw new RuntimeException(e);
	    } finally {
		    cleanup(db, cursor);
	    }
    }

    @Override
    public List<String> getTitles() throws IOException {
	    SQLiteDatabase db = null;
	    Cursor cursor = null;

	    try {
		    db = getReadableDatabase();

		    String[] columns = { "title" };
		    cursor = db.query("movies", columns, null, null, null, null, "created asc");

		    List<String> titles = new ArrayList<>();
		    if (cursor.moveToFirst()) {
			    do {
				    titles.add(cursor.getString(cursor.getColumnIndexOrThrow("title")));
			    } while (cursor.moveToNext());
		    }

		    Log.w(getClass().getSimpleName(), "Titles retrieved from db: " + titles.toString());
		    return titles;

	    } catch (Exception e) {
		    throw new RuntimeException(e);
	    } finally {
			cleanup(db, cursor);
	    }
    }

    protected SQLiteDatabase getReadableDatabase() {
        return sqliteHelper.getReadableDatabase();
    }

    protected SQLiteDatabase getWritableDatabase() {
        return sqliteHelper.getWritableDatabase();
    }

	protected static void cleanup(SQLiteDatabase db, Cursor cursor) {
		if (db != null) {
			if (db.inTransaction()) {
				db.endTransaction();
			}
		}

		if (cursor != null) {
			cursor.close();
		}
	}
}
