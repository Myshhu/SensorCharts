package com.example.myshh.sensorcharts;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    private LineChart accelerometerChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.accelerometerChart = findViewById(R.id.accelerometerChart);
    }

    public void onBtnStartClick(View v) {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        new Thread(new accelerometerListener(accelerometerChart, sensorManager));
    }
}
