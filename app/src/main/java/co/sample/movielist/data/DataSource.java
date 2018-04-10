package co.sample.movielist.data;

import java.util.List;

import co.sample.movielist.model.Movie;
import co.sample.movielist.model.MovieResponse;

/**
 * Created by nivedita on 07/04/18.
 */

public abstract class DataSource {
    public interface GetMoviesCallback {
        void onSuccess(MovieResponse response);

        void onFailure(Throwable throwable);

        void onNetworkFailure();

    }

    public interface GetIsLikedCallback {
        void isLiked(boolean isLiked);
    }

    public abstract void getTopRatedMovies(int page, GetMoviesCallback callback);

    public abstract void getPopularMovies(int page, GetMoviesCallback callback);


    public abstract void saveFavourites(Movie movie, boolean isLiked, String movieId);

    public abstract void isLiked(String movieId, GetIsLikedCallback callback);

}
