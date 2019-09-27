package com.example.mohammedibrahim.studiomcet;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohammedibrahim.studiomcet.Data.MyDataSetGet1;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Event_Fragment extends Fragment {

    public static Event_Fragment newInstances() {
        return new Event_Fragment();
        // Required empty public constructor
    }

    private RecyclerView myRecycleView;
    View v;
    Event_Fragment.MyAdapter adapter;
    List<MyDataSetGet1> listData;
    FirebaseDatabase FDB;
    DatabaseReference DBR;
    ProgressDialog progress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event_, container, false);
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){

        super.onViewCreated(view,savedInstanceState);
        this.v=view;
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Fetching");
        progress.setCancelable(false);
        progress.show();
        init();


    }

    public class MyAdapter extends RecyclerView.Adapter<Event_Fragment.MyAdapter.MyViewHolder>{

        List<MyDataSetGet1>  listArray;

        public  MyAdapter (List<MyDataSetGet1> List){

            this.listArray = List;
        }

        @Override
        public Event_Fragment.MyAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent , int viewType){

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_view,parent,false);
            return new Event_Fragment.MyAdapter.MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(Event_Fragment.MyAdapter.MyViewHolder holder, int position ){
            MyDataSetGet1 data = listArray.get(position);

            holder.myText.setText(data.getEventname());

            holder.myText1.setText(data.getEventdate());

            holder.myText2.setText(data.getEventlink());

            holder.myText3.setText(data.getEventoraganizer());

        }

        public  class MyViewHolder extends RecyclerView.ViewHolder{



            TextView myText,myText1,myText2,myText3;

            public MyViewHolder(View event_item_view) {
                super(event_item_view);


                myText = (TextView)event_item_view.findViewById(R.id.eventname_text);
                myText1 = (TextView)event_item_view.findViewById(R.id.eventdate);
                myText2 = (TextView)event_item_view.findViewById(R.id.eventlink);
                myText3 = (TextView)event_item_view.findViewById(R.id.eventorganizer);


            }

        }

        @Override
        public int getItemCount(){


            progress.dismiss();
            return  listArray.size();

        }

    }

    private void init() {

        myRecycleView = (RecyclerView)v.findViewById(R.id.myRecyclerevent);
        myRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        myRecycleView.setLayoutManager(LM);
        myRecycleView.setItemAnimator(new DefaultItemAnimator());
        myRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        listData = new ArrayList<>();
        adapter = new Event_Fragment.MyAdapter(listData);
        FDB = FirebaseDatabase.getInstance();

        getfirebasedatabase();
    }

    private void getfirebasedatabase() {

        DBR =FDB.getReference("event");

        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                MyDataSetGet1 data = dataSnapshot.getValue(MyDataSetGet1.class);
                listData.add(data);
                myRecycleView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
