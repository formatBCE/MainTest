package ua.mainacad.maintest.maintest.ui.posts.add;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.DatabaseReference;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.model.Post;

@InjectViewState
public class AddPostPresenter extends MvpPresenter<IAddPostView> {

    void onPostAdd(String title, String body) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        DatabaseReference newReference = MyApp.get().getFirebaseDb().getReference("posts").push();
        final String key = newReference.getKey();
        if (key != null) {
            post.setId(key.hashCode());
        }
        newReference.setValue(post)
                .addOnFailureListener(Throwable::printStackTrace)
                .addOnSuccessListener(aVoid -> getViewState().onPostAdded());
    }
}
