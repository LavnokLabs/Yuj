package com.lavnok.yuj.ui.videos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lavnok.yuj.R;
import com.lavnok.yuj.ViewVideoFragment;
import com.lavnok.yuj.data.Videos;
import com.lavnok.yuj.utils.VideoRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment {
    final String TAG = "com.lavnok.yuj-Logger";
    RecyclerView videosRecyclerView;
    List<Videos> videosList;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;

    VideoViewModel mViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_videos, container, false);
//        database.setPersistenceEnabled(true);
        ref = database.getReference("/videos");
//        ref.keepSynced(true);
        videosList = new ArrayList<Videos>();

        mViewModel = ViewModelProviders.of(getActivity()).get(VideoViewModel.class);
        // set up the RecyclerView
        videosRecyclerView = root.findViewById(R.id.videosRecyclerView);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        generateVideosList();
        return root;
    }

    void generateVideosList() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            Log.d(TAG, dataSnapshot1.toString());
                            Videos video = dataSnapshot1.getValue(Videos.class);
                            videosList.add(video);
                            Log.d(TAG, "Size of video list" + videosList.size());
                        }
                        Log.d(TAG, "Size of video list" + videosList.size());

                        VideoRecyclerViewAdapter adapter1;
                        adapter1 = new VideoRecyclerViewAdapter(getActivity().getApplicationContext(), videosList);
                        adapter1.setClickListener(new VideoRecyclerViewAdapter.ItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Log.d(TAG, "Item clicked" + position);
                                mViewModel.setVideo(videosList.get(position));
                                ViewVideoFragment nextFrag = new ViewVideoFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.nav_host_fragment, nextFrag, "findThisFragment")
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });

                        videosRecyclerView.setAdapter(adapter1);


                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "No videos found", Toast.LENGTH_SHORT).show();
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
}