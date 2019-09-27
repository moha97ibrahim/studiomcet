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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohammedibrahim.studiomcet.Data.MyDataSetGet;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class Download_Fragment extends Fragment {
    public static Download_Fragment newInstances(){
        return new Download_Fragment();
    }

    private RecyclerView myRecycleView;
    View v;
    MyAdapter adapter;
    List<MyDataSetGet> listData;
    FirebaseDatabase FDB;
    DatabaseReference DBR;
    ProgressDialog progress;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_download_, container, false);



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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        List<MyDataSetGet>  listArray;

        public  MyAdapter (List<MyDataSetGet> List){
            this.listArray = List;
            }

            @Override
            public MyAdapter.MyViewHolder onCreateViewHolder (ViewGroup parent ,int viewType){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_item_view,parent,false);
                return new MyViewHolder(view);
            }


            @Override
            public void onBindViewHolder(MyAdapter.MyViewHolder holder,int position ){
            MyDataSetGet data = listArray.get(position);
                holder.myText.setText(data.getDownloadname());
                holder.myText1.setText(data.getDownloaddate());
                holder.myText2.setText(data.getDownloadlink());
                holder.myText3.setText(data.getDownloadevetorgan());

            }

            public  class MyViewHolder extends RecyclerView.ViewHolder{

            TextView myText,myText1,myText2,myText3;

                public MyViewHolder(View download_item_view) {
                    super(download_item_view);
                    myText = (TextView)download_item_view.findViewById(R.id.downloadlink_text);
                    myText1 = (TextView)download_item_view.findViewById(R.id.eventdatedow);
                    myText2 = (TextView)download_item_view.findViewById(R.id.eventlinkdow);
                    myText3 = (TextView)download_item_view.findViewById(R.id.eventorganizerdow);
                }

        }

        @Override
        public int getItemCount(){
            progress.dismiss();
            return  listArray.size();

        }

    }



    private void init() {
        myRecycleView = (RecyclerView)v.findViewById(R.id.myRecycler);
        myRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(getContext());
        myRecycleView.setLayoutManager(LM);
        myRecycleView.setItemAnimator(new DefaultItemAnimator());
        myRecycleView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        listData = new ArrayList<>();
        adapter = new MyAdapter(listData);
        FDB = FirebaseDatabase.getInstance();

        getfirebasedatabase();


    }

    private void getfirebasedatabase() {
        DBR =FDB.getReference("link");

        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MyDataSetGet data = dataSnapshot.getValue(MyDataSetGet.class);
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
