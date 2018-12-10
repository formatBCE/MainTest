package ua.mainacad.maintest.maintest.ui.photos;

import android.support.annotation.NonNull;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.mainacad.maintest.maintest.MyApp;
import ua.mainacad.maintest.maintest.model.Photo;

import java.util.ArrayList;
import java.util.List;


@InjectViewState
public class PhotosListPresenter extends MvpPresenter<IPhotoListView> {
    private ArrayList<Photo> mPhotos = new ArrayList<>();

    PhotosListPresenter() {
        Call<List<Photo>> photos = MyApp.get().getApi().getAllPhotos();
        photos.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(@NonNull Call<List<Photo>> call, @NonNull Response<List<Photo>> response) {
                if (response.body() != null) {
                    mPhotos.clear();
                    mPhotos.addAll(response.body());
                    getViewState().setPhotos(mPhotos);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Photo>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void attachView(IPhotoListView view) {
        super.attachView(view);
        getViewState().setPhotos(mPhotos);
    }
}
