package com.asaproject.eventnotifier;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ClubActivity extends AppCompatActivity {
    private String club_mail_id =null;
    private DatabaseReference databaseEvent;
    private ArrayList<KeyForEvents> eventsList,eventsListPrev;
    LinearLayoutManager lin,linp;
    RecyclerView cEventList,cEventListPrev;
    CustomRecyclerAdapter customRecyclerAdapter,customRecyclerAdapterPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_activty);
        setTitle("Club UploadEvents");
        databaseEvent = FirebaseDatabase.getInstance().getReference("events");
        eventsList = new ArrayList<>();
        eventsListPrev=new ArrayList<>();

//                ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPage);
//        ImageAdapter adapterView = new ImageAdapter(this,eis);
//        mViewPager.setAdapter(adapterView);
//
//        ViewPager pViewPager = (ViewPager) findViewById(R.id.viewPage2);
//        ImageAdapter adapterView2 = new ImageAdapter(this,eis);
//        pViewPager.setAdapter(adapterView2);

        lin = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        linp = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        cEventList = (RecyclerView) findViewById(R.id.cevent_list);
        cEventListPrev=findViewById(R.id.cevent_list_prev);
        cEventList.setHasFixedSize(true);
        cEventListPrev.setHasFixedSize(true);
        cEventList.setLayoutManager(lin);
        cEventListPrev.setLayoutManager(linp);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;
                int position = layoutManager.getPosition(centerView);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {

                    if (velocityX < 0) {

                        targetPosition = position - 1;
                    } else {

                        targetPosition = position + 1;
                    }

                }

                if (layoutManager.canScrollVertically()) {

                    if (velocityX < 0) {

                        targetPosition = position - 1;
                    } else {

                        targetPosition = position + 1;
                    }

                }
                final int firstitem = 0;
                final int lastitem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastitem, Math.max(targetPosition, firstitem));


                return targetPosition;
            }
        };

        linearSnapHelper.attachToRecyclerView(cEventList);
        linearSnapHelper.attachToRecyclerView(cEventListPrev);


    }


    @Override
    public void onStart() {
        super.onStart();
        club_mail_id = getIntent().getStringExtra("Username");
        Query databasefiltered=databaseEvent.orderByChild("username").equalTo(club_mail_id);

        databasefiltered.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                eventsList.clear();
                eventsListPrev.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                   EventItems ei=ds.getValue(EventItems.class);

                    if(new Date().compareTo(new Date(ei.getDateOfEvent()))==-1)
                        eventsList.add(new KeyForEvents(ei,ds.getKey()));
                    else
                        eventsListPrev.add(new KeyForEvents(ei,ds.getKey()));

                }


                Collections.reverse(eventsList);
                Collections.sort(eventsList);

                Collections.sort(eventsListPrev);
                Collections.reverse(eventsListPrev);


                customRecyclerAdapter=new CustomRecyclerAdapter(eventsList,getApplicationContext());
                customRecyclerAdapterPrev=new CustomRecyclerAdapter(eventsListPrev,getApplicationContext());
                cEventList.setAdapter(customRecyclerAdapter);
                cEventListPrev.setAdapter(customRecyclerAdapterPrev);



            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });




    }

    public void aboutClicked(View view) {
        Intent n = new Intent(getBaseContext(),ClubInfo.class);
        startActivity(n);

    }
}