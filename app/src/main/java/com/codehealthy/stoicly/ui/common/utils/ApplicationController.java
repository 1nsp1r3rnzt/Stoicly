package com.codehealthy.stoicly.ui.common.utils;

import android.app.Application;

import com.codehealthy.stoicly.BuildConfig;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

//import com.squareup.leakcanary.LeakCanary;

public class ApplicationController extends Application {
    private static final boolean LEAK_CANARY_STATUS = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LEAK_CANARY_STATUS) {
            //        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }
    }
}

class DebugTree extends Timber.DebugTree {
    @Override
    protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {
        return String.format("Timber: [L:%s] [M:%s] [C:%s]",
                element.getLineNumber(),
                element.getMethodName(),
                super.createStackElementTag(element));
    }
}