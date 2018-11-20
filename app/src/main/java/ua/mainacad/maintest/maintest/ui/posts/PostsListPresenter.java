package ua.mainacad.maintest.maintest.ui.posts;

import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.api.IPostsApi;
import ua.mainacad.maintest.maintest.model.Post;
import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class PostsListPresenter extends MvpPresenter<IPostListView> {
    private List<Post> mPosts = new ArrayList<>();

    public PostsListPresenter() {
        Retrofit retrofit = MyApp.get().getRetrofit();
        final Call<List<Post>> posts = retrofit.create(IPostsApi.class).getPosts();
        posts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (response.body() != null) {
                    mPosts.clear();
                    mPosts.addAll(response.body());
                    getViewState().setPostList(mPosts);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void attachView(IPostListView view) {
        super.attachView(view);
        view.setPostList(mPosts);
    }
}
