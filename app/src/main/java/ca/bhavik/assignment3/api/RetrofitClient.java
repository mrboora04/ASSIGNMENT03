package ca.bhavik.assignment3.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://www.omdbapi.com/";
    private static Retrofit retrofit = null;

    public static MovieApi getClient(String url) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MovieApi.class);
    }

    public static String getApiKey() {
        return "2a670738";
    }
}