package ua.mainacad.maintest.maintest;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends Application {

    private static MyApp instance;

    public static MyApp get() {
        return instance;
    }

    private Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }


}
