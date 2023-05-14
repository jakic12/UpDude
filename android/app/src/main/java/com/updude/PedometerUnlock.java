package com.updude;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;


import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class Pedometer extends ReactContextBaseJavaModule{
    private static final String TAG = "Pedometer";

    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int initialStepCount;
    private boolean isListening;
    private PedometerListener listener;

    public Pedometer(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        initialStepCount = 0;
        isListening = false;
        listener = new PedometerListener();
    }

    public void start() {
        if (!isListening) {
            sensorManager.registerListener(listener, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isListening = true;
        }
    }

    public void stop() {
        if (isListening) {
            sensorManager.unregisterListener(listener);
            isListening = false;
        }
    }

    public int getStepCount() {
        if (initialStepCount > 0) {
            return (int) (listener.currentStepCount - initialStepCount);
        } else {
            return 0;
        }
    }

    @Override
    public String getName() {
        return "Pedometer";
    }


    private class PedometerListener implements SensorEventListener {
        private int currentStepCount = 0;

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (initialStepCount == 0) {
                initialStepCount = (int) event.values[0];
            }
            currentStepCount = (int) event.values[0];
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do nothing
        }
    }
}
