package co.sample.movielist.data.remote;

/**
 * Created by nivedita on 08/04/18.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static ApiInterface apiService = null;


    public static ApiInterface getClient() {
        if (apiService==null) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            apiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build().create(ApiInterface.class);

        }
        return apiService;
    }
}