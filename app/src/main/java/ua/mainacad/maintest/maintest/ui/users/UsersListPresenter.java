package ua.mainacad.maintest.maintest.ui.users;

import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.api.IUsersApi;
import ua.mainacad.maintest.maintest.model.User;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class UsersListPresenter extends MvpPresenter<IUserListView> {


    private List<User> mUsers = new ArrayList<>();

    public UsersListPresenter() {
        Retrofit retrofit = MyApp.get().getRetrofit();
        final Call<List<User>> users = retrofit.create(IUsersApi.class).getUsers();
        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(
                    @NonNull Call<List<User>> call,
                    @NonNull Response<List<User>> response) {
                if (response.body() != null) {
                    mUsers.clear();
                    mUsers.addAll(response.body());
                    getViewState().setUserList(mUsers);
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<List<User>> call,
                    @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void attachView(IUserListView view) {
        super.attachView(view);
        view.setUserList(mUsers);
    }
}
