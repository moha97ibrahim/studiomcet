package com.example.mohammedibrahim.studiomcet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment  {

    public static ProfileFragment newInstances()  {
        return new ProfileFragment();

    }
    private TextView FEEDBACK,ABOUTUS,TEAM,CONTACTUS,LOGOUT,getuser;
    private FirebaseAuth firebaseAuth;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        FEEDBACK= rootView.findViewById(R.id.feedbackActivity);
        ABOUTUS= rootView.findViewById(R.id.aboutusActivity);
        TEAM = rootView.findViewById(R.id.teamactivity);
        CONTACTUS = rootView.findViewById(R.id.contactusactivity);
        LOGOUT = rootView.findViewById(R.id.logoutactivity);
        getuser = rootView.findViewById(R.id.getusremail);

        getuser.setText(firebaseAuth.getInstance().getCurrentUser().getEmail());


        FEEDBACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),feedback.class);
                startActivity(i);
            }
        });

        ABOUTUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),aboutus.class);
                startActivity(i);

            }
        });
        TEAM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),team.class);
                startActivity(i);

            }
        });
        CONTACTUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),contactus.class);
                startActivity(i);

            }
        });
        LOGOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth=FirebaseAuth.getInstance();
                firebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(),LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);


            }
        });




        return rootView;
    }




}
