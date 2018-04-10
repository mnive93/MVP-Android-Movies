package co.sample.movielist.data.local;

import java.util.List;

import co.sample.movielist.data.DataSource;
import co.sample.movielist.data.remote.RemoteDataSource;
import co.sample.movielist.model.Favourites;
import co.sample.movielist.model.Movie;
import co.sample.movielist.model.MovieResponse;
import co.sample.movielist.util.MainUiThread;

import static android.R.attr.author;
import static android.R.attr.thickness;
import static okhttp3.internal.Internal.instance;

/**
 * Created by nivedita on 10/04/18.
 */

public class LocalDataSource extends DataSource {

    private static LocalDataSource instance;
    private MainUiThread uiThread;

    public LocalDataSource(MainUiThread mainUiThread) {
        uiThread = mainUiThread;
    }

    public static LocalDataSource getInstance(MainUiThread uiThread) {
        if (instance == null) {
            instance = new LocalDataSource(uiThread);
        }
        return instance;
    }


    public Favourites getFavMovie(String movieId){
        List<Favourites> favourites = Favourites.find(Favourites.class, "movie_id = ?", movieId);
        if(favourites.size() > 0)
            return favourites.get(0);
        return null;
    }

    @Override
    public void getTopRatedMovies(int page, GetMoviesCallback callback) {

    }

    @Override
    public void getPopularMovies(int page, GetMoviesCallback callback) {

    }

    @Override
    public void getExistingMovies(GetMoviesCallback callback) {
        long count = Movie.count(Movie.class, null, null);
        if( count > 0 ) {
            List<Movie> movies = Movie.listAll(Movie.class);
            MovieResponse response = new MovieResponse(1, movies, 1);
            callback.onSuccess(response);
        }

    }

    @Override
    public void saveMovies(final List<Movie> movies) {

        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                Movie.deleteAll(Movie.class);
                for(Movie movie : movies) {
                    movie.save();
                }
            }
        };

        new Thread(saveRunnable).start();
    }


    @Override
    public void saveFavourites(Movie movie, boolean isLiked, String movieID) {
        if(!isLiked){
                Favourites fav = getFavMovie(movieID);
                if(fav != null)
                    fav.delete();
        } else {
            Favourites favourites = new Favourites(movie, isLiked, movieID);
            favourites.save();
        }
    }

    @Override
    public void isLiked(String movieId, GetIsLikedCallback callback) {

            Favourites fav = getFavMovie(movieId);
            if(fav != null)
               callback.isLiked(fav.isLiked());
            else
                callback.isLiked(false);
    }
}
