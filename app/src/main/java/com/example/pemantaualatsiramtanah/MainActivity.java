package com.example.pemantaualatsiramtanah;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.pemantaualatsiramtanah.FragmentCode.fragAbout;
import com.example.pemantaualatsiramtanah.FragmentCode.fragChart;
import com.example.pemantaualatsiramtanah.FragmentCode.fragHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.pemantaualatsiramtanah.NotificationChannelCap.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    //Nav_drawer and Toolbar
    Toolbar mTopToolbar;
     NotificationManagerCompat notificationManager;
    DrawerLayout drawer;
    FrameLayout frameLayout;


    //boolean State
    Boolean tanahL1 ;
    Boolean tanahL2 ;
    Boolean tanahL3 ;
    Boolean sprinklerState=true;


    //Firebase Auth & Database
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference sistemDatabase = database.getReference("Sistem");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FIREBASE_AUTH STATE
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        //BooleanState Value
        tanahL1 = false;
        tanahL2 =true;
        tanahL3 = false;



        /// TOOLBAR AND NAV_DRAWER
        frameLayout = findViewById(R.id.fragment_container);
        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        //SET DRAWER NAVIGATION
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mTopToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //SET LAYOUT TO BE HOME WHEN OPEN APP
        if(savedInstanceState == null) {
            frameLayout.setVisibility(View.GONE);
            navigationView.setCheckedItem(R.id.nav_home);
        }

        notificationManager = NotificationManagerCompat.from(this);

        //REPLACING THE LAYOUT FROM BLANK TO MAIN LAYOUT ((HOME))
        frameLayout.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new fragHome()).commit();

        DataDariFirebase();


    }

    //RETRIVE DATA FROM DATABASE , THEN SHOW THE NOTIFICATION BASED ON LOGIC
    public void DataDariFirebase() {


        sistemDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean sprinklestate = dataSnapshot.child("stat_sprinkler").getValue(Boolean.class);


                if(String.valueOf(sprinklestate).equals("true")&&sprinklerState){

                    shownotification("Sprinkler","Sprinkler telah nyala",1);
                    sprinklerState=false;



                }
                else if (String.valueOf(sprinklestate).equals("false")&&!sprinklerState){

                    shownotification("Sprinkler","Sprinkler telah mati",1);
                    sprinklerState=true;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sistemDatabase.child("kelembapan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer kelembapan = dataSnapshot.getValue(Integer.class);

                if(kelembapan<60&&tanahL1 ){
                    shownotification("Kelembapan","Kelembapan tanah dibawah level normal",0);

                    tanahL1  = false;
                    tanahL2=true;
                    tanahL3=true;
                }
                else if (kelembapan>60&&kelembapan<70&&tanahL2 ) {
                    shownotification("Kelembapan","kelembapan tanah mendekai dibawah level normal," +
                            " segera nyalakan sprinkler",0);


                    tanahL1  = true;
                    tanahL2=false;
                    tanahL3=true;
                }
                else {
                    shownotification("Kelembapan","kelembapan tanah normal",0);

                    tanahL1  = true;
                    tanahL2=true;
                    tanahL3=false;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    //NOTIFICATION CODE
    public void shownotification(String title, String content, int id) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.arrow)
                        .setContentTitle(title)
                        .setContentText(content);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(id, builder.build());
        Intent notificationIntent = new Intent(this, LoginActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(this, id, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());

    }


    //NAVIGATION CODE
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                frameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragHome()).commit();
                break;
            case R.id.nav_chart:
                frameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragChart()).commit();
                break;
            case R.id.nav_about:
                frameLayout.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new fragAbout()).commit();
                break;
            case R.id.nav_logout:
                alertLogout();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //LOGOUT ALERT
    public void alertLogout() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Logout ?");
        builder1.setMessage("");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));


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
}


