package ua.mainacad.maintest.maintest.ui.posts.add;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.DatabaseReference;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.Random;

@InjectViewState
public class AddPostPresenter extends MvpPresenter<IAddPostView> {


    void onPostAdd(String title, String body) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setId(new Random().nextInt(100));
        DatabaseReference posts = MyApp.get().getFirebaseDb().getReference("posts");
        posts.child(String.valueOf(post.getId())).setValue(post)
                .addOnFailureListener(Throwable::printStackTrace)
                .addOnSuccessListener(aVoid -> getViewState().onPostAdded());
    }
}
