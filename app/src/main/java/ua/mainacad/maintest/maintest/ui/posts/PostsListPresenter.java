package ua.mainacad.maintest.maintest.ui.posts;

import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.database.DbHelper;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class PostsListPresenter extends MvpPresenter<IPostListView> {
    private List<Post> mPosts = new ArrayList<>();
    private DbHelper database = MyApp.get().getDatabase();

    PostsListPresenter() {
        final Call<List<Post>> posts = MyApp.get().getApi().getPosts();
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                final List<Post> posts = response.body();
                if (posts != null) {
                    try {
                        database.savePosts(posts);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    updatePosts(posts);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                t.printStackTrace();
                updatePosts(database.fetchAllPosts());
            }
        });
    }

    private void updatePosts(@NonNull List<Post> posts) {
        mPosts.clear();
        mPosts.addAll(posts);
        getViewState().setPostList(mPosts);
    }

    @Override
    public void attachView(IPostListView view) {
        super.attachView(view);
        view.setPostList(mPosts);
    }
}
