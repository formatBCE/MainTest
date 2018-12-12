package ua.mainacad.maintest.maintest.ui.users;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import io.reactivex.Single;
import ua.mainacad.maintest.maintest.IMyMvpView;
import ua.mainacad.maintest.maintest.MyPresenter;
import ua.mainacad.maintest.maintest.dao.UserDao;
import ua.mainacad.maintest.maintest.model.User;

import java.util.List;
import java.util.Map;

@InjectViewState
public class UsersListPresenter extends MyPresenter<User, IMyMvpView<User>> {

    @NonNull
    @Override
    protected DatabaseReference getFirebaseReference() {
        return firebase().getReference("users");
    }

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
    protected LiveData<List<User>> getDatabaseSubscription() {
        return db().userDao().getAll();
    }

    @Override
    protected Map<String, User> parseFirebaseData(DataSnapshot dataSnapshot) {
        GenericTypeIndicator<Map<String, User>> genericTypeIndicator =
                new GenericTypeIndicator<Map<String, User>>() {
                };
        return dataSnapshot.getValue(genericTypeIndicator);
    }
}
