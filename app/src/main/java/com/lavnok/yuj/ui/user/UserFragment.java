package com.lavnok.yuj.ui.user;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lavnok.yuj.AdminActivity;
import com.lavnok.yuj.R;

public class UserFragment extends Fragment {
    final String TAG = "com.lavnok.yuj-Logger";
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText etvname,etemail,etphoneNo,etage,etmedihis;
    DatabaseReference ref;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        //region Description
        //initialize elements
        etvname=root.findViewById(R.id.etName);
        etemail=root.findViewById(R.id.etEmail);
        etphoneNo=root.findViewById(R.id.etPhoneNumber);
        etage=root.findViewById(R.id.etAge);
        etmedihis=root.findViewById(R.id.etMedHis);
        //endregion

        final ImageButton bAddVideo=root.findViewById(R.id.buttAdmin);
        bAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Executing on button click");
                Intent intent=new Intent(getActivity().getApplicationContext(), AdminActivity.class);
                startActivity(intent);

//                LayoutInflater inflater = (LayoutInflater)
//                        getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View popupView = inflater.inflate(R.layout.activity_admin, null);
//
//                // create the popup window
//                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
//                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
//                boolean focusable = true; // lets taps outside the popup also dismiss it
//                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//                // show the popup window
//                // which view you pass in doesn't matter, it is only used for the window tolken
//                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            }
        });
        return root;
    }

}
