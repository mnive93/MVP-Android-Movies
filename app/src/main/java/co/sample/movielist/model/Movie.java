package co.sample.movielist.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by nivedita on 06/04/18.
 */

public class Movie extends SugarRecord<Movie> {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    private transient Long id = null;
    @SerializedName("id")
    @Expose
    private Integer movieId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;


    public float getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie(){

    }

    public Movie(String posterPath, Integer id, String title, String overview, String backdropPath, float voteAverage, String releaseDate) {

        this.posterPath = posterPath;
        this.movieId = id;
        this.title = title;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {

        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String toJSONString() {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }

    public static Movie fromStringToGson(String gsonString){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(gsonString, Movie.class);
    }

    public Integer getMovieId() {
        return movieId;
    }
}
