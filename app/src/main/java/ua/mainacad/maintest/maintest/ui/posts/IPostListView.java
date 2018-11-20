package ua.mainacad.maintest.maintest.ui.posts;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import ua.mainacad.maintest.maintest.model.Post;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
interface IPostListView extends MvpView {
    void setPostList(List<Post> posts);
}
