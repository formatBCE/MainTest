package ua.mainacad.maintest.maintest.ui.photos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.model.Photo;

import java.util.List;

public class PhotoListFragment extends MvpAppCompatFragment implements IPhotoListView {
    @InjectPresenter
    PhotosListPresenter presenter;

    private PhotosListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerViewPhotos = (RecyclerView) inflater
                .inflate(
                        R.layout.fragment_users_list,
                        container,
                        false);

        adapter = new PhotosListAdapter();
        recyclerViewPhotos.setAdapter(adapter);
        recyclerViewPhotos.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL));
        return recyclerViewPhotos;
    }

    @Override
    public void setPhotos(List<Photo> photos) {
        adapter.setPhotos(photos);
    }
}
