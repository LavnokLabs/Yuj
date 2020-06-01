package com.lavnok.yuj.ui.more;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lavnok.yuj.AdminActivity;
import com.lavnok.yuj.R;

public class MoreFragment extends Fragment {
    String TAG="com.lavnok.yuj-Logger";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more, container, false);

        final Button bAddVideo=root.findViewById(R.id.butt_add_video);
        bAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Executing on button click");
                Intent intent=new Intent(getActivity().getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

//        Fragment fragment2=new AddVideosFragment();
//        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.test,fragment2,"tag");
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
        return root;
    }
}
