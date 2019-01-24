package com.example.myshh.sensorcharts;

import android.Manifest;
import android.hardware.SensorManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;

public class MainActivity extends AppCompatActivity {

    private LineChart accelerometerChart;
    private LineChart gyroscopeChart;
    private LineChart magneticFieldChart;
    private LineChart lightPowerChart;
    private LineChart soundVolumeChart;
    private BooleanHolder isSpeakerOutputEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ask for permission
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                10);

        this.accelerometerChart = findViewById(R.id.accelerometerChart);
        this.gyroscopeChart = findViewById(R.id.gyroscopeChart);
        this.magneticFieldChart = findViewById(R.id.magneticFieldChart);
        this.lightPowerChart = findViewById(R.id.lightPowerChart);
        this.soundVolumeChart = findViewById(R.id.soundVolumeChart);
    }

    public void onBtnStartClick(View v) {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        new accelerometerListener(accelerometerChart, sensorManager);
        new gyroscopeListener(gyroscopeChart, sensorManager);
        new magneticFieldListener(magneticFieldChart, sensorManager);
        new lightPowerListener(lightPowerChart, sensorManager);

        isSpeakerOutputEnabled = new BooleanHolder();
        new fakeSoundListener(soundVolumeChart, sensorManager, isSpeakerOutputEnabled);
    }

    public void onBtnEnableSpeakerClick(View v){
        if (isSpeakerOutputEnabled != null) {
            isSpeakerOutputEnabled.setBooleanValue(!isSpeakerOutputEnabled.isBooleanValue());
        }
    }
}
