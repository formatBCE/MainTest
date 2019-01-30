package ua.mainacad.maintest.maintest.ui.purchase;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.android.billingclient.api.SkuDetails;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ua.mainacad.maintest.maintest.R;

import java.util.List;

public class PurchaseActivity extends MvpAppCompatActivity implements IPurchaseView {
    @InjectPresenter
    PurchasePresenter presenter;
    private PurchasesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        findViewById(R.id.btn_refresh_purchases).setOnClickListener(v -> presenter.refresh());
        adapter = new PurchasesAdapter(sku -> presenter.buy(this, sku));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void update(List<SkuDetails> newItems) {
        adapter.update(newItems);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
