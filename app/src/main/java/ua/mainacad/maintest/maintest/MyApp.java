package ua.mainacad.maintest.maintest;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.mainacad.maintest.maintest.api.Api;
import ua.mainacad.maintest.maintest.database.AppDatabase;
import ua.mainacad.maintest.maintest.database.DbHelper;

public class MyApp extends Application {

    private static MyApp instance;

    public static MyApp get() {
        return instance;
    }


    private Api.Get api;
    private DbHelper sqlDb;
    private AppDatabase roomDb;
    private FirebaseDatabase firebaseDb;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        api = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(Api.Get.class);
        sqlDb = new DbHelper(this);
        roomDb = Room.databaseBuilder(this, AppDatabase.class, "firebaseDb-name").build();
        firebaseDb = FirebaseDatabase.getInstance();
    }

    public Api.Get getApi() {
        return api;
    }


    public DbHelper getSqlDatabase() {
        return sqlDb;
    }



    public AppDatabase getRoomDatabase() {
        return roomDb;
    }

    public FirebaseDatabase getFirebaseDb() {
        return firebaseDb;
    }
}
