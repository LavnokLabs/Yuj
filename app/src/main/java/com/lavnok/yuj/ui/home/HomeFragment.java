package com.lavnok.yuj.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lavnok.yuj.R;

public class HomeFragment extends Fragment {
    ViewFlipper  simpleViewFlipper;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        simpleViewFlipper = (ViewFlipper) root.findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
        simpleViewFlipper.setFlipInterval(3000);
        Animation in = AnimationUtils.loadAnimation(getActivity().getBaseContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity().getBaseContext(), android.R.anim.slide_out_right);
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);
        simpleViewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSliderMenu(v);
            }
        });
        simpleViewFlipper.startFlipping();
        return root;
    }

    public void openSliderMenu(View v){

    }
}