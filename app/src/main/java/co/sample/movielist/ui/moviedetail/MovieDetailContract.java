package co.sample.movielist.ui.moviedetail;



import java.util.List;

import co.sample.movielist.BasePresenter;
import co.sample.movielist.BaseView;
import co.sample.movielist.model.Movie;

/**
 * Created by nivedita on 07/04/18.
 */

public interface MovieDetailContract {

    interface View extends BaseView<Presenter> {
        void showLiked(boolean isLiked);
    }

    interface Presenter extends BasePresenter {
        String formatDate(String releaseDate);
        void markAsFavourite(Movie movie, boolean isLiked);
        void getIsLiked(String movieId);
    }
}