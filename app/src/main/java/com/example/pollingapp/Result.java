package com.example.pollingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {
    AnyChartView anyChartView;
    String docId;
    String opt1,opt2;
    int count1,count2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent i=getIntent();
        opt1=i.getStringExtra("opt1");
        opt2=i.getStringExtra("opt2");
        count1=i.getIntExtra("count1",0);
        count2=i.getIntExtra("count2", 0);

        anyChartView= findViewById(R.id.any_chart_view);
        setupPieChart();
    }

    public void setupPieChart(){

        Pie pie= AnyChart.pie();
        List<DataEntry> dataEntries=new ArrayList<>();
        String c1=Integer.toString(count1);
        Toast.makeText(getApplicationContext(),c1, Toast.LENGTH_LONG).show();
        dataEntries.add(new ValueDataEntry(opt1, count1));
        dataEntries.add(new ValueDataEntry(opt2, count2));

        pie.data(dataEntries);
        anyChartView.setChart(pie);
    }
}