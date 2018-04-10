package co.sample.movielist.ui.moviegrid;



import java.util.List;

import co.sample.movielist.BasePresenter;
import co.sample.movielist.BaseView;
import co.sample.movielist.model.Movie;

/**
 * Created by nivedita on 07/04/18.
 */

public interface MovieContract {

    interface View extends BaseView<Presenter> {

        void showMovies(List<Movie> movies);
        void isOffline();
    }

    interface Presenter extends BasePresenter {
        void loadNextBatch();
        void setFilterType(MovieFilterType filterType);
        void onConnectionChanged(boolean isOnline);
    }
}