package co.sample.movielist.ui.moviegrid;


import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.sample.movielist.data.DataRepository;
import co.sample.movielist.data.DataSource;
import co.sample.movielist.model.MovieResponse;

/**
 * Created by nivedita on 07/04/18.
 */

public class MoviePresenter implements MovieContract.Presenter {

    MovieContract.View mMovieView;
    DataRepository dataRepository;
    public final  String TAG = getClass().getSimpleName();
    private int CURRENT_PAGE = 1;
    private int TOTAL_PAGE;
    MovieFilterType movieFilterType = MovieFilterType.TOP_MOVIES;

    public MoviePresenter(Context context, MovieContract.View movieView) {
        mMovieView = movieView;
        mMovieView.setPresenter(this);
        dataRepository = DataRepository.getInstance(context);
    }

    @Override
    public void start() {
        getMovies();
    }

    public void getMovies() {

        dataRepository.getMovies(CURRENT_PAGE, movieFilterType, new DataSource.GetMoviesCallback() {
            @Override
            public void onSuccess(MovieResponse movieResponse) {
                TOTAL_PAGE = movieResponse.getTotalPages();
                mMovieView.showMovies(movieResponse.getResults());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "Failed movies");
            }

            @Override
            public void onNetworkFailure() {

            }
        });
    }

    @Override
    public void loadNextBatch() {
        Log.d(TAG, "Total pages are " + TOTAL_PAGE);
        if(CURRENT_PAGE < TOTAL_PAGE) {
            CURRENT_PAGE += 1;
            getMovies();
        }
    }

    @Override
    public void setFilterType(MovieFilterType filterType) {
        movieFilterType = filterType;
        CURRENT_PAGE = 1;
        getMovies();
    }


}