package ua.mainacad.maintest.maintest.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import ua.mainacad.maintest.maintest.R;
import ua.mainacad.maintest.maintest.Utils;

public class RegisterEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_email);
        findViewById(R.id.btn_register_email).setOnClickListener(v -> tryRegister());
    }

    private void tryRegister() {
        String email = ((EditText) findViewById(R.id.et_email)).getText().toString();
        String pwd = ((EditText) findViewById(R.id.et_pwd)).getText().toString();
        String pwdRepeat = ((EditText) findViewById(R.id.et_pwd_repeat)).getText().toString();
        if (!Utils.isEmailValid(email)) {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.contains(" ")) {
            Toast.makeText(this, "Password cannot contain whitespaces", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pwd.length() < 6) {
            Toast.makeText(this, "Password should be at least 6 symbols long", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(pwdRepeat)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(task -> {
                    Toast.makeText(
                            RegisterEmailActivity.this,
                            "Registered successful",
                            Toast.LENGTH_SHORT)
                            .show();
                    final Intent data = new Intent();
                    data.putExtra(LoginActivity.KEY_EMAIL, email);
                    setResult(RESULT_OK, data);
                    finish();
                });
    }
}
