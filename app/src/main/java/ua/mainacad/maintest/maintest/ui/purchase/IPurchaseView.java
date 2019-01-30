package ua.mainacad.maintest.maintest.ui.purchase;

import com.android.billingclient.api.SkuDetails;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(SkipStrategy.class)
interface IPurchaseView extends MvpView {

    void update(List<SkuDetails> newItems);

    void showToast(String message);
}
