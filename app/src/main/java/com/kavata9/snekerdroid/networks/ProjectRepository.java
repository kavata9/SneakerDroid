package com.kavata9.snekerdroid.networks;


import android.content.res.Resources;
import android.os.Build;
import android.util.Log;


import androidx.annotation.RequiresApi;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.kavata9.snekerdroid.BuildConfig;
import com.kavata9.snekerdroid.helpers.Status;
import com.kavata9.snekerdroid.interfaces.ProgressInterface;
import com.kavata9.snekerdroid.models.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;



import java.util.HashMap;



import java.util.Objects;

import java.util.concurrent.TimeUnit;


import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static com.kavata9.snekerdroid.networks.APIService.CONNECT_TIMEOUT;
import static com.kavata9.snekerdroid.networks.APIService.READ_TIMEOUT;
import static com.kavata9.snekerdroid.networks.APIService.WRITE_TIMEOUT;


/**
 * Created by AKM on 8/19/18.
 */

public class ProjectRepository {

    private static ProjectRepository projectRepository;

    private APIService service;

    //constructor
    private ProjectRepository () {



        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .dispatcher(dispatcher)
                .addInterceptor(logging);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        service = retrofit.create(APIService.class);




    }
    //Singleton
    public synchronized static ProjectRepository getInstance() {

        if (projectRepository == null) {
            synchronized (ProjectRepository.class) {
                if (projectRepository == null) {
                    projectRepository = new ProjectRepository();
                }
            }
        }

        return projectRepository;
    }


    public void customersRegister(final ProgressInterface<HashMap<Status, String>> progressInterface, HashMap<String, Object> map) {


        service.customerCall(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                HashMap<Status, String> hashMap = new HashMap<>();

                if (response.isSuccessful()) {
                    hashMap.put(Status.SUCCESS, "SUCCESS");


                    progressInterface.onResult(hashMap);
                    Log.d(TAG, "onResponse: " + response.body().getAccessToken());

                } else {
                    //hide loading
                    progressInterface.onResult(hashMap);

                    hashMap.put(Status.CONNECTION_ERROR,"fail");
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(ProjectRepository.class.getSimpleName(), "onFailure: " + t.toString());

                HashMap<Status, String> newMap = new HashMap<>();

                //hide loading
                progressInterface.onResult(newMap);

            }
        });


    }




}










