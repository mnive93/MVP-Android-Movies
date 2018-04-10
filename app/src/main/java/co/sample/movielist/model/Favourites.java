package co.sample.movielist.model;

import com.orm.SugarRecord;

/**
 * Created by nivedita on 09/04/18.
 */

public class Favourites extends SugarRecord<Favourites> {
    Movie movie;
    boolean liked;
    String movieId;

    public Favourites() {

    }

    public Favourites(Movie movie, boolean liked, String movieId) {
        this.movie = movie;
        this.liked = liked;
        this.movieId = movieId;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
}
