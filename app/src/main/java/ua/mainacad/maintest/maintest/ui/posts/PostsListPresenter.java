package ua.mainacad.maintest.maintest.ui.posts;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import com.google.firebase.database.*;
import io.reactivex.Single;
import ua.mainacad.maintest.maintest.IMyMvpView;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.MyPresenter;
import ua.mainacad.maintest.maintest.dao.PostDao;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@InjectViewState
public class PostsListPresenter extends MyPresenter<Post, IMyMvpView<Post>> {

    private DatabaseReference posts;

    PostsListPresenter() {
        posts = MyApp.get().getFirebaseDb().getReference("posts");
        getFromFirebase();
    }

    private void getFromFirebase() {
        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Map<String, Post>> genericTypeIndicator =
                        new GenericTypeIndicator<Map<String, Post>>() {
                        };
                Map<String, Post> value = dataSnapshot.getValue(genericTypeIndicator);
//                Log.e("Posts", "Value is: " + value);
                if (value != null && !value.isEmpty()) {
                    List<Post> posts = new ArrayList<>(value.values());
                    for (Post p : posts) {
                        p.setFromFirebase(true);
                    }
                    //updateDb(posts);
                    onObjectsUpdatedFromFirebase(posts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Posts", "Failed to read value.", error.toException());
            }
        });
    }

    @NonNull
    @Override
    protected Single<List<Post>> getApiCall() {
        return api().getAllPosts();
    }

    @Override
    protected void updateDb(@NonNull List<Post> objects) {
        final PostDao postDao = db().postDao();
        final int rows = postDao.updateAll(objects);
        if (rows < objects.size()) {
            postDao.insertAll(objects);
        }
    }

    @NonNull
    @Override
    protected LiveData<List<Post>> getAll() {
        return db().postDao().getAll();
    }
}
