package com.example.kiranastoremanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class UserVerification extends AppCompatActivity {

    TextInputLayout etVerificationCode;
    Button btnVerify,btnResendCode;
    TextView tvOptCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);

        etVerificationCode=findViewById(R.id.etVerification);
        btnVerify=findViewById(R.id.btnVerify);
        btnResendCode=findViewById(R.id.btnResendCode);
        tvOptCountDown=findViewById(R.id.tvOtpCountDown);
    }
}
