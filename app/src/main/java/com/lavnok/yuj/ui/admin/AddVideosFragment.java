package com.lavnok.yuj.ui.admin;


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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lavnok.yuj.R;
import com.lavnok.yuj.data.Videos;
import com.lavnok.yuj.utils.ImageLoadTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class AddVideosFragment extends Fragment {
    final String TAG = "com.lavnok.yuj-Logger";
    final String IMAGEURL_PART1 = "https://img.youtube.com/vi/";
    final String IMAGEURL_PART2 = "/1.jpg";
    final String youTubeUrl = "https://www.youtube.com/watch?v=";
    String srcThumbNail[] = {"None", "YouTube", "Custom"};
    String[] difficultyLevel = {"Beginner", "Experienced Beginner", "Intermediate", "Advanced Intermediate"};
    String[] tagList;
    Videos newVideo;
    Spinner sSourceThumbNamil, sDifficultyLevel;
    EditText etName, etDesc, etBenefits, etTags, etYouTubeId;
    TextView tvYouTubeUrl;
    Button bUploadVideo, bUploadThumbNail;
    ImageView imvThumbnail;
    TableRow trURL, trThumbNail;
    int duration;
    FirebaseUser user;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseTag;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_videos, container, false);

        //region initializers
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseTag = FirebaseDatabase.getInstance().getReference("/tags/tag");
        //Load Spinners
        sSourceThumbNamil = (Spinner) root.findViewById(R.id.valueThumbSource);
        sDifficultyLevel = (Spinner) root.findViewById(R.id.valueDifficultyLevel);

        etName = (EditText) root.findViewById(R.id.valueName);
        etDesc = (EditText) root.findViewById(R.id.valueDescription);
        etBenefits = (EditText) root.findViewById(R.id.valueBenefits);
        etTags = (EditText) root.findViewById(R.id.valueTags);
        etYouTubeId = (EditText) root.findViewById(R.id.valueYouTubeId);
        bUploadVideo = (Button) root.findViewById(R.id.buttonUploadVideo);
        bUploadThumbNail = (Button) root.findViewById(R.id.bAddCustomThumb);
        trURL = (TableRow) root.findViewById(R.id.trUrl);
        trThumbNail = (TableRow) root.findViewById(R.id.trThumbNailRow);
        tvYouTubeUrl = (TextView) root.findViewById(R.id.valueYouTubeUrl);

        imvThumbnail = (ImageView) root.findViewById(R.id.valueThumbnail);
        //endregion


        //region spinners
        ArrayAdapter adapterSourceThumbNail = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, srcThumbNail);
        adapterSourceThumbNail.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        sSourceThumbNamil.setAdapter(adapterSourceThumbNail);

        ArrayAdapter adapterDifficultyLevel = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, difficultyLevel);
        adapterSourceThumbNail.setDropDownViewResource(R.layout.spinner_row);
        //Setting the ArrayAdapter data on the Spinner
        sDifficultyLevel.setAdapter(adapterDifficultyLevel);
        //endregion

        //region listeners
        bUploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createVideoInstance();
//                uploadVideo();
                uploadTags();
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
                trURL.setVisibility(View.VISIBLE);

            }
        });

        sSourceThumbNamil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1:
                        String imageURL = IMAGEURL_PART1 + etYouTubeId.getText().toString() + IMAGEURL_PART2;
                        new ImageLoadTask(imageURL, imvThumbnail).execute();
                        trThumbNail.setVisibility(View.VISIBLE);

                        break;
                    case 2:
                        trThumbNail.setVisibility(View.VISIBLE);
                        imvThumbnail.setImageResource(android.R.color.transparent);
                        bUploadThumbNail.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //endregion

        return root;
    }


    public void createVideoInstance() {
        BitmapDrawable drawable = (BitmapDrawable) imvThumbnail.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String tags = etTags.getText().toString();
        tagList = tags.split(",");
        newVideo = new Videos(etName.getText().toString(), bitmap, etYouTubeId.getText().toString(), etDesc.getText().toString(), etBenefits.getText().toString(), tagList, duration, sDifficultyLevel.getSelectedItem().toString());

    }


    public void uploadVideo() {
        if (newVideo == null)
            Log.e(TAG, "Video object null");
        else {
            try {
                String key = mDatabase.child("videos").push().getKey();
                Map<String, Object> videoValues = newVideo.toMap();

                Map<String, Object> videoUpdate = new HashMap<>();
                videoUpdate.put("/videos/" + key, videoValues);

                mDatabase.updateChildren(videoUpdate);
                Log.d(TAG, "Video successfully added to DB");
                Toast.makeText(getActivity().getApplicationContext(), "Upload successful.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Unable to upload videos.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void uploadTags() {
//        final String currentTagList;
        if (tagList != null) {
//            String tags= String.valueOf(mDatabase.child("tags").child("tag"));
            mDatabaseTag.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String currentTagList;
                    boolean isUpdateRequired = false;
                    String[] tags = (etTags.getText().toString()).split(",");
                    if (dataSnapshot.hasChild("tag/value")) {
//                        String change = dataSnapshot.getValue(String.class);
                        currentTagList = dataSnapshot.child("tag").child("title").getValue(String.class);
                        Log.d(TAG, "Value from db" + currentTagList);
                        for (String tag : tags) {
                            if (!currentTagList.contains(tag)) {
                                isUpdateRequired = true;
                                currentTagList = currentTagList + "," + tag;
                            } else
                                Log.d(TAG, "Tag:" + tag + " already present");
                        }
                    } else {
                        isUpdateRequired = true;
                        currentTagList = etTags.getText().toString();
                    }

                    //updating the table
                    if (isUpdateRequired)
                        mDatabaseTag.child("value").setValue(currentTagList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Log.e(TAG, "Unable to update tags table. TagList empty");
        }
    }


}

