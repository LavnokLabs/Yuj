package com.lavnok.yuj;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class AddVideosFragment extends Fragment {

    String[] srcThumbNail={"YouTube","Custom"};
    String[] difficultyLevel={"Beginner","Experienced Beginner","Intermediate","Advanced Intermediate"};

    Spinner sSourceThumbNamil,sDifficultyLevel;
    EditText etName,etDesc,etBenefits,etTags,etYouTubeId;
    ImageView imvThumbnail;
    TextView tvYouTubeUrl;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_videos, container, false);
        //Load Spinners
        sSourceThumbNamil = (Spinner) root.findViewById(R.id.valueThumbSource);
        sDifficultyLevel=(Spinner)root.findViewById(R.id.valueDifficultyLevel);

        etName=(EditText)root.findViewById(R.id.valueName);
        etDesc=(EditText)root.findViewById(R.id.valueDescription);
        etBenefits=(EditText)root.findViewById(R.id.valueBenefits);
        etTags=(EditText)root.findViewById(R.id.valueTags);
        etYouTubeId=(EditText)root.findViewById(R.id.valueId);


        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,AdminConfigEnum);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        return root;
    }
}

