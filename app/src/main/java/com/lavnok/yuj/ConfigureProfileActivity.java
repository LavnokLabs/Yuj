package com.lavnok.yuj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lavnok.yuj.data.User;

import java.util.HashMap;
import java.util.Map;

public class ConfigureProfileActivity extends AppCompatActivity {
    //third activity - One time call
    String TAG = "com.lavnok.labs.yogapp-Logger";
    EditText etName, etEmail, etAge, etNumber, etMedHis;
    CheckBox cbIsInstructor;
    ImageView imAvatar;
    Button btUpload, btRegister, btVerify;
    FirebaseUser user;
    String MyPREFERENCES="com.lavnok.labs.yougapp.sharedpref";

    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_profile);
        etName = findViewById(R.id.editDisplayName);
        etEmail = findViewById(R.id.editEmail);
        etAge = findViewById(R.id.editAge);
        etNumber = findViewById(R.id.editPhoneNumber);
        etMedHis = findViewById(R.id.editTextMedHis);
        cbIsInstructor = findViewById(R.id.checkBoxIsInstructor);
        imAvatar = findViewById(R.id.imageViewAvatar);
        btRegister = findViewById(R.id.buttonRegister);
        btUpload = findViewById(R.id.buttonUploadAvatar);
        btVerify = findViewById(R.id.buttonVerifyEmail);
        checkUser();

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
            }
        });

        btVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailVerificationLink();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void checkUser() {
        try {
            user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.d(TAG, "Logged in user identified successfully");
                etNumber.setText(user.getPhoneNumber());
            } else {
                // No user is signed in
                Log.d(TAG, "No user found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "Error while fetching the user from FireBase DB");
        }
    }

    public void saveUserData() {

        //save username in sharedpref
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharedpreferences.edit();
        edit.putString("displayName",etName.getText().toString());
        edit.commit();


        //updating firebase profile
        updateNameAndPic();
        updateEmail();
        updateOtherInfo();
        if (cbIsInstructor.isChecked())
            initiateNewInstructorFlow();

        //updating other user datas
        updateUserDB();

        //Go to User Home page with list of videos
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("displayName", etNumber.getText().toString());

        startActivity(intent);
    }

    public void updateNameAndPic() {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(etName.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void updateEmail() {

        user.updateEmail(etEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }
                });
    }

    public void sendEmailVerificationLink() {
        verifyEmailStructure();
        if(etEmail.getText()!=null && !etEmail.getText().toString().isEmpty()) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
        }
        else
        {
            Log.d(TAG,"Unable to send verificationLink");
        }
    }

    public void verifyEmailStructure(){

    }

    public void updateOtherInfo() {

    }

    public void updateUserDB(){

        writeNewUser(etName.getText().toString(),Integer.valueOf(etAge.getText().toString()),etMedHis.getText().toString(),cbIsInstructor.isChecked());
    }


    private void writeNewUser(String displayName,int age,String MedicalHistory,boolean isInstructor) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("users").push().getKey();
        User post = new User(displayName,age,MedicalHistory,isInstructor);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("/users/" + key, postValues);

        mDatabase.updateChildren(userUpdate);
        Log.d(TAG,"Profile updated in 2 DBs successfully");
    }
    public void initiateNewInstructorFlow() {
//Do nothing for now
    }

}
