package com.example.myshh.sensorcharts;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * Listener based on other sensor
 * If based on 'onSensorChanged' then chart does not crash
 */

public class fakeSoundListener extends Thread implements SensorEventListener {

    private LineChart soundVolumeChart;
    private double[] maxSoundValue;

    fakeSoundListener(LineChart soundVolumeChart, SensorManager sensorManager, BooleanHolder isSpeakerOutputEnabled){

        this.soundVolumeChart = soundVolumeChart;
        //Create lineDataSet for data from light sensor
        LineDataSet lineDataSet1 = createSet(Color.CYAN, "Microphone");
        LineData lineData = new LineData(lineDataSet1);

        //Set data to chart
        soundVolumeChart.setData(lineData);

        //Register listener
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);

        maxSoundValue = new double[1];
        new threadMicReader(maxSoundValue, isSpeakerOutputEnabled).start();
    }

    //Function for creating datasets
    private LineDataSet createSet(int color, String label){
        LineDataSet set = new LineDataSet(new ArrayList<>(), label);
        set.setDrawCircles(false);
        set.setLineWidth(3f);
        set.setColor(color);
        return set;
    }

    //Function called when light power has changed
    @Override
    public void onSensorChanged(SensorEvent event) {
        //Get chart lineData
        LineData data = soundVolumeChart.getData();

        if (data != null) {
            //Add new point to chart
            data.addEntry(new Entry(data.getDataSetByIndex(0).getEntryCount(), (float)maxSoundValue[0]), 0);

            //After adding
            data.notifyDataChanged();
            soundVolumeChart.notifyDataSetChanged();
            soundVolumeChart.setVisibleXRangeMaximum(100);
            soundVolumeChart.moveViewToX(data.getEntryCount());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
