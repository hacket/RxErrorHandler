/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.rxerrorhandler.demo;

import android.app.Application;
import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.exceptions.UndeliverableException;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import me.jessyan.rxerrorhandler.handler.listener.UndeliverableErrorListener;

/**
 * ================================================ Created by JessYan on 22/09/2017 15:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class App extends Application {
    private final String TAG = getClass().getSimpleName();
    private RxErrorHandler mRxErrorHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        //Initialization
        mRxErrorHandler = RxErrorHandler
                .builder()
                .with(this)
                .responseErrorListener(new ResponseErrorListener() {
                    @Override
                    public void handleResponseError(Context context, Throwable t) {
                        if (t instanceof UnknownHostException) {
                            //do something ...
                        } else if (t instanceof SocketTimeoutException) {
                            //do something ...
                        } else if (t instanceof ParseException || t instanceof JSONException) {
                            //do something ...
                        } else {
                            //handle other Exception ...
                        }
                        Log.w(TAG, "Error handle");
                    }
                }).build();

        RxErrorHandler.setErrorHandler(new UndeliverableErrorListener() {
            @Override
            public void handleError(Throwable e) {
                if (e instanceof UndeliverableException) {
                    e = e.getCause();
                }
                if ((e instanceof SocketException) || (e instanceof IOException)) {
                    // fine, irrelevant network problem or API that throws on cancellation
                    return;
                }
                if (e instanceof InterruptedException) {
                    // fine, some blocking code was interrupted by a dispose call
                    return;
                }
                if ((e instanceof NullPointerException) || (e instanceof IllegalArgumentException)) {
                    // that's likely a bug in the application
                    Thread.currentThread()
                            .getUncaughtExceptionHandler()
                            .uncaughtException(Thread.currentThread(), e);
                    return;
                }
                if (e instanceof IllegalStateException) {
                    // that's a bug in RxJava or in a custom operator
                    Thread.currentThread()
                            .getUncaughtExceptionHandler()
                            .uncaughtException(Thread.currentThread(), e);
                    return;
                }
            }
        });
    }

    public RxErrorHandler getRxErrorHandler() {
        return mRxErrorHandler;
    }
}
