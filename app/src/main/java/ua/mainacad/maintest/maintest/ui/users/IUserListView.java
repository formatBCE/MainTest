package ua.mainacad.maintest.maintest.ui.users;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import ua.mainacad.maintest.maintest.model.User;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
interface IUserListView extends MvpView {

    void setUserList(List<User> users);

}
