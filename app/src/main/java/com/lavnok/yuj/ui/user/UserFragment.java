package com.lavnok.yuj.ui.user;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lavnok.yuj.AdminActivity;
import com.lavnok.yuj.R;

public class UserFragment extends Fragment {
    String TAG="com.lavnok.yuj-Logger";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        final TextView textView = root.findViewById(R.id.text_user);
        textView.setText("This is user fragment");

        final ImageButton bAddVideo=root.findViewById(R.id.buttAdmin);
        bAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Executing on button click");
                Intent intent=new Intent(getActivity().getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

}
