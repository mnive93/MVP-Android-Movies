package co.sample.movielist.ui.moviegrid;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.sample.movielist.R;
import co.sample.movielist.model.Movie;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by nivedita on 07/04/18.
 */

public class MovieFragment extends Fragment implements MovieContract.View {
    private MovieContract.Presenter mPresenter;

    @BindView(R.id.details_rv)
    RecyclerView detailsRV;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public GridAdapter gridAdapter;
    View root;
    RecyclerScrollListener scrollListener;
    public final String TAG = getClass().getSimpleName();

    public static MovieFragment newInstance() {
        return new MovieFragment();
    }


    @Override
    public void setPresenter(@NonNull MovieContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showMovies(List<Movie> movies) {
        if(progressBar.getVisibility() == View.VISIBLE)
            progressBar.setVisibility(View.GONE);

        gridAdapter.updateMoviesList(movies);
    }

    @Override
    public void isOffline() {
        Snackbar offlineBar = Snackbar.make(root.findViewById(R.id.container),
                R.string.offline_text, Snackbar.LENGTH_LONG);
        offlineBar.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_movie_grid, container, false);
        ButterKnife.bind(this, root);
        gridAdapter = new GridAdapter(getActivity());
        detailsRV.setAdapter(gridAdapter);
        detailsRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        scrollListener  = new RecyclerScrollListener();
        detailsRV.addOnScrollListener(scrollListener);
        mPresenter.start();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_top_movies:
                gridAdapter.setShouldClear(true);
                scrollListener.init();
                mPresenter.setFilterType(MovieFilterType.TOP_MOVIES);

                return true;
            case R.id.action_popular_movies:
                gridAdapter.setShouldClear(true);
                scrollListener.init();
                mPresenter.setFilterType(MovieFilterType.POPULAR_MOVIES);

                return true;
        }
        return false;
    }

    class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        int previousTotal;
        int visibleThreshold;
        boolean loading;

        public void init() {
            previousTotal = 0;
            visibleThreshold = 5;
            loading = false;
        }

        public RecyclerScrollListener() {
            super();
            init();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = gridAdapter.getItemCount();
            int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

            if (loading && totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }

            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    mPresenter.loadNextBatch();
                    loading = true;
            }
        }
    }

}
