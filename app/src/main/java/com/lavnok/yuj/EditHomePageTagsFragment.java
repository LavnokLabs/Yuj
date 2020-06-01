package com.lavnok.yuj;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditHomePageTagsFragment extends Fragment {
    LinearLayout llleft,llright;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edithomepagetags, container, false);
        //Initiali
        generateLayout();
        return root;
    }

    public void generateLayout(){

    }

}
