package com.lavnok.yuj.utils;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FireBaseDBComms {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final String TAG = "com.lavnok.yuj-Logger";
    DatabaseReference ref;
    String tags=null;
    public FireBaseDBComms(){
    }

    public String readTag(){

        ref=database.getReference("/tags/tag/value");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tags = dataSnapshot.getValue(String.class);
                Log.d(TAG,"cp1"+tags);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        Log.d(TAG,"cp2"+tags);
        return tags;
    }




}
