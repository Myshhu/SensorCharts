package com.example.myshh.sensorcharts;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    private LineChart accelerometerChart;
    private LineChart gyroscopeChart;
    private LineChart magneticFieldChart;
    private LineChart lightPowerChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.accelerometerChart = findViewById(R.id.accelerometerChart);
        this.gyroscopeChart = findViewById(R.id.gyroscopeChart);
        this.magneticFieldChart = findViewById(R.id.magneticFieldChart);
        this.lightPowerChart = findViewById(R.id.lightPowerChart);
    }

    public void onBtnStartClick(View v) {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        new Thread(new accelerometerListener(accelerometerChart, sensorManager));
        new Thread(new gyroscopeListener(gyroscopeChart, sensorManager));
        new Thread(new magneticFieldListener(magneticFieldChart, sensorManager));
        new Thread(new lightPowerListener(lightPowerChart, sensorManager));
    }
}
