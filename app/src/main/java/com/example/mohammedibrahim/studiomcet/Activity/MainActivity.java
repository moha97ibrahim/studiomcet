package com.example.mohammedibrahim.studiomcet.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mohammedibrahim.studiomcet.Download_Fragment;
import com.example.mohammedibrahim.studiomcet.Event_Fragment;
import com.example.mohammedibrahim.studiomcet.ProfileFragment;
import com.example.mohammedibrahim.studiomcet.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadEventFragment();
                    return true;
                case R.id.navigation_dashboard:
                    loadDownloadFragment();
                    return true;
                case R.id.navigation_notifications:
                    loadProfileFragment();
                    return true;
            }
            return false;
        }
    };

    private void loadProfileFragment() {
        ProfileFragment fragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_frame, fragment)
                .commit();

    }

    private void loadDownloadFragment() {
        Download_Fragment fragment = new Download_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_frame, fragment)
                .commit();

    }

    private void loadEventFragment() {
        Event_Fragment fragment = new Event_Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_frame, fragment)
                .commit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Event_Fragment fragment = new Event_Fragment();
        if (savedInstanceState == null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_frame,fragment, fragment.getTag()).commit();
        }

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
