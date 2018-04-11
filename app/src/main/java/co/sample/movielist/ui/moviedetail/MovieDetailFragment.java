package co.sample.movielist.ui.moviedetail;

import android.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.sample.movielist.R;
import co.sample.movielist.model.Movie;
import co.sample.movielist.util.FileHelper;

import static co.sample.movielist.R.id.fav_movie;
import static co.sample.movielist.R.id.toolbar;
import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;


/**
 * Created by nivedita on 09/04/18.
 */

public class MovieDetailFragment extends Fragment implements MovieDetailContract.View{
    public static MovieDetailFragment getInstance() {
        return new MovieDetailFragment();
    }

    @BindView(R.id.poster_image)
    ImageView posterImage;

    @BindView(R.id.backdrop_image)
    ImageView backDropImage;
    @BindView(R.id.overview_text)
    TextView overView;
    @BindView(R.id.fav_movie)
    FloatingActionButton favMovie;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.movie_release)
    TextView movieRelease;
    @BindView(R.id.movie_rating)
    TextView movieRating;
    MovieDetailContract.Presenter mPresenter;
    boolean isLiked;
    Movie movie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, root);

        String movieString = getArguments().getString("movie");
        movie = Movie.fromStringToGson(movieString);
        FileHelper.loadImage(backDropImage, movie.getBackdropPath());
        FileHelper.loadImage(posterImage, movie.getPosterPath());
        overView.setText(movie.getOverview());
        collapsingToolbarLayout.setTitleEnabled(false);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle(movie.getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOnBackPress();
            }
        });
        movieRelease.setText(mPresenter.formatDate(movie.getReleaseDate()));
        movieRating.setText(movie.getVoteAverage() + "");
        mPresenter.getIsLiked(String.valueOf(movie.getMovieId()));

        return root;

    }

    public void handleOnBackPress() {
        getActivity().finish();
    }

    @Override
    public void setPresenter(MovieDetailContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @OnClick(R.id.fav_movie)
    public void favouriteMovie(){
        if(isLiked) {
            favMovie.setImageResource(R.drawable.ic_star_border_white);
            isLiked = false;
        }else{
            favMovie.setImageResource(R.drawable.ic_star_white);
            isLiked = true;
        }
        mPresenter.markAsFavourite(movie, isLiked);
    }



    @Override
    public void showLiked(boolean isLiked) {
        this.isLiked = isLiked;
        if(isLiked)
            favMovie.setImageResource(R.drawable.ic_star_white);
        else
            favMovie.setImageResource(R.drawable.ic_star_border_white);

    }
}
