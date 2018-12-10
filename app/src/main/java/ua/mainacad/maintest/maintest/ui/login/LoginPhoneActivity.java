package ua.mainacad.maintest.maintest.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import ua.mainacad.maintest.maintest.R;

import java.util.concurrent.TimeUnit;

public class LoginPhoneActivity extends AppCompatActivity {

    String currentVerificationId;
    private EditText etPhoneNumber;
    private EditText etVerificationCode;
    private Button btnSendCode;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);
        etPhoneNumber = findViewById(R.id.et_phone);
        etVerificationCode = findViewById(R.id.et_code);
        btnSendCode = findViewById(R.id.btn_send_code);
        btnLogin = findViewById(R.id.btn_login);
        btnSendCode.setOnClickListener(v -> sendCode());
        btnLogin.setOnClickListener(v -> tryLogin());
        setupInterface();
    }

    private void tryLogin() {
        if (currentVerificationId == null) {
            return;
        }
        String code = etVerificationCode.getText().toString();
        PhoneAuthCredential cred = PhoneAuthProvider.getCredential(currentVerificationId, code);
        login(cred);
    }

    private void setupInterface() {
        final boolean codeIsSent = currentVerificationId != null;
        etPhoneNumber.setEnabled(!codeIsSent);
        etVerificationCode.setEnabled(codeIsSent);
        btnSendCode.setVisibility(codeIsSent ? View.GONE : View.VISIBLE);
        btnLogin.setVisibility(!codeIsSent ? View.GONE : View.VISIBLE);
    }

    private void sendCode() {
        String phone = ((EditText) findViewById(R.id.et_phone)).getText().toString();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        login(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        e.printStackTrace();
                        String message = e.getMessage();
                        Toast.makeText(
                                LoginPhoneActivity.this,
                                message == null ? "Wrong phone number" : message,
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        Log.e("Auth", "Code sent");
                        currentVerificationId = verificationId;
                        setupInterface();
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        currentVerificationId = null;
                        setupInterface();
                    }
                });
    }

    private void login(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    setResult(RESULT_OK);
                    finish();
                })
                .addOnFailureListener(e -> {
                    e.printStackTrace();
                    String message = e.getMessage();
                    Toast.makeText(
                            LoginPhoneActivity.this,
                            message == null ? "Wrong code" : message,
                            Toast.LENGTH_SHORT)
                            .show();
                });
    }
}
