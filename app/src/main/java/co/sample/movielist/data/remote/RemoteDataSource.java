package co.sample.movielist.data.remote;


import android.util.Log;

import java.util.Iterator;
import java.util.List;
import co.sample.movielist.data.DataSource;
import co.sample.movielist.model.Movie;
import co.sample.movielist.model.MovieResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nivedita on 07/04/18.
 */

public class RemoteDataSource extends DataSource {

    private static RemoteDataSource instance;
    private final static String API_KEY = "2b4e368cec70d05715e0fe8ee5a1fadb";
    public final String TAG = getClass().getSimpleName();

    public static RemoteDataSource getInstance() {
        if (instance == null) {
            instance = new RemoteDataSource();
        }
        return instance;
    }

    @Override
    public void getTopRatedMovies(int page, final GetMoviesCallback callback) {
        Call<MovieResponse> call = ApiClient.getClient().getTopRatedMovies(API_KEY, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                callback.onSuccess(movieResponse);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(new Throwable());
            }
        });
    }

    @Override
    public void getPopularMovies(int page, final GetMoviesCallback callback) {
        Call<MovieResponse> call = ApiClient.getClient().getPopularMovies(API_KEY, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                MovieResponse movieResponse = response.body();
                callback.onSuccess(movieResponse);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(new Throwable());
            }
        });
    }

    @Override
    public void getExistingMovies(GetMoviesCallback callback) {

    }

    @Override
    public void saveMovies(List<Movie> movies) {

    }

    @Override
    public void saveFavourites(Movie movie, boolean isLiked, String movieId) {

    }

    @Override
    public void isLiked(String movieId, GetIsLikedCallback callback) {

    }


}
