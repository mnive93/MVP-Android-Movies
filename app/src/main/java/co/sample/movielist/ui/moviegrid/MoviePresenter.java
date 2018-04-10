package co.sample.movielist.ui.moviegrid;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import co.sample.movielist.data.DataRepository;
import co.sample.movielist.data.DataSource;
import co.sample.movielist.model.MovieResponse;
import co.sample.movielist.util.NetworkHelper;

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
    Context context;

    public MoviePresenter(Context context, MovieContract.View movieView) {
        mMovieView = movieView;
        this.context = context;
        mMovieView.setPresenter(this);
        dataRepository = DataRepository.getInstance(context);
        context.registerReceiver(new NetworkBroadcastReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
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

                if(CURRENT_PAGE == 1 && NetworkHelper.isNetworkAvailable(context))
                    dataRepository.saveMovies(movieResponse.getResults());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.d(TAG, "Failed movies");
            }

            @Override
            public void onNetworkFailure() {
                mMovieView.isOffline();
            }
        });
    }

    @Override
    public void loadNextBatch() {
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

    @Override
    public void onConnectionChanged(boolean isOnline) {
        if(!isOnline) {
            mMovieView.isOffline();
        } else {
            mMovieView.isOnline();
        }
    }


    private class NetworkBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            final android.net.NetworkInfo wifi = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            final android.net.NetworkInfo mobile = connMgr
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (wifi.isAvailable() || mobile.isAvailable()) {
                return;
            }
            onConnectionChanged(false);
        }
    }


}