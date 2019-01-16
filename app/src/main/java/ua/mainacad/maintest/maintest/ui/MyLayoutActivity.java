package ua.mainacad.maintest.maintest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;
import ua.mainacad.maintest.maintest.R;

public class MyLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_layout_activity);
        ViewGroup myLayout = findViewById(R.id.my_layout);
        for (int i = 0; i < 5; i++) {
            TextView v = new TextView(this);
            final int colorPrimary = i % 2 == 0 ? R.color.colorPrimary : R.color.colorAccent;
            v.setBackgroundColor(ContextCompat.getColor(this, colorPrimary));
            myLayout.addView(v);
        }
    }
}
