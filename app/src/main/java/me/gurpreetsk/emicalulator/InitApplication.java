package me.gurpreetsk.emicalulator;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by gurpreet on 05/05/17.
 */

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
