package ua.mainacad.maintest.maintest.ui.posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ua.mainacad.maintest.maintest.IMyMvpView;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.List;

public class PostsListFragment extends MvpAppCompatFragment implements IMyMvpView<Post> {
    @InjectPresenter
    PostsListPresenter mPresenter;
    private PostsListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_posts_list,
                container,
                false);
        mAdapter = new PostsListAdapter();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        return recyclerView;
    }

    @Override
    public void updateWith(List<Post> posts) {
        mAdapter.setPosts(posts);
    }
}
