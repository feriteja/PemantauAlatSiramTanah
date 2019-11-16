package com.example.pemantaualatsiramtanah.FragmentCode;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pemantaualatsiramtanah.LoginActivity;
import com.example.pemantaualatsiramtanah.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class fragHome extends Fragment {


    //deklarasi   Objek Coding
        TextView nitrogenTxt,psforTxt,kaliumTxt,humiTxt;
        CardView SprinkleButtonOn, SprinkleButtonOff;
        RelativeLayout springkleStateColorOn,springkleStateColorOff;

        boolean sprinkleState;



    //Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference sistemDatabase = database.getReference("Sistem");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fraghome, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        sprinkleState=true;


        //inisialisasi objek
        nitrogenTxt = view.findViewById(R.id.nitrogenText);
        psforTxt=view.findViewById(R.id.psforText);
        kaliumTxt=view.findViewById(R.id.kaliumText);
        humiTxt=view.findViewById(R.id.humiText);
        SprinkleButtonOn=view.findViewById(R.id.SprinkleButtonOn);
        SprinkleButtonOff=view.findViewById(R.id.SprinkleButtonOff);
        springkleStateColorOn=view.findViewById(R.id.sprinkleImageOn);
        springkleStateColorOff=view.findViewById(R.id.sprinkleImageOff);


        SprinkleButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertSprinklerOn();
                }


        });

        SprinkleButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                alertSprinklerOff();

            }


        });


        DataDariFirebase();
    }

    public void alertSprinklerOn() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle("Turn on sprinkler");
        builder1.setMessage("do you want to turn on the splinker ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sistemDatabase.child("control_sprinkler").setValue(true);

                        springkleStateColorOn.setBackgroundColor(Color.parseColor("#CCF7B2B2"));
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void alertSprinklerOff() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setTitle("Turn off sprinkler");
        builder1.setMessage("do you want to turn off the splinker ?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sistemDatabase.child("control_sprinkler").setValue(false);



                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public void DataDariFirebase() {

        sistemDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer nitrogen = dataSnapshot.child("nitrogen").getValue(Integer.class);
                Integer phosphorus = dataSnapshot.child("phosphorus").getValue(Integer.class);
                Integer kalium = dataSnapshot.child("kalium").getValue(Integer.class);
                Integer kelembapan = dataSnapshot.child("kelembapan").getValue(Integer.class);
                Boolean sprinklestate = dataSnapshot.child("stat_sprinkler").getValue(Boolean.class);

                nitrogenTxt.setText(String.valueOf(nitrogen));
                psforTxt.setText(String.valueOf(phosphorus));
                kaliumTxt.setText(String.valueOf(kalium));
                humiTxt.setText(String.valueOf(kelembapan));

//                springkleStateColorOff.setBackgroundColor(Color.parseColor("#ffffff"));


                if(String.valueOf(sprinklestate).equals("true")){
                    springkleStateColorOff.setBackgroundColor(Color.parseColor("#ffffff"));
                    springkleStateColorOn.setBackgroundColor(Color.parseColor("#CC9DF8BE"));



                }
                else if (String.valueOf(sprinklestate).equals("false")){
                    springkleStateColorOff.setBackgroundColor(Color.parseColor("#CCF7B2B2"));
                    springkleStateColorOn.setBackgroundColor(Color.parseColor("#ffffff"));


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }





}
