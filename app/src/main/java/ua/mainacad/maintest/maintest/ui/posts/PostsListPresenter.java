package ua.mainacad.maintest.maintest.ui.posts;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import io.reactivex.Single;
import ua.mainacad.maintest.maintest.IMyMvpView;
import ua.mainacad.maintest.maintest.MyPresenter;
import ua.mainacad.maintest.maintest.dao.PostDao;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.List;

@InjectViewState
public class PostsListPresenter extends MyPresenter<Post, IMyMvpView<Post>> {

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
