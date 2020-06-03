package com.lavnok.yuj;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lavnok.yuj.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditHomePageTagsFragment extends Fragment {
    final String TAG = "com.lavnok.yuj-Logger";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    //    LinearLayout llleft, llright;
    String tagsFromDB;
    String homeTagsFromDB;
    List<String> tagsList;
    List<String> homeTagsList;
    ArrayList<String> selectedTagsToAdd;
    DatabaseReference ref;
    Context context;
    RecyclerView rightRRV;
    Spinner spinTags;
    String tagToAdd;
    Button butAddToHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edithomepagetags, container, false);

        //region Initialize ui elements
        context = getActivity().getApplicationContext();
        spinTags = root.findViewById(R.id.spinTags);
        butAddToHome = root.findViewById(R.id.buttAddTags);
        //endregion

        ref = database.getReference("/tags");
        // set up the RecyclerView
        rightRRV = root.findViewById(R.id.rightRRV);
        rightRRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        butAddToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        updateHomePageTags();
            }
        });

        spinTags.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"Position"+position+" tagsList.get"+tagsList.get(position));
                tagToAdd = tagsList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        generateLayout();
        return root;
    }

    public void updateHomePageTags(){
        if(tagToAdd==null)
            Toast.makeText(context, "Unable to add tag. null", Toast.LENGTH_SHORT).show();
        else
        {
            try {
                if(homeTagsFromDB!=null)
                    tagToAdd=homeTagsFromDB+","+tagToAdd;
                ref.child("hometag").child("value").setValue(tagToAdd);
                Log.d(TAG, "Video successfully added to DB");
                generateLayout();
                Toast.makeText(getActivity().getApplicationContext(), "Upload successful.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Unable to upload videos.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void generateLayout() {



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    if (dataSnapshot.hasChild("tag/value")) {
                        tagsFromDB = dataSnapshot.child("tag").child("value").getValue(String.class);
                        tagsList = Arrays.asList(tagsFromDB.split(","));

                        ArrayAdapter aa = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, tagsList);
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        //Setting the ArrayAdapter data on the Spinner
                        spinTags.setAdapter(aa);


                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "No tags found", Toast.LENGTH_SHORT).show();
                    }
                    if (dataSnapshot.hasChild("hometag/value")) {
                        homeTagsFromDB = dataSnapshot.child("hometag").child("value").getValue(String.class);
                        homeTagsList = Arrays.asList(homeTagsFromDB.split(","));

                        MyRecyclerViewAdapter adapter1;
                        adapter1 = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), homeTagsList);
                        adapter1.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getActivity().getApplicationContext(), "You clicked ", Toast.LENGTH_SHORT).show();

                            }
                        });
                        rightRRV.setAdapter(adapter1);
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "No home page tags found. Please add a few to start.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Exception occurred");
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    public boolean isUniqueTag(String tag) {

        boolean isUniqueFlag = true;
//        if (homeTagsList != null && homeTagsList.length > 0) {
//            for (int i = 0; i < homeTagsList.length; i++) {
//                if (tag.equalsIgnoreCase(homeTagsList[i])) {
//                    Log.d(TAG, "tag not unique" + tag);
//                    isUniqueFlag = false;
//                }
//            }
//        }
        return isUniqueFlag;

    }

}
