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

public class lightPowerListener extends Thread implements SensorEventListener {

    private LineChart lightPowerChart;

    lightPowerListener(LineChart lightPowerChart, SensorManager sensorManager) {

        this.lightPowerChart = lightPowerChart;
        //Create lineDataSet for data from light sensor
        LineDataSet lineDataSet1 = createSet(Color.MAGENTA, "Light");
        LineData lineData = new LineData(lineDataSet1);

        //Set data to chart
        lightPowerChart.setData(lineData);

        //Register listener
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_GAME);
    }

    //Function for creating datasets
    private LineDataSet createSet(int color, String label){
        LineDataSet set = new LineDataSet(new ArrayList<Entry>(), label);
        set.setDrawCircles(false);
        set.setLineWidth(3f);
        set.setColor(color);
        return set;
    }

    //Function called when light power has changed
    @Override
    public void onSensorChanged(SensorEvent event) {
        //Get chart lineData
        LineData data = lightPowerChart.getData();

        if(data != null) {
            //Add new point to chart
            data.addEntry(new Entry(data.getDataSetByIndex(0).getEntryCount(), event.values[0]), 0);

            //After adding
            data.notifyDataChanged();
            lightPowerChart.notifyDataSetChanged();
            lightPowerChart.setVisibleXRangeMaximum(100);
            lightPowerChart.moveViewToX(data.getEntryCount());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
