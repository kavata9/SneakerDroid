package com.kavata9.snekerdroid.networks;


import com.google.gson.JsonObject;
import com.kavata9.snekerdroid.models.ResponseBody;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by AKM on 8/19/18.
 */
public interface APIService {

    int CONNECT_TIMEOUT = 22 * 1000;

    //Connection Read timeout duration
    int READ_TIMEOUT = 22 * 1000;

    //Connection write timeout duration
    int WRITE_TIMEOUT = 22 * 1000;

    String BASE_URL = "http://interviews.busaracenterlab.org";

    String FCM_KEY = "BJAa-icl1LMPeht2610oZCsGgfxvXpAw3M7Zc7xJMlF307ttKzD2bCuh0W2myaTxIRhCpOu8N58Cff73V7N9Qls";
    String UPLOAD_URL = "http://interviews.busaracenterlab.org";

    @Headers( "Content-Type: application/json" )
    @POST("/api/v1/mobile/participants/")
    Call<ResponseBody> customerCall(@Body HashMap<String, Object> map);

    @Headers( "Content-Type: application/json" )
    @POST("/api/v1/mobile/probes-data/create-bulk")
    Call<JsonObject> dataUpload(@Query("access_token") String token, @Body Map<String, Object> data);





}
