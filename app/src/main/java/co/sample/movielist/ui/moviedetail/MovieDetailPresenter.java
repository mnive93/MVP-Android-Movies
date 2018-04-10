package co.sample.movielist.ui.moviedetail;


import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.sample.movielist.data.DataRepository;
import co.sample.movielist.data.DataSource;
import co.sample.movielist.model.Movie;
import co.sample.movielist.model.MovieResponse;

/**
 * Created by nivedita on 07/04/18.
 */

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

    MovieDetailContract.View mMovieView;
    DataRepository dataRepository;
    public final  String TAG = getClass().getSimpleName();


    public MovieDetailPresenter(Context context, MovieDetailContract.View movieDetailView) {
        mMovieView = movieDetailView;
        mMovieView.setPresenter(this);
        dataRepository = DataRepository.getInstance(context);
    }

    @Override
    public void start() {
    }


    @Override
    public String formatDate(String releaseDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(releaseDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return outputDate;
    }

    @Override
    public void markAsFavourite(Movie movie, boolean isLiked) {
        dataRepository.saveFavourites(movie, isLiked, String.valueOf(movie.getMovieId()));
    }

    @Override
    public void getIsLiked(String movieId) {

        dataRepository.isLiked(movieId, new DataSource.GetIsLikedCallback() {
            @Override
            public void isLiked(boolean isLiked) {
                Log.d(TAG,  "Isliked" + isLiked);
                mMovieView.showLiked(isLiked);
            }
        });
    }


}