package ua.mainacad.maintest.maintest.ui.posts;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import io.reactivex.Single;
import ua.mainacad.maintest.maintest.IMyMvpView;
import ua.mainacad.maintest.maintest.MyPresenter;
import ua.mainacad.maintest.maintest.dao.PostDao;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.List;
import java.util.Map;

@InjectViewState
public class PostsListPresenter extends MyPresenter<Post, IMyMvpView<Post>> {

    @NonNull
    @Override
    protected DatabaseReference getFirebaseReference() {
        return firebase().getReference("posts");
    }

    @NonNull
    @Override
    protected Single<List<Post>> getApiCall() {
        return api().getAllPosts();
    }


    @NonNull
    @Override
    protected LiveData<List<Post>> getDatabaseSubscription() {
        return db().postDao().getAll();
    }

    @Override
    protected Map<String, Post> parseFirebaseData(DataSnapshot dataSnapshot) {
        GenericTypeIndicator<Map<String, Post>> genericTypeIndicator =
                new GenericTypeIndicator<Map<String, Post>>() {
                };
        return dataSnapshot.getValue(genericTypeIndicator);
    }

    @Override
    protected void updateDb(@NonNull List<Post> objects) {
        final PostDao postDao = db().postDao();
        final int rows = postDao.updateAll(objects);
        if (rows < objects.size()) {
            postDao.insertAll(objects);
        }
    }
}
