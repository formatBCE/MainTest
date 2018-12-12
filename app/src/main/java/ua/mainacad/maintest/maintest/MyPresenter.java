package ua.mainacad.maintest.maintest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.util.Log;
import com.arellomobile.mvp.MvpPresenter;
import com.google.firebase.database.*;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ua.mainacad.maintest.maintest.api.Api;
import ua.mainacad.maintest.maintest.database.AppDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class MyPresenter<O, T extends IMyMvpView<O>> extends MvpPresenter<T> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final List<O> mValues = new ArrayList<>();
    private final Single<List<O>> apiCall;
    private final LiveData<List<O>> liveData;
    private final DatabaseReference firebaseReference;
    private final Observer<List<O>> liveDataObserver;

    protected MyPresenter() {
        apiCall = getApiCall();
        liveData = getDatabaseSubscription();
        firebaseReference = getFirebaseReference();
        liveDataObserver = objects -> updateView(objects == null ? new ArrayList<>() : objects);
        liveData.observeForever(liveDataObserver);
        initData();
    }

    @NonNull
    protected abstract DatabaseReference getFirebaseReference();

    @NonNull
    protected abstract Single<List<O>> getApiCall();

    protected abstract void updateDb(@NonNull List<O> objects);

    @NonNull
    protected abstract LiveData<List<O>> getDatabaseSubscription();

    @Override
    public void attachView(T view) {
        super.attachView(view);
        view.updateWith(mValues);
    }

    private void initData() {
        loadInitData();
    }

    private void loadInitData() {
        rx(apiCall
                .subscribeOn(Schedulers.io())
                .subscribe(
                        objects -> {
                            if (objects != null) {
                                writeToDb(objects);
                            }
                            loadUpdates();
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            loadUpdates();
                        }));
    }

    private void loadUpdates() {
        firebaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, O> value = parseFirebaseData(dataSnapshot);
                if (value != null && !value.isEmpty()) {
                    List<O> objects = new ArrayList<>(value.values());
                    writeToDb(objects);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Objects", "Failed to read value.", error.toException());
            }
        });
    }

    protected abstract Map<String, O> parseFirebaseData(DataSnapshot dataSnapshot);

    private void writeToDb(List<O> objects) {
        rx(Single.<Boolean>create(emitter -> {
            updateDb(objects);
            emitter.onSuccess(true);
        })
                .subscribeOn(Schedulers.io())
                .subscribe(
                        Boolean -> {
                        },
                        Throwable::printStackTrace));
    }

    private void updateView(@NonNull Collection<O> objects) {
        mValues.clear();
        mValues.addAll(objects);
        getViewState().updateWith(mValues);
    }

    private void rx(Disposable d) {
        compositeDisposable.add(d);
    }

    @Override
    public void onDestroy() {
//        Log.e("firebase", "onDestroy triggered in " + this.getClass().getSimpleName());
        compositeDisposable.dispose();
        liveData.removeObserver(liveDataObserver);
        super.onDestroy();
    }

    protected Api.Get api() {
        return MyApp.get().getApi();
    }

    protected FirebaseDatabase firebase() {
        return MyApp.get().getFirebaseDb();
    }

    protected AppDatabase db() {
        return MyApp.get().getRoomDatabase();
    }
}
