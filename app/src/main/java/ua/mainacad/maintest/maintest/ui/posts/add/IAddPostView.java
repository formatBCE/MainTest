package ua.mainacad.maintest.maintest.ui.posts.add;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(SkipStrategy.class)
interface IAddPostView extends MvpView {
    void onPostAdded();
}
