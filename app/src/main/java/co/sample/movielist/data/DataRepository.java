package co.sample.movielist.data;

import android.content.Context;
import android.util.Log;

import java.util.List;

import co.sample.movielist.data.local.LocalDataSource;
import co.sample.movielist.data.remote.RemoteDataSource;
import co.sample.movielist.model.Movie;
import co.sample.movielist.ui.moviegrid.MovieFilterType;
import co.sample.movielist.util.NetworkHelper;

/**
 * Created by nivedita on 07/04/18.
 */

public class DataRepository{
    private DataSource remoteDataSource;
    private DataSource localDataSource;
    public final String TAG = getClass().getSimpleName();

    private static DataRepository dataRepository;
    Context context;


    public DataRepository(Context context) {
        this.remoteDataSource = RemoteDataSource.getInstance();
        this.localDataSource = LocalDataSource.getInstance();
        this.context = context;

    }

    public static synchronized DataRepository getInstance(Context context) {
        if (dataRepository == null) {
            dataRepository = new DataRepository(context);
        }
        return dataRepository;
    }

    public void getMovies(int page, MovieFilterType filterType, DataSource.GetMoviesCallback callback) {
        if(!NetworkHelper.isNetworkAvailable(context))
            callback.onNetworkFailure();
        else{
            if (filterType == MovieFilterType.TOP_MOVIES) {
                remoteDataSource.getTopRatedMovies(page, callback);
            }
            if (filterType == MovieFilterType.POPULAR_MOVIES) {
                Log.d(TAG, "Fetching popular movies now");
                remoteDataSource.getPopularMovies(page, callback);
            }
        }
    }


    public void saveFavourites(Movie movie, boolean liked, String movieId) {
        localDataSource.saveFavourites(movie, liked, movieId);
    }

    public void isLiked(String movieId, DataSource.GetIsLikedCallback callback) {
         localDataSource.isLiked(movieId, callback);

    }

}
