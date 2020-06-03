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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lavnok.yuj.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    RecyclerView leftRRV,rightRRV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edithomepagetags, container, false);

        //region Initialize ui elements
        context = getActivity().getApplicationContext();
//        llleft = root.findViewById(R.id.llleft);
//        llright = root.findViewById(R.id.llright);

        //endregion

        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        leftRRV = root.findViewById(R.id.leftrrv);
        leftRRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rightRRV=root.findViewById(R.id.rightRRV);
        rightRRV.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        generateLayout();
        return root;
    }







    public void generateLayout() {

        ref = database.getReference("/tags");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    if (dataSnapshot.hasChild("tag/value")) {
                        tagsFromDB = dataSnapshot.child("tag").child("value").getValue(String.class);
                        tagsList = Arrays.asList(tagsFromDB.split(","));

                        MyRecyclerViewAdapter adapter;
                        adapter = new MyRecyclerViewAdapter(getActivity().getApplicationContext(), tagsList);
                        adapter.setClickListener(new MyRecyclerViewAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getActivity().getApplicationContext(), "Yo", Toast.LENGTH_SHORT).show();

                            }
                        });
                        leftRRV.setAdapter(adapter);

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "No tags found", Toast.LENGTH_SHORT).show();
                    }
                    if (dataSnapshot.hasChild("hometag/value")) {
                        homeTagsFromDB = dataSnapshot.child("tag").child("value").getValue(String.class);
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
