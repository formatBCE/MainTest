package ua.mainacad.maintest.maintest;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.mainacad.maintest.maintest.database.AppDatabase;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.List;

public class Utils {

    public static boolean isEmailValid(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (email.trim().isEmpty()) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }
        return email.indexOf("@") == email.lastIndexOf("@");
    }

    public static void loadPostsFromDb(
            AppDatabase db,
            ICallback<List<Post>> callback) {
        loadPostsFromDb(db, callback, Schedulers.io(), AndroidSchedulers.mainThread());
    }

    public static void loadPostsFromDb(
            AppDatabase db,
            ICallback<List<Post>> callback,
            Scheduler workScheduler,
            Scheduler mainThreadScheduler) {
        Single.just(db.postDao().getAllSynchronously())
                .subscribeOn(workScheduler)
                .observeOn(mainThreadScheduler)
                .subscribe(
                        list -> callback.call(list),
                        Throwable::printStackTrace);
    }

}
