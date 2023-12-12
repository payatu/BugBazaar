package com.bug.hook;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class emultorcheck {


    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    static int[] sensorTypes = {
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_GAME_ROTATION_VECTOR,
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_GYROSCOPE_UNCALIBRATED,
            Sensor.TYPE_HEART_RATE,
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_LINEAR_ACCELERATION,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED,
            Sensor.TYPE_ORIENTATION,
            Sensor.TYPE_PRESSURE,
            Sensor.TYPE_PROXIMITY,
            Sensor.TYPE_RELATIVE_HUMIDITY,
            Sensor.TYPE_ROTATION_VECTOR,
            Sensor.TYPE_SIGNIFICANT_MOTION,
            Sensor.TYPE_STEP_COUNTER,
            Sensor.TYPE_STEP_DETECTOR,
            // Add more sensor types as needed
    };

    static Set<Integer> emulatorSensorTypes = new HashSet<>();

    static {
        // Add sensor types that are commonly not available in emulators
        emulatorSensorTypes.add(Sensor.TYPE_AMBIENT_TEMPERATURE);
        emulatorSensorTypes.add(Sensor.TYPE_HEART_RATE);
        emulatorSensorTypes.add(Sensor.TYPE_STEP_COUNTER);
        emulatorSensorTypes.add(Sensor.TYPE_STEP_DETECTOR);
        // Add more sensor types as needed
    }

    public static boolean isEmulator(Context context) {
        //Log.d("cool", "hello");
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        for (int sensorType : sensorTypes) {
            Sensor sensor = sensorManager.getDefaultSensor(sensorType);
            //Log.d("hellocool", String.valueOf(sensor));

            if (emulatorSensorTypes.contains(sensorType)) {

              //  Log.d("hellocool", String.valueOf(sensorType));
                // Sensor of the specified type is not available on this device (likely an emulator)
                return true;
            }
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean isEmulator = false;

        if (networkInfo == null || !networkInfo.isConnected()) {
            // No network connectivity (may indicate emulator)
            isEmulator = true;
        }

        // Add other checks if needed

        return isEmulator;
    }
}
