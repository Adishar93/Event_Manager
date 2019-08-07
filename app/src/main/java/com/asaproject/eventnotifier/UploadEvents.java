package com.asaproject.eventnotifier;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class UploadEvents extends AppCompatActivity {

private EditText title,desc;
private ImageView imageView;
private Intent imageIntent;
private UploadImage uploadImage;
private ProgressBar pb;
private static final int PICK_IMAGE=1;
private static Date dateOfEvent;
private boolean IMAGE_SELECTED;
private static boolean DATE_SELECTED;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setTitle("Upload Event");

        //Storing firebase database path in databaseEvents

        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        imageView=findViewById(R.id.imageView);

        //storing firebase storage path in storageRef

        pb= findViewById(R.id.progressBar);
        IMAGE_SELECTED=false;
        DATE_SELECTED=false;


    }

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//
//    }



    //Listener for select image button (onclick listener)
    public void openGallery(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode==RESULT_OK&&requestCode == PICK_IMAGE)
        {
            //Getting the intent containing image data in a class variable
            imageIntent=data;
            imageView.setImageURI(data.getData());
            IMAGE_SELECTED=true;

        }
    }

    //Listener for upload data button(onClick Listener)
    public void addEvents(View v)
    {

        if (IMAGE_SELECTED&&!title.getText().toString().equals("")&&!desc.getText().toString().equals("")&&DATE_SELECTED)
        {
            uploadImage=new UploadImage(getApplicationContext(),imageIntent,pb);
            uploadImage.upload(title.getText().toString(), desc.getText().toString(),dateOfEvent.getTime());

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please fill everything!", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),"Values:"+IMAGE_SELECTED+DATE_SELECTED,Toast.LENGTH_SHORT).show();
        }

    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    @TargetApi(24)
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            if(dateOfEvent==null) {
                final android.icu.util.Calendar c = android.icu.util.Calendar.getInstance();
                int year = c.get(android.icu.util.Calendar.YEAR);
                int month = c.get(android.icu.util.Calendar.MONTH);
                int day = c.get(android.icu.util.Calendar.DAY_OF_MONTH);
                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }
            else
            {
                Calendar cal=Calendar.getInstance();
                cal.setTime(dateOfEvent);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }


        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        try {
//

            dateOfEvent = new SimpleDateFormat("dd/MM/yyyy/HH/mm/ss").parse(day + "/" + (month+1)+ "/" + year+"/23/59/59");
            DATE_SELECTED=true;

//            Log.d("asfasdfaasdfa", "" + dateOfEvent.getTime());
//            Log.d("asfasdfaasdfa", "" + new Date(dateOfEvent.getTime()));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        }
    }



}


