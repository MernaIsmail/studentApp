package com.example.merna.studentapp;

import com.estimote.sdk.BeaconManager;
import com.firebase.client.Firebase;

/**
 * Includes one-time initialization of Firebase related code
 */
public class AttendanceListApplication extends android.app.Application {
    private BeaconManager beaconManager;


    @Override
    public void onCreate() {
        super.onCreate();
   /* Initialize Firebase */
        Firebase.setAndroidContext(this);

    }

}