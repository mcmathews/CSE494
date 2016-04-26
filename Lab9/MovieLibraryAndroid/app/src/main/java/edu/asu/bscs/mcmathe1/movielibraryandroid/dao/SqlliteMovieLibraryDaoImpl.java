package edu.asu.bscs.mcmathe1.movielibraryandroid.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.asu.bscs.mcmathe1.movielibraryandroid.MovieDescription;

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
		    cv.put("filename", movie.getFilename());
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
		    cv.put("filename", movie.getFilename());
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
		    movie.setFilename(cursor.getString(cursor.getColumnIndexOrThrow("filename")));
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
