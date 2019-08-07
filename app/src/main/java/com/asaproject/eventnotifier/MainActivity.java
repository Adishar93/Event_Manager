package com.asaproject.eventnotifier;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.design.widget.NavigationView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
ListView lv;
ArrayList<KeyForEvents> eventsList;
DatabaseReference databaseEvent;
 private  RecyclerView mEventList;
 private TextView navigation_head;
 private ProgressBar progressBar;
 //int lastFirstVisiblePosition=0;


 NavigationView nv;
 private Menu nav_Menu;
 private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nv = (NavigationView) findViewById(R.id.nav_view);
        View header = nv.getHeaderView(0);
        navigation_head = (TextView) header.findViewById(R.id.nav_heading);
        nav_Menu = nv.getMenu();
        progressBar=findViewById(R.id.mainPb);


        linearLayoutManager = new LinearLayoutManager(getBaseContext());
        //linearLayoutManager.setReverseLayout(true);
        //linearLayoutManager.setStackFromEnd(true);
        //linearLayoutManager.getStackFromEnd();
        mEventList = (RecyclerView) findViewById(R.id.event_list);
        mEventList.setHasFixedSize(true);
        mEventList.setLayoutManager(linearLayoutManager);


        FirebaseApp.initializeApp(getApplicationContext());


       // lv=findViewById(R.id.lv);
        databaseEvent= FirebaseDatabase.getInstance().getReference("events");
        eventsList=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){



            @Override
            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_SETTLING) {

                    // starts opening

                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        nav_Menu.findItem(R.id.uploadEvent).setVisible(true);
                        nav_Menu.findItem(R.id.login).setVisible(false);
                        //nav_Menu.findItem(R.id.logout).setTitle("Logout "+FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        nav_Menu.findItem(R.id.logout).setVisible(true);
                        navigation_head.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    } else {
                        nav_Menu.findItem(R.id.uploadEvent).setVisible(false);
                        nav_Menu.findItem(R.id.login).setVisible(true);
                        nav_Menu.findItem(R.id.logout).setVisible(false);
                        navigation_head.setText("Welcome");
                    }
                }
            }
        };
        // Set the drawer toggle as the DrawerListener
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (getIntent().getExtras() != null) {

            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);

                if (key.equals("AnotherActivity") && value.equals("True")) {
                    Intent intent = new Intent(this, AnotherActivity.class);
                    intent.putExtra("value", value);
                    startActivity(intent);
                    finish();
                }

            }
        }

//        subscribeToPushService();

    }


    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Log.d("AndroidBash", "Subscribed");
        Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();

        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
//        Log.d("AndroidBash", token);
        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public void onPause() {
//
//        super.onPause();
//        lastFirstVisiblePosition =linearLayoutManager.findFirstVisibleItemPosition();
//        Log.d("Saved Value", "Hi"+lastFirstVisiblePosition);
//    }
//
//    @Override
//    public void onResume() {
//
//        super.onResume();
//        Log.d("Retrieved Value", "Hi"+lastFirstVisiblePosition);
//
//            mEventList.scrollToPosition(lastFirstVisiblePosition);
//
//    }

//
        @Override
    protected void onStart()
    {
        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
//            nav_Menu.findItem(R.id.uploadEvent).setVisible(true);
//            nav_Menu.findItem(R.id.login).setVisible(false);
//            //nav_Menu.findItem(R.id.logout).setTitle("Logout "+FirebaseAuth.getInstance().getCurrentUser().getEmail());
//            nav_Menu.findItem(R.id.logout).setVisible(true);
//        }
//        else {
//            nav_Menu.findItem(R.id.uploadEvent).setVisible(false);
//            nav_Menu.findItem(R.id.login).setVisible(true);
//            nav_Menu.findItem(R.id.logout).setVisible(false);
//        }


//        FirebaseRecyclerAdapter <EventItems,EventViewHolder> FBRA = new FirebaseRecyclerAdapter<EventItems,EventViewHolder>(EventItems.class,R.layout.event_row,EventViewHolder.class, databaseEvent)
//        {
//            @Override
//          protected void populateViewHolder (EventViewHolder viewHolder,EventItems model,int position){
//                final String post_key = getRef(position).getKey();
//           viewHolder.setTitle(model.getTitle());
//           viewHolder.setDesc(model.getDesc());
//           viewHolder.setUsername(model.getUsername());
//           viewHolder.setImage(getApplicationContext(),model.getImageUrl());
//           viewHolder.mView.setOnClickListener(new View.OnClickListener()
//           {
//               public void onClick(View v){
//
//                   Intent s = new Intent(getApplicationContext(),SingleEvent.class);
//                   s.putExtra("PostId",post_key);
//                   startActivity(s);
//               }
//           });
//            }
//        } ;
//        mEventList.setAdapter(FBRA);



        databaseEvent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                eventsList.clear();

                for(DataSnapshot eventSnapshot:dataSnapshot.getChildren())
                {

                    EventItems ei=eventSnapshot.getValue(EventItems.class);

                    if(new Date().compareTo(new Date(ei.getDateOfEvent()))==-1)
                        eventsList.add(new KeyForEvents(ei,eventSnapshot.getKey()));


                }

                Collections.reverse(eventsList);
                Collections.sort(eventsList);

                 MainCustomRecyclerAdapter adapter=new MainCustomRecyclerAdapter(eventsList,getApplicationContext());
                 progressBar.setVisibility(View.GONE);

               mEventList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            //write your code here for what to do when item clicked
        }
        else if (id == R.id.login) {
Intent l = new Intent(getBaseContext(),LoginActivity.class);
startActivity(l);
        }

        else if (id == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog;

            builder.setTitle("Logout");
            builder.setMessage("Are you Sure you want to Logout?");
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();

                }
            });
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    FirebaseAuth.getInstance().signOut();
                    if (FirebaseAuth.getInstance().getCurrentUser() == null)
                        Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Logout unsuccessful", Toast.LENGTH_SHORT).show();
                }
            });
            dialog=builder.create();
            dialog.show();
        }
        else if (id == R.id.musicclub) {
            Intent c = new Intent(getBaseContext(),ClubActivity.class);
            c.putExtra("Username","musicclub@gmail.com");
            startActivity(c);
        }
        else if (id == R.id.dramaclub) {
            Intent c = new Intent(getBaseContext(),ClubActivity.class);
            c.putExtra("Username","dramaclub@gmail.com");
            startActivity(c);
        }
        else if (id == R.id.danceclub) {
            Intent c = new Intent(getBaseContext(),ClubActivity.class);
            c.putExtra("Username","danceclub@gmail.com");
            startActivity(c);
        }
        else if(id==R.id.uploadEvent)
        {
            startActivity(new Intent(getApplicationContext(),UploadEvents.class));
        }
        else if (id == R.id.rate) {
            startActivity(new Intent(getApplicationContext(),RatingActivity.class));
        }
        else if(id == R.id.about){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog;

        builder.setTitle("Exit");
        builder.setMessage("Are you Sure you want to Exit?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.super.onBackPressed();       }
        });
        dialog=builder.create();
        dialog.show();
    }


//    public static class  EventViewHolder extends RecyclerView.ViewHolder
//    {View mView;
//        public EventViewHolder(View itemView){
//
//            super(itemView);
//            mView = itemView;
//        }
//
////        title=v.findViewById(R.id.title);
//
////        public void setTitle(String title){
////            TextView post_title=(TextView)mView.findViewById(R.id.title);
////            post_title.setText(title);
//        //}
//        public  void setUsername(String username)
//        {
//            TextView post_username = (TextView)mView.findViewById(R.id.textUsername);
//            post_username.setText(username);
//        }
//        public void setTitle(String title)
//        {
//            TextView post_title = (TextView)mView.findViewById(R.id.textTitle);
//            post_title.setText(title);
//        }
//        public  void setDesc(String desc){
//            TextView post_desc=(TextView)mView.findViewById(R.id.textDescription);
//            post_desc.setText(desc);
//        }
//        public void setImage(Context ctx, String image){
//            ImageView post_image=(ImageView)mView.findViewById(R.id.post_image);
//
//            Picasso.get().load(image).transform(new RoundedCornersTransformation(80,0)).into(post_image);
//        }


//    }

}
