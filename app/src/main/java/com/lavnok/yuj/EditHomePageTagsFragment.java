package com.lavnok.yuj;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lavnok.yuj.utils.FireBaseDBComms;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditHomePageTagsFragment extends Fragment {
    final String TAG = "com.lavnok.yuj-Logger";

    LinearLayout llleft, llright;
    String tagsFromDB;
    String homeTagsFromDB;
    String tagsList[];
    String homeTagsList[];

    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edithomepagetags, container, false);

        //region Initialize ui elements
        context = getActivity().getApplicationContext();
        llleft = root.findViewById(R.id.llleft);
        llright = root.findViewById(R.id.llright);

        //endregion

        generateLayout();
        return root;
    }

    public void populatetagsFromDB() {

//        mDatabaseTag.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                try {
//                    if (dataSnapshot.hasChild("tag/value")) {
//                        tagsFromDB = dataSnapshot.child("tag").child("value").getValue(String.class);
//                        Log.d(TAG, "Value from db" + tagsFromDB);
//                        tagsList = tagsFromDB.split(",");
//                        Log.d(TAG,tagsList[0]);
//                    } else {
//                        Toast.makeText(getActivity().getApplicationContext(), "No tags found", Toast.LENGTH_SHORT).show();
//                    }
//                    if (dataSnapshot.hasChild("hometag/value")) {
//                        homeTagsFromDB = dataSnapshot.child("tag").child("value").getValue(String.class);
//                        Log.d(TAG, "Value from db" + homeTagsFromDB);
//                        homeTagsList = homeTagsFromDB.split(",");
//                    } else {
//                        Toast.makeText(getActivity().getApplicationContext(), "No home page tags found. Please add a few to start.", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    Log.e(TAG,"Exception occurred");
//                    e.printStackTrace();
//                }
//                Log.d(TAG,tagsList[0]);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }


    public void generateLayout() {
//        populatetagsFromDB();
        Log.d(TAG,"cp2"+new FireBaseDBComms().readTag());

        if (tagsList != null && tagsList.length > 0) {
            for (String tag : tagsList) {
                if (isUniqueTag(tag)) {
                    Log.d(TAG, "Adding to left view:" + tag);
                    TextView textView = new TextView(context);
                    textView.setText(tag);
                    llleft.addView(textView);
                }
            }
        }

        if (homeTagsList != null && homeTagsList.length > 0) {
            for (String tag : homeTagsList) {
                Log.d(TAG, "Adding to right view:" + tag);
                TextView textView = new TextView(context);
                textView.setText(tag);
                llright.addView(textView);
            }
        }
    }

    public boolean isUniqueTag(String tag) {

        boolean isUniqueFlag = true;
        if (homeTagsList != null && homeTagsList.length > 0) {
            for (int i = 0; i < homeTagsList.length; i++) {
                if (tag.equalsIgnoreCase(homeTagsList[i])) {
                    Log.d(TAG,"tag not unique"+tag);
                    isUniqueFlag = false;
                }
            }
        }
        return isUniqueFlag;

    }

}
