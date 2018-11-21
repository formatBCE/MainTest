package ua.mainacad.maintest.maintest.ui.photos;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import ua.mainacad.maintest.maintest.model.Photo;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface IPhotoListView extends MvpView
{
    void setPhotos(List<Photo> photos);
}
