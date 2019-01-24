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

public class gyroscopeListener extends Thread implements SensorEventListener {

    private LineChart gyroscopeChart;

    gyroscopeListener(LineChart gyroscopeChart, SensorManager sensorManager) {

        this.gyroscopeChart = gyroscopeChart;
        //Create lineDataSets for 3 axis from gyroscope
        LineDataSet lineDataSet1 = createSet(Color.GREEN, "x");
        LineDataSet lineDataSet2 = createSet(Color.RED, "y");
        LineDataSet lineDataSet3 = createSet(Color.BLUE, "z");
        LineData lineData = new LineData(lineDataSet1, lineDataSet2, lineDataSet3);

        //Set data to chart
        gyroscopeChart.setData(lineData);

        //Register listener
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
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

    //Function called when one of axis values has changed
    @Override
    public void onSensorChanged(SensorEvent event) {
        //Get chart lineData
        LineData data = gyroscopeChart.getData();

        if(data != null) {
            //Add new points to chart
            data.addEntry(new Entry(data.getDataSetByIndex(0).getEntryCount(), event.values[0]), 0);
            data.addEntry(new Entry(data.getDataSetByIndex(1).getEntryCount(), event.values[1]), 1);
            data.addEntry(new Entry(data.getDataSetByIndex(2).getEntryCount(), event.values[2]), 2);

            //After adding
            data.notifyDataChanged();
            gyroscopeChart.notifyDataSetChanged();
            gyroscopeChart.setVisibleXRangeMaximum(100);
            gyroscopeChart.moveViewToX(data.getEntryCount());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
