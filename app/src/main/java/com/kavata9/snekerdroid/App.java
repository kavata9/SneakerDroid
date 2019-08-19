package com.kavata9.snekerdroid;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kavata9.snekerdroid.helpers.AppExecutors;
import com.kavata9.snekerdroid.models.SampleLifeCycle;
import com.kavata9.snekerdroid.networks.ProjectRepository;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * AKM
 */

public class App extends Application {



    public AppExecutors mAppExecutors;

    private static final String TINK_KEYSET_NAME = "app_gen_keyset";
    private static final String MASTER_KEY_URI = "android-keystore://app_gen_key";
    //    public Aead aead;
    public SampleLifeCycle lifecycleListener = new SampleLifeCycle();

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        mAppExecutors = new AppExecutors();
        setupLifecycleListener();


    }


    public AppExecutors getExecutors() {
        return mAppExecutors;
    }

//    public AppDatabase getDatabase() {
//        return AppDatabase.getInstance(this);
//    }

    public ProjectRepository getRepository() {
        return (ProjectRepository) ProjectRepository.getInstance();
    }





    private void setupLifecycleListener() {


    }


}