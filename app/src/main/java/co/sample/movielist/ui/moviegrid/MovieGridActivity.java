package co.sample.movielist.ui.moviegrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import co.sample.movielist.R;

public class MovieGridActivity extends AppCompatActivity {

    MoviePresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MovieFragment mFragment = MovieFragment.newInstance();
        mPresenter = new MoviePresenter(getApplicationContext(), mFragment);
        getFragmentManager().beginTransaction().replace(R.id.container, mFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
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
