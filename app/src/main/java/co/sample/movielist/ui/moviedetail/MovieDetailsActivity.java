package co.sample.movielist.ui.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import co.sample.movielist.R;

import static co.sample.movielist.R.id.toolbar;

public class MovieDetailsActivity extends AppCompatActivity {

    MovieDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Bundle bundle = new Bundle();
        bundle.putString("movie", getIntent().getStringExtra("movie"));
        MovieDetailFragment movieDetailFragment = MovieDetailFragment.getInstance();
        movieDetailFragment.setArguments(bundle);
        mPresenter = new MovieDetailPresenter(getApplicationContext(), movieDetailFragment);
        getFragmentManager().beginTransaction().replace(R.id.container, movieDetailFragment).commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}



