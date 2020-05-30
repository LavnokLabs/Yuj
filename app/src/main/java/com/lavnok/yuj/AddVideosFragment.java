package com.lavnok.yuj;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.lavnok.yuj.data.Videos;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class AddVideosFragment extends Fragment {
    String TAG = "com.lavnok.yuj-Logger";
    final String youTubeUrl = "https://www.youtube.com/watch?v=";
    String srcThumbNail[] = {"None", "YouTube", "Custom"};
    String[] difficultyLevel = {"Beginner", "Experienced Beginner", "Intermediate", "Advanced Intermediate"};
    Videos newVideo;
    Spinner sSourceThumbNamil, sDifficultyLevel;
    EditText etName, etDesc, etBenefits, etTags, etYouTubeId;
    TextView tvYouTubeUrl;
    Button bUploadVideo,bUploadThumbNail;
    ImageView imvThumbnail;
    TableRow trURL,trThumbNail;
    int duration;
    FirebaseUser user;
    String MyPREFERENCES = "com.lavnok.labs.yougapp.sharedpref";

    private DatabaseReference mDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_videos, container, false);
        //Load Spinners
        sSourceThumbNamil = (Spinner) root.findViewById(R.id.valueThumbSource);
        sDifficultyLevel = (Spinner) root.findViewById(R.id.valueDifficultyLevel);

        etName = (EditText) root.findViewById(R.id.valueName);
        etDesc = (EditText) root.findViewById(R.id.valueDescription);
        etBenefits = (EditText) root.findViewById(R.id.valueBenefits);
        etTags = (EditText) root.findViewById(R.id.valueTags);
        etYouTubeId = (EditText) root.findViewById(R.id.valueYouTubeId);
        bUploadVideo = (Button) root.findViewById(R.id.buttonUploadVideo);
        bUploadThumbNail=(Button)root.findViewById(R.id.bAddCustomThumb);
        trURL = (TableRow) root.findViewById(R.id.trUrl);
        trThumbNail=(TableRow)root.findViewById(R.id.trThumbNailRow);
        tvYouTubeUrl = (TextView) root.findViewById(R.id.valueYouTubeUrl);

        imvThumbnail=(ImageView)root.findViewById(R.id.valueThumbnail) ;



        ArrayAdapter adapterSourceThumbNail = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, srcThumbNail);
        adapterSourceThumbNail.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        sSourceThumbNamil.setAdapter(adapterSourceThumbNail);

        ArrayAdapter adapterDifficultyLevel = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, difficultyLevel);
        adapterSourceThumbNail.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        sDifficultyLevel.setAdapter(adapterDifficultyLevel);

        bUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createVideoInstance();
                uploadVideo();
            }
        });

        bUploadThumbNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Feature coming soon", Toast.LENGTH_LONG).show();
            }
        });

        etYouTubeId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                tvYouTubeUrl.setText(youTubeUrl + etYouTubeId.getText().toString());
                tvYouTubeUrl.setMovementMethod(LinkMovementMethod.getInstance());
                trURL.setVisibility(View.VISIBLE);

            }
        });

        sSourceThumbNamil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                    {
                        imvThumbnail.setImageBitmap(getBitmapFromURL(tvYouTubeUrl.getText().toString()));
                    }
                    case 2:
                    {
                        imvThumbnail.setImageResource(android.R.color.transparent);
                        bUploadThumbNail.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return root;
    }

    public void createVideoInstance() {
        BitmapDrawable drawable = (BitmapDrawable) imvThumbnail.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String tags = etTags.getText().toString();
        String[] tagList = tags.split(",");
        newVideo = new Videos(etName.getText().toString(), bitmap, etYouTubeId.getText().toString(), etDesc.getText().toString(), etBenefits.getText().toString(), tagList, duration, sDifficultyLevel.getSelectedItem().toString());

    }


    public void uploadVideo() {
        try {
            String key = mDatabase.child("videos").push().getKey();
            Map<String, Object> videoUpdate = new HashMap<>();
            videoUpdate.put("/videos/" + key, newVideo.toMap());
            mDatabase.updateChildren(videoUpdate);
            Log.d(TAG, "Video successfully added to DB");
            Toast.makeText(getActivity().getApplicationContext(), "Upload successful.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Unable to upload videos.", Toast.LENGTH_SHORT).show();
        }

//        String key = mDatabase.child("users").push().getKey();
//        User post = new User(displayName,age,MedicalHistory,isInstructor);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> userUpdate = new HashMap<>();
//        userUpdate.put("/users/" + key, postValues);
//
//        mDatabase.updateChildren(userUpdate);
//        Log.d(TAG,"Profile updated in 2 DBs successfully");
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

}

