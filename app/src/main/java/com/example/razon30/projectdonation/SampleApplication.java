package com.example.razon30.projectdonation;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by razon30 on 20-02-16.
 */
public class SampleApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }

}
