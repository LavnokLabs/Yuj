package com.lavnok.yuj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth auth;
    CountryCodePicker ccp;
    String selected_country_code;
    EditText e1, e2;
    String TAG = "com.lavnok.yuj-Logger";
    TextView error;
    Button b2;
    String verification_Code;
    String MyPREFERENCES = "com.lavnok.labs.yougapp.sharedpref";
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = findViewById(R.id.editText3);
        e2 = findViewById(R.id.editText4);
        b2 = findViewById(R.id.button4);
        error = findViewById(R.id.errorText);
        ccp = findViewById(R.id.ccp);
        auth = FirebaseAuth.getInstance();

        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                singINUsingPhone(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.w(TAG, "onVerificationFailed", e);


                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    error.setText("Invalid credentials");
                    error.setTextColor(Color.RED);
                    e2.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    error.setText("Unable to LogIn. Please try after sometime.");
                    error.setTextColor(Color.RED);
                    e2.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    error.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Unable to signIn", Toast.LENGTH_SHORT).show();

                }

                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification_Code = s;
                Toast.makeText(getApplicationContext(), "OTP Successfully sent", Toast.LENGTH_SHORT).show();

                e2.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
            }
        };


    }

    public void onCountryPickerClick(View view) {
        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                //Alert.showMessage(RegistrationActivity.this, ccp.getSelectedCountryCodeWithPlus());
                selected_country_code = ccp.getSelectedCountryCodeWithPlus();
            }
        });
    }

    public void send_sms(View v) {

//        e2.setVisibility(View.VISIBLE);
//        b2.setVisibility(View.VISIBLE);
        try {
            error.setVisibility(View.INVISIBLE);
            String number = e1.getText().toString();
            if (number.isEmpty())
                number = "+919710975397";
            PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mCallBack);
        } catch (Exception e) {
            error.setText("Unable signIn. Generic Exception");
            error.setVisibility(View.VISIBLE);
            e.printStackTrace();
            Log.d(TAG, e.getStackTrace().toString());
        }

    }

    public void singINUsingPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User signed in successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = task.getResult().getUser();
                    if (user.getEmail() == null || user.getEmail().isEmpty()) {
                        //take user to one time profile setup
                        Intent intent = new Intent(getApplicationContext(), ConfigureProfileActivity.class);
                        intent.putExtra("Number", e1.getText().toString());

                        startActivity(intent);
                    } else {
                        //set user name
                        SharedPreferences sharedpref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedpref.edit();
                        edit.putString("displayName", user.getDisplayName());
                        edit.commit();
                        //take user to list of videos
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("Number", e1.getText().toString());
                        startActivity(intent);
                    }
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        error.setText("Invalid credentials");
                        error.setTextColor(Color.RED);
                        e2.setVisibility(View.GONE);
                        b2.setVisibility(View.GONE);
                        error.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }


    public void verify(View v) {
        String input_code = e2.getText().toString();
        if (input_code.isEmpty())
            input_code = "123456";
        if (verification_Code != null)
            verifyPhoneNumber(verification_Code, input_code);
        else
            Log.d(TAG, "Verification code null");
    }

    private void verifyPhoneNumber(String verifyCode, String input_code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, input_code);
        singINUsingPhone(credential);


    }

}
