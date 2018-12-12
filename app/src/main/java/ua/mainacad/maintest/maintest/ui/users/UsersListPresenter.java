package ua.mainacad.maintest.maintest.ui.users;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.arellomobile.mvp.InjectViewState;
import io.reactivex.Single;
import ua.mainacad.maintest.maintest.IMyMvpView;
import ua.mainacad.maintest.maintest.MyPresenter;
import ua.mainacad.maintest.maintest.dao.UserDao;
import ua.mainacad.maintest.maintest.model.User;

import java.util.List;

@InjectViewState
public class UsersListPresenter extends MyPresenter<User, IMyMvpView<User>> {

    @NonNull
    @Override
    protected Single<List<User>> getApiCall() {
        return api().getAllUsers();
    }

    @Override
    protected void updateDb(@NonNull List<User> objects) {
        final UserDao userDao = db().userDao();
        final int rows = userDao.updateAll(objects);
        if (rows < objects.size()) {
            userDao.insertAll(objects);
        }
    }

    @NonNull
    @Override
    protected LiveData<List<User>> getAll() {
        return db().userDao().getAll();
    }
}
