package ua.mainacad.maintest.maintest.ui.login;

import android.arch.persistence.room.util.StringUtil;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import ua.mainacad.maintest.maintest.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    static final String KEY_EMAIL = "email";
    private static final int REQUEST_REGISTER = 22;
    private static final int REQUEST_PHONE_LOGIN = 23;
    private EditText etEmail;
    private EditText etPwd;
    private Executor executor = Executors.newFixedThreadPool(10);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_email);
        etEmail.setText(
                PreferenceManager.getDefaultSharedPreferences(this)
                        .getString(KEY_EMAIL, null));
        etPwd = findViewById(R.id.et_pwd);
        findViewById(R.id.btn_login).setOnClickListener(v -> tryLogin());
        findViewById(R.id.btn_register_email).setOnClickListener(v ->
                startActivityForResult(
                        new Intent(this, RegisterEmailActivity.class),
                        REQUEST_REGISTER));
        findViewById(R.id.btn_register_phone).setOnClickListener(v ->
                startActivityForResult(
                        new Intent(this, LoginPhoneActivity.class),
                        REQUEST_PHONE_LOGIN));
    }

    private void tryLogin() {
        String email = etEmail.getText().toString();
        String pass = etPwd.getText().toString();

        if(isEmptyLoginInput(email, pass)) {
            Toast.makeText(
                    LoginActivity.this,
                    "Login and password can't be empty",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                etEmail.getText().toString(),
                etPwd.getText().toString()
        ).addOnCompleteListener(executor, task -> {
            final Exception exception = task.getException();
            if (exception != null) {
                return;
            }
            final AuthResult result = task.getResult();
            if (result != null) {
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putString(KEY_EMAIL, result.getUser().getEmail())
                        .apply();
                setResult(RESULT_OK);
                finish();

            }
        }).addOnFailureListener(executor, e -> {
            e.printStackTrace();
            String message = e.getMessage();
            runOnUiThread(() ->
                    Toast.makeText(
                            LoginActivity.this,
                            message == null ? "Email or password is incorrect" : message,
                            Toast.LENGTH_SHORT)
                            .show());
        });
    }

    private boolean isEmptyLoginInput(String email, String pass) {
        return TextUtils.isEmpty(email) || TextUtils.isEmpty(pass);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_REGISTER
                    && data != null) {
                String email = data.getStringExtra(KEY_EMAIL);
                etEmail.setText(email);
            } else if (requestCode == REQUEST_PHONE_LOGIN) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
