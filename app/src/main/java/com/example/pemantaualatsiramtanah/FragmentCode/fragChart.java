package com.example.pemantaualatsiramtanah.FragmentCode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pemantaualatsiramtanah.R;
import com.example.pemantaualatsiramtanah.TempleteChart;
import com.example.pemantaualatsiramtanah.booleanChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class fragChart extends Fragment {


    //Chart
    LineChart mlineChart;
    LineDataSet lineDataSet = new LineDataSet (null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;
    ArrayList<Entry>yValue;

    Boolean nitroBool,psfrBool,kaliBool,humiBool;




    Button nitrogenButton, phosphorousButton, kaliumButton, kelembapanButton;


    //Firebase databasae path
    //Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference sistemDatabase = database.getReference("Sistem");
    DatabaseReference histodyDB = sistemDatabase.child("history");


    //get DATE in second
    Date now = new Date();
    long tanggalpenentu = now.getTime()/1000;
    SimpleDateFormat sdf = new SimpleDateFormat("mm-HH-dd");





    int aCoba;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragchart, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        mlineChart=view.findViewById(R.id.lineChartFrag);
        nitrogenButton=view.findViewById(R.id.nitrogenButon);
        phosphorousButton=view.findViewById(R.id.phosphorousButton);
        kaliumButton=view.findViewById(R.id.kaliumButton);
        kelembapanButton=view.findViewById(R.id.kelembapanButton);




        mlineChart.setDragEnabled(true);
        mlineChart.setScaleEnabled(true);
        styleGraph();

        yValue = new ArrayList<>();


        //GET DATA FIREBASE TO CHART BY BUTTON CLICKED

        nitrogenButton.performClick();

        nitrogenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nitroBool =true;
                psfrBool= false;
                kaliBool=false;
                humiBool=false;




                iLineDataSets.clear();
                yValue.clear();
                mlineChart.invalidate();
                mlineChart.clear();
                histodyDB.addValueEventListener(new ValueEventListener() {




                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            for(DataSnapshot mydatasnapshot : dataSnapshot.getChildren()){
                                final TempleteChart datapoint = mydatasnapshot.getValue(TempleteChart.class);



                                if(datapoint.getTime()>=1538927258&&nitroBool){
                                    yValue.add(new Entry(datapoint.getTime(),(long)datapoint.getNitrogen()));
                                    if(yValue.size()>4){
                                        // yValue.remove(0);
                                    }
                                }

                                mlineChart.invalidate();


                            }
                            showChart(yValue);
                        }
                        else {
                            mlineChart.clear();
                            mlineChart.invalidate();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        phosphorousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nitroBool =false;
                psfrBool= true;
                kaliBool=false;
                humiBool=false;


                iLineDataSets.clear();
                yValue.clear();
                mlineChart.invalidate();
                mlineChart.clear();
                histodyDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            for(DataSnapshot mydatasnapshot : dataSnapshot.getChildren()){
                                final TempleteChart datapoint = mydatasnapshot.getValue(TempleteChart.class);




                                if(datapoint.getTime()>=1538927258&&psfrBool){
                                    yValue.add(new Entry(datapoint.getTime(),(long)datapoint.getPhosphorous()));
                                    if(yValue.size()>4){
                                        // yValue.remove(0);
                                    }
                                }

                                mlineChart.invalidate();

                            }
                            showChart(yValue);
                        }
                        else {
                            mlineChart.clear();
                            mlineChart.invalidate();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        kaliumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                nitroBool =false;
                psfrBool= false;
                kaliBool=true;
                humiBool=false;


                iLineDataSets.clear();
                yValue.clear();
                mlineChart.invalidate();
                mlineChart.clear();
                histodyDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            for(DataSnapshot mydatasnapshot : dataSnapshot.getChildren()){
                                final TempleteChart datapoint = mydatasnapshot.getValue(TempleteChart.class);




                                if(datapoint.getTime()>=1538927258&&kaliBool){
                                    yValue.add(new Entry(datapoint.getTime(),(long)datapoint.getKalium()));
                                    if(yValue.size()>4){
                                        // yValue.remove(0);
                                    }
                                }
                                mlineChart.invalidate();



                            }
                            showChart(yValue);
                        }
                        else {
                            mlineChart.clear();
                            mlineChart.invalidate();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        kelembapanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                nitroBool =false;
                psfrBool= false;
                kaliBool=false;
                humiBool=true;


                iLineDataSets.clear();
                yValue.clear();
                mlineChart.invalidate();
                mlineChart.clear();
                histodyDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren()){
                            for(DataSnapshot mydatasnapshot : dataSnapshot.getChildren()){
                                final TempleteChart datapoint = mydatasnapshot.getValue(TempleteChart.class);



                                if(datapoint.getTime()>=1538927258&&humiBool){
                                    yValue.add(new Entry(datapoint.getTime(),(long)datapoint.getKelembapan()));
                                    if(yValue.size()>4){
                                        // yValue.remove(0);
                                    }
                                }

                                mlineChart.invalidate();


                            }
                            showChart(yValue);
                        }
                        else {
                            mlineChart.clear();
                            mlineChart.invalidate();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });






    }


    private void showChart(ArrayList<Entry> yValue) {

        //CONFIGURATION LINE CHART
        lineDataSet.setValues(yValue);
        lineDataSet.setValueTextSize(15f);
        lineDataSet.setCircleRadius(8f);
        lineDataSet.setLabel("Menit-Jam-Hari");
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);

        lineData=new LineData(iLineDataSets);
        mlineChart.clear();
        mlineChart.setData(lineData);
        mlineChart.invalidate();

    }

    private void styleGraph () {

        //CUSTOM X AND Y AXIS
        final XAxis xAxis = mlineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(45);


        mlineChart.getAxisRight().setEnabled(false);


        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                xAxis.setLabelCount(5,true);
                long datenya = (long) value;

                return sdf.format(datenya*1000) ;

            }
        });

    }


}
