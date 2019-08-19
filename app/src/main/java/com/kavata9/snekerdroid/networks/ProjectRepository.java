package com.kavata9.snekerdroid.networks;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import okhttp3.CacheControl;
import okhttp3.CertificatePinner;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.cache.CacheInterceptor;
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
                .certificatePinner(cp)
                .addNetworkInterceptor(new CacheInterceptor())
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
    synchronized static ProjectRepository getInstance(final AppDatabase database) {

        if (projectRepository == null) {
            synchronized (ProjectRepository.class) {
                if (projectRepository == null) {
                    projectRepository = new ProjectRepository(database);
                }
            }
        }

        return projectRepository;
    }






    /*  public LiveData<List<LoaneeModel>> getSelectedLoaneeDelete(final AppExecutors executors, final List<String> userList) {

        final MutableLiveData<List<LoaneeModel>> data = new MutableLiveData<>();

        if (!executors.serviceIO().isShutdown() || !executors.serviceIO().isTerminated()) {

            Callable<List<LoaneeModel>> callable = () -> {
                return null;//mDb.loanDAO().livefetchSelected(userList);
            };
            Future<List<LoaneeModel>> future = executors.serviceIO().submit(callable);
            // future.get() returns list or raises an exception if the thread dies, so safer

            try {
                data.setValue(future.get());
                if (BuildConfig.DEBUG) {
                    Log.i(ProjectRepository.class.getSimpleName(), "getSelectedLoanee: " + future.get());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (BuildConfig.DEBUG) {
                Log.i(ProjectRepository.class.getSimpleName(), "DATA: " + new Gson().toJson(data, MutableLiveData.class));
            }

            //executors.serviceIO().shutdown();

        } else {
            System.out.println("ServiceIO shutdown");
        }
        return data;

        }
    */




    public void customersRegister(ProgressInterface<HashMap<Status, String>> progressInterface, HashMap<String, Object> data) {

        service.customerCall("", data).enqueue(new Callback<JsonObject>() {
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










