package com.lavnok.yuj;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lavnok.yuj.ui.admin.AddVideosFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_videos, R.id.navigation_batches,R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


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
