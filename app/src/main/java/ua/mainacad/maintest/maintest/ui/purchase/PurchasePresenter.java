package ua.mainacad.maintest.maintest.ui.purchase;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Log;
import com.android.billingclient.api.*;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import ua.mainacad.maintest.maintest.MyApp;

import java.util.Arrays;
import java.util.List;

@InjectViewState
public class PurchasePresenter extends MvpPresenter<IPurchaseView> implements PurchasesUpdatedListener {

    private final BillingClient mBillingClient;
    private final List<String> skus = Arrays.asList(
            "product_1"
    );

    PurchasePresenter() {
        mBillingClient = BillingClient.newBuilder(MyApp.get()).setListener(this).build();
        connect();
    }

    private void connect() {
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                getViewState().showToast("Billing service connected, updating");
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    refresh();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                getViewState().showToast("Billing service disconnected");
            }
        });
    }


    void refresh() {
        if (mBillingClient.isReady()) {
            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
            params.setSkusList(skus).setType(BillingClient.SkuType.INAPP);
            mBillingClient.querySkuDetailsAsync(params.build(),
                    (responseCode, skuDetailsList) -> getViewState().update(skuDetailsList));
        } else {
            getViewState().showToast("Billing service disconnected, retrying");
            connect();
        }
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

    }

    void buy(Activity activity, SkuDetails sku) {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(sku)
                .build();
        int responseCode = mBillingClient.launchBillingFlow(activity, flowParams);
        Log.e("CODE", "" + responseCode);
    }
}
