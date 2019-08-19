package com.kavata9.snekerdroid.networks;

import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kavata9.snekerdroid.BuildConfig;
import com.kavata9.snekerdroid.helpers.Status;
import com.kavata9.snekerdroid.interfaces.ProgressInterface;

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


    public void customersRegister(final ProgressInterface<HashMap<Status, String>> progressInterface, HashMap<String, Object> data) {

        service.customerCall("/api/v1/mobile/participants/", data).enqueue(new Callback<JsonObject>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                HashMap<Status, String> map = new HashMap<>();

                if (response.isSuccessful()) {

                    map.put(Status.SUCCESS, "You have been successfully registered. Log in to continue.");

                    progressInterface.onResult(map);


                } else {

                    JSONObject jObjError = null;
                    String message = "Failed. Something went wrong";
                    try {
                        jObjError = new JSONObject(Objects.requireNonNull(response.errorBody()).string());
                        JsonParser jsonParser = new JsonParser();
                        JsonObject gsonObject = (JsonObject) jsonParser.parse(jObjError.toString());
                        JsonObject error = gsonObject.get("error").getAsJsonObject();
                        message = error.get("message").getAsString();

                        if (BuildConfig.DEBUG) {
                            Log.i(ProjectRepository.class.getSimpleName(), "onResponse: " + message);
                        }
                        map.put(Status.FAIL, message);
                        progressInterface.onResult(map);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                HashMap<Status, String> map = new HashMap<>();
                map.put(Status.FAIL, "Failed. Something went wrong.\nTry again later");
                progressInterface.onResult(map);


            }
        });
    }




}










