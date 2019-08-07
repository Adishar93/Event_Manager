package com.asaproject.eventnotifier;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadImage {
    private Intent imageIntent;
    private ProgressBar pb;
    private StorageReference storageRef;
    private Context c;
    private DatabaseReference databaseEvents;
    EventItems ei;

    public UploadImage(Context c, Intent imageIntent, ProgressBar pb)
    {
        this.c = c;
        this.imageIntent = imageIntent;
        this.pb = pb;
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");
        storageRef = FirebaseStorage.getInstance().getReference();
    }

    public void upload(final String title, final String desc,final long date) {
        //Progress bar becomes visible
        pb.setVisibility(ProgressBar.VISIBLE);

        final Long randomName = System.currentTimeMillis();
        //Setting name for image
        final StorageReference eventsRef = storageRef.child("eventimages").child("img" + randomName);

        //Uploading image and checking if successful using listeners


        eventsRef.putFile(imageIntent.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Getting and creating the unique key generated when creating a new entry in database

                //Get Uri of that file reference and store in class Uri
                eventsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ei = new EventItems(title, desc,date, uri.toString(), "img" + randomName,FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        String dbid = databaseEvents.push().getKey();
                        //Setting values
                        databaseEvents.child(dbid).setValue(ei);
                    }
                });



                //Disable progress bar
                pb.setVisibility(ProgressBar.GONE);
                c.startActivity(new Intent(c,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                Toast.makeText(c, "Upload successful", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //Nothing is uploaded only toast is displayed
                        pb.setVisibility(ProgressBar.GONE);
                        Toast.makeText(c, "Sorry! Upload unsuccessful", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}







