package ua.mainacad.maintest.maintest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import com.arellomobile.mvp.MvpPresenter;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.mainacad.maintest.maintest.api.Api;
import ua.mainacad.maintest.maintest.database.AppDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class MyPresenter<O, T extends IMyMvpView<O>> extends MvpPresenter<T> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final List<O> mValues = new ArrayList<>();
    private final Call<List<O>> apiCall;
    private final LiveData<List<O>> liveData;
    private final Observer<List<O>> liveDataObserver;

    protected MyPresenter() {
        apiCall = getApiCall();
        liveData = getAll();
        liveDataObserver = objects -> updateModelAndView(objects == null ? new ArrayList<>() : objects);
        init();
    }

    @NonNull
    protected abstract Call<List<O>> getApiCall();

    protected abstract void updateDb(@NonNull List<O> objects);

    @NonNull
    protected abstract LiveData<List<O>> getAll();

    private void init() {
        liveData.observeForever(liveDataObserver);
        apiCall.enqueue(new Callback<List<O>>() {
            @Override
            public void onResponse(@NonNull Call<List<O>> call, @NonNull Response<List<O>> response) {
                final List<O> objects = response.body();
                if (objects != null) {
                    rx(Single.<Boolean>create(emitter -> {
                        updateDb(objects);
                        emitter.onSuccess(true);
                    })
                            .subscribeOn(Schedulers.io())
                            .subscribe());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<O>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void attachView(T view) {
        super.attachView(view);
        view.updateWith(mValues);
    }

    private void updateModelAndView(@NonNull Collection<O> objects) {
        mValues.clear();
        mValues.addAll(objects);
        getViewState().updateWith(mValues);
    }

    private void rx(Disposable d) {
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        liveData.removeObserver(liveDataObserver);
        super.onDestroy();
    }

    protected Api.Get api() {
        return MyApp.get().getApi();
    }

    protected AppDatabase db() {
        return MyApp.get().getRoomDatabase();
    }
}
