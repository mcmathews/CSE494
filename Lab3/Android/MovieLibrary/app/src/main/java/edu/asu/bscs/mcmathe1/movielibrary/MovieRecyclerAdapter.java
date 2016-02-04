package edu.asu.bscs.mcmathe1.movielibrary;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;



public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private MovieLibrary library;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitle;

        public ViewHolder(TextView v){
            super(v);
            movieTitle = v;
        }
    }

    public MovieRecyclerAdapter(MovieLibrary library) {
        this.library = library;
    }

    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        ViewHolder vh = new MovieRecyclerAdapter.ViewHolder(new TextView(parent.getContext()));
        try {
            View movieView = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_text_view, parent, false);
            TextView atv = (TextView) movieView.findViewById(R.id.hwTV);
            vh = new ViewHolder(atv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.movieTitle.setText(library.getMovieDescriptions().get(position).getTitle());
    }

    @Override
    public int getItemCount(){
        return library.getMovieDescriptions().size();
    }
}
