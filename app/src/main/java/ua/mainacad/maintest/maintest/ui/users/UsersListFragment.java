package ua.mainacad.maintest.maintest.ui.users;

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
import ua.mainacad.maintest.maintest.model.User;

import java.util.List;

public class UsersListFragment extends MvpAppCompatFragment implements IMyMvpView<User> {

    @InjectPresenter
    UsersListPresenter mPresenter;
    private UsersListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        RecyclerView mRecyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_users_list,
                container,
                false);
        mAdapter = new UsersListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        return mRecyclerView;
    }

    @Override
    public void updateWith(List<User> users) {
        mAdapter.setUsers(users);
    }

}
