package edu.asu.mcmathe1.bscs.movielibraryandroidclient;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
 * @author Michael Mathews    mailto:Michael.C.Mathews@asu.edu
 * @version 2/26/2016
 */
public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> {

    private List<String> movieTitles;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView movieListLayout;

        public ViewHolder(CardView movieListLayout){
            super(movieListLayout);
            this.movieListLayout = movieListLayout;
        }
    }

    public MovieRecyclerAdapter(List<String> movieTitles) {
        this.movieTitles = movieTitles;
    }

    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
	    CardView listMovieLayout = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_list, parent, false);
	    return new ViewHolder(listMovieLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
	    TextView movieTitleView = (TextView) holder.movieListLayout.findViewById(R.id.movie_list_title);
	    movieTitleView.setText(movieTitles.get(position));
    }

    @Override
    public int getItemCount(){
        return movieTitles.size();
    }
}
