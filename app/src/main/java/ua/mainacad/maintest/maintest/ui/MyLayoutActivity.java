package ua.mainacad.maintest.maintest.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.ui.view.MyLayout;

public class MyLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout_activity);
        MyLayout myLayout = findViewById(R.id.my_layout);
        findViewById(R.id.increase_shift).setOnClickListener(
                v -> myLayout.setChildShifts(
                        myLayout.getChildHorizontalShift() + 5,
                        myLayout.getChildVerticalShift() + 5));
        findViewById(R.id.add_view).setOnClickListener(
                v -> myLayout.addView(getView(myLayout.getChildCount())));
    }

    @NonNull
    private View getView(int currentChildCount) {
        TextView v = new TextView(this);
        final int colorPrimary = currentChildCount % 2 == 0
                ? R.color.colorPrimary
                : R.color.colorAccent;
        v.setBackgroundColor(ContextCompat.getColor(this, colorPrimary));
        return v;
    }
}
