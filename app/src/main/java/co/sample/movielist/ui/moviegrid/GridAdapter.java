package co.sample.movielist.ui.moviegrid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.sample.movielist.R;
import co.sample.movielist.model.Movie;
import co.sample.movielist.ui.moviedetail.MovieDetailsActivity;
import co.sample.movielist.util.FileHelper;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by nivedita on 07/04/18.
 */

public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.MovieHolder>  {
    private List<Movie> moviesList;
    Context context;
    boolean shouldClear;

    public final String TAG = getClass().getSimpleName();
    public GridAdapter(Context context) {
        this.context = context;
        moviesList = new ArrayList<>();
    }

    @Override
    public GridAdapter.MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.view_movie_grid, parent, false);

        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridAdapter.MovieHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.bindData(movie);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public void setShouldClear(boolean shouldClear) {
        this.shouldClear = shouldClear;
    }

    public void updateMoviesList(List<Movie> movies){


        if(shouldClear) {
            this.moviesList.clear();
            shouldClear = false;
            this.moviesList.addAll(movies);
            notifyDataSetChanged();
        } else {
            int curSize = getItemCount();
            this.moviesList.addAll(movies);
            notifyItemRangeInserted(curSize, movies.size());
        }


    }
    class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.movie_poster)
        ImageView moviePoster;


        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bindData(Movie movie) {

            FileHelper.loadImage(moviePoster, movie.getPosterPath());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = moviesList.get(position);
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("movie", movie.toJSONString());
            context.startActivity(intent);
        }
    }
}
