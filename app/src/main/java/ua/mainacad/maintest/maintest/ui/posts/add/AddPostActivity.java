package ua.mainacad.maintest.maintest.ui.posts.add;

import android.os.Bundle;
import android.widget.EditText;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import ua.mainacad.maintest.maintest.R;

public class AddPostActivity extends MvpAppCompatActivity implements IAddPostView {

    @InjectPresenter
    AddPostPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        final EditText etTitle = findViewById(R.id.et_title);
        final EditText etBody = findViewById(R.id.et_body);
        findViewById(R.id.btn_add_post).setOnClickListener(
                v -> mPresenter.onPostAdd(
                        etTitle.getText().toString(),
                        etBody.getText().toString()
                )

        );
    }


    @Override
    public void onPostAdded() {
        setResult(RESULT_OK);
        finish();
    }
}
