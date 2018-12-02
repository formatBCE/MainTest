package ua.mainacad.maintest.maintest;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
public interface IMyMvpView<O> extends MvpView {
    void updateWith(List<O> mValues);
}
