package com.example.mohammedibrahim.studiomcet;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mohammedibrahim.studiomcet.Data.MyDataSetGet4;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class team extends AppCompatActivity {

    private RecyclerView myRecycleView;
    View v;
    MyAdapter adapter;
    List<MyDataSetGet4> listData;
    FirebaseDatabase FDB;
    DatabaseReference DBR;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        myRecycleView = (RecyclerView) findViewById(R.id.myRecyclerteam);
        myRecycleView.setHasFixedSize(true);
        RecyclerView.LayoutManager LM = new GridLayoutManager(this,2);
        myRecycleView.setLayoutManager(LM);
        myRecycleView.setItemAnimator(new DefaultItemAnimator());
        myRecycleView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));
        listData = new ArrayList<>();
        adapter = new MyAdapter(listData);
        FDB = FirebaseDatabase.getInstance();

        getfirebasedatabase();
    }


    public class MyAdapter extends RecyclerView.Adapter<team.MyAdapter.MyViewHolder>{

        List<MyDataSetGet4>  listArray;

        public  MyAdapter (List<MyDataSetGet4> List){
            this.listArray = List;
        }

        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.team_item_view, parent, false));
        }


        @Override
        public void onBindViewHolder(team.MyAdapter.MyViewHolder holder, int position ){
            MyDataSetGet4 data = listArray.get(position);
            holder.myText.setText(data.getName());
            Glide.with(getApplicationContext()).load(data.getImageurl()).into(holder.Image);


        }

        public  class MyViewHolder extends RecyclerView.ViewHolder{

            TextView myText;

            ImageView Image;

            public MyViewHolder(View team_item_view) {
                super(team_item_view);
                myText = (TextView)team_item_view.findViewById(R.id.nameteamview);
                Image = (ImageView) team_item_view.findViewById(R.id.profileteamview);

            }

        }

        @Override
        public int getItemCount(){

            return  listArray.size();

        }

    }

    private void getfirebasedatabase() {
        DBR =FDB.getReference("image");

        DBR.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MyDataSetGet4 data = dataSnapshot.getValue(MyDataSetGet4.class);
                listData.add(data);
                Log.e("error",data.getImageurl());
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_back) {
            onBackPressed();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }




}


