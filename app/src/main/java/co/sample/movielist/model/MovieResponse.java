package co.sample.movielist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nivedita on 09/04/18.
 */

public class MovieResponse {
    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private List<Movie> results;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public MovieResponse(int page, List<Movie> results, int totalPages) {
        this.page = page;
        this.results = results;
        this.totalPages = totalPages;
    }

    public int getPage() {

        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
