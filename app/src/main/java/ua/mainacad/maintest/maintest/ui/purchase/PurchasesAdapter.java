package ua.mainacad.maintest.maintest.ui.purchase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.billingclient.api.SkuDetails;
import ua.mainacad.maintest.maintest.R;

import java.util.ArrayList;
import java.util.List;

class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder> {

    private final List<SkuDetails> items = new ArrayList<>();

    @NonNull
    private final OnBuyClickCallback callback;

    PurchasesAdapter(@NonNull OnBuyClickCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.purchase_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder vh, int i) {
        SkuDetails item = items.get(i);
        vh.title.setText(item.getTitle());
        vh.description.setText(item.getDescription());
        final String price = item.getPrice() + item.getPriceCurrencyCode();
        vh.price.setText(price);
        vh.buy.setOnClickListener(v -> callback.onBuyClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void update(List<SkuDetails> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView price;
        private Button buy;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.descr);
            price = itemView.findViewById(R.id.price);
            buy = itemView.findViewById(R.id.buy);
        }
    }

    public interface OnBuyClickCallback {
        void onBuyClick(SkuDetails sku);
    }
}
