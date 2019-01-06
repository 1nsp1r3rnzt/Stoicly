/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package codehealthy.com.stoicly.UI.common.utility;

import android.app.Application;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import codehealthy.com.stoicly.BuildConfig;
import timber.log.Timber;

public class ApplicationController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        }
    }
}

class DebugTree extends Timber.DebugTree {
    @Override
    protected @Nullable String createStackElementTag(@NotNull StackTraceElement element) {
//        method to show
        return String.format("[L:%s] [M:%s] [C:%s]", element.getLineNumber(), element.getMethodName(), super.createStackElementTag(element));
    }
}