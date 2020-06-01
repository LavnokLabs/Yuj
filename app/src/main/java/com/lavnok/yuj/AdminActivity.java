package com.lavnok.yuj;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lavnok.yuj.ui.admin.AddVideosFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    String AdminConfigEnum[] = {"Edit Home Page Tags", "Upload Video", "Remove Video", "Start a batch", "Modify batch"};
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        spin = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, AdminConfigEnum);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), AdminConfigEnum[position], Toast.LENGTH_LONG).show();
                switch (position) {
                    case 0:
                        FragmentTransaction transaction0 = getSupportFragmentManager().beginTransaction();
                        EditHomePageTagsFragment newFrag0 = new EditHomePageTagsFragment();
                        transaction0.replace(R.id.fragmentLayout, newFrag0);
                        transaction0.addToBackStack(null);
                        transaction0.commit();
                        break;
                    case 1:
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        AddVideosFragment newFrag1 = new AddVideosFragment();
                        transaction1.replace(R.id.fragmentLayout, newFrag1);
                        transaction1.addToBackStack(null);
                        transaction1.commit();
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}
