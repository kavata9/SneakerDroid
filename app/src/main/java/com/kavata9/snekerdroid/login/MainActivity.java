package com.kavata9.snekerdroid.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.kavata9.snekerdroid.App;
import com.kavata9.snekerdroid.R;
import com.kavata9.snekerdroid.helpers.Status;
import com.kavata9.snekerdroid.interfaces.ProgressInterface;
import com.kavata9.snekerdroid.models.DeviceDetails;
import com.kavata9.snekerdroid.networks.APIService;
import com.kavata9.snekerdroid.networks.ProjectRepository;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static com.kavata9.snekerdroid.App.getContext;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

  private static final String TAG = "MainActivity";

  //    widgets
  private EditText editTextfirstname;
  private EditText editTextlastname;
  private EditText editTextphone;
  private EditText editTextprojectcode;
  private EditText editTextappversion;
  private EditText editTextfcmkey;

  private TextView TextviewDevicemodel;
  private TextView TextviewDeviceType;
  private TextView TextviewDeviceHardware;
  private TextView TextviewDevicemanufacturer;

  private CountryCodePicker ccp;

  private Button mButtonRegister;
  private ProjectRepository repo;

  private LinearLayout mLinearLayout;
  private LinearLayout mLinearLayout1;

  //    vars
  ArrayList<DeviceDetails> mDeviceDetails = new ArrayList<>();
  String deviceModel = android.os.Build.MODEL;
  String deviceType = android.os.Build.DEVICE;
  String hardware = android.os.Build.HARDWARE;
  String manufacture = android.os.Build.MANUFACTURER;

  String selected_country_code;
  String fullNumber;

  //    userDetails fields
  String firstName;
  String lastName;
  String phone;
  String projectCode;
  String appVersion;
  String fcmKey;

  //    create new instance of the DeviceDetails model class
  DeviceDetails mDeviceDetailes = new DeviceDetails();

  @RequiresApi(api = Build.VERSION_CODES.KITKAT)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

    }
    setContentView(R.layout.activity_main);
    repo = ((App) App.getContext()).getRepository();
    Log.d(TAG, "onCreate: " + deviceModel + deviceType);

    //    initialize the widgets

    editTextfirstname = findViewById(R.id.firstname);
    editTextlastname = findViewById(R.id.lastname);
    editTextphone = findViewById(R.id.input_number);
    editTextprojectcode = findViewById(R.id.projectcode);
    editTextappversion = findViewById(R.id.appversion);
    editTextfcmkey = findViewById(R.id.fcmkey);

    TextviewDevicemodel = findViewById(R.id.devicemodel);
    TextviewDeviceType = findViewById(R.id.devicetype);
    TextviewDeviceHardware = findViewById(R.id.hardware);
    TextviewDevicemanufacturer = findViewById(R.id.manufacturer);

    ccp = findViewById(R.id.ccp);

    mButtonRegister = findViewById(R.id.register);

    mLinearLayout = findViewById(R.id.detailsGroup);
    mLinearLayout1 = findViewById(R.id.sensitiveData);

    mButtonRegister.setOnClickListener(this);

    // this method is used to hide the views
    hideViews();

    setDeviceDetails();

  }

  private void hideViews() {
    Log.d(TAG, "hideViews: that the user doesnt have to see");
    mLinearLayout1.setVisibility(View.GONE);
    mLinearLayout.setVisibility(View.GONE);
  }


  private void setDeviceDetails() {
    mDeviceDetailes.setDeviceModel(deviceModel);
    mDeviceDetailes.setDeviceType(deviceType);
    mDeviceDetailes.setHardware(hardware);
    mDeviceDetailes.setManufacturer(manufacture);

    getDeviceDetails();
  }

  private void getDeviceDetails() {
    Log.d(TAG, "getDeviceDetails: " + mDeviceDetailes.getDeviceModel());

    //   editText fcm key
    editTextfcmkey.setText(APIService.FCM_KEY);

    TextviewDevicemodel.setText(mDeviceDetailes.getDeviceModel());
    TextviewDeviceType.setText(mDeviceDetailes.getDeviceType());
    TextviewDeviceHardware.setText(mDeviceDetailes.getHardware());
    TextviewDevicemanufacturer.setText(mDeviceDetailes.getManufacturer());
  }

  @Override
  public void onClick(View v) {

    if (v.getId() == R.id.register) {

      phone = editTextphone.getText().toString();
      Log.d(TAG, " phone " + phone);

      //   get the correct phone number format (international format)
      selected_country_code = ccp.getSelectedCountryCodeWithPlus();

      // log details

      firstName = editTextfirstname.getText().toString();
      Log.d(TAG, "firstName " + firstName);
      lastName = editTextlastname.getText().toString();
      Log.d(TAG, "lastName " + lastName);
      fullNumber = selected_country_code + phone;
      Log.d(TAG, "msisdn " + fullNumber);
      projectCode = editTextprojectcode.getText().toString();
      Log.d(TAG, "projectCode " + projectCode);
      appVersion = editTextappversion.getText().toString();
      Log.d(TAG, "appVersion " + appVersion);
      fcmKey = editTextfcmkey.getText().toString();
      Log.d(TAG, " device details " + mDeviceDetailes);

      Log.d(TAG, "field items " + firstName +" "+ lastName +" "+ fullNumber +" "+ projectCode +" "+ appVersion +" "+ fcmKey +" "+ mDeviceDetailes);


      if(!firstName.isEmpty() && !lastName.isEmpty() && !fullNumber.isEmpty()) {

        register(firstName, lastName, fullNumber, projectCode, appVersion, fcmKey, mDeviceDetailes);

      } else {

        Snackbar.make(v.getRootView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
      }

    }

  }

  private void register(String firstname, String lastname, String phone, String projectCode, String appVersion, String fcmKey, DeviceDetails deviceDetailss) {


    Log.d(TAG, "registerProcess: field items " + firstname +" "+ lastname +" "+ phone +" "+ projectCode +" "+ appVersion +" "+ fcmKey +" "+ deviceDetailss);



    //  device details json
    JsonObject device_details = new JsonObject();
    device_details.addProperty("device_model", deviceModel);
    device_details.addProperty("device_type", deviceType);
    device_details.addProperty("hardware", hardware);
    device_details.addProperty("manufacturer", manufacture);




    HashMap<String, Object> map = new HashMap<>();
    map.put("phone_number", phone);
    map.put("first_name", firstname);
    map.put("last_name", lastname);
    map.put("device_details", device_details);
    map.put("project_code", projectCode);
    map.put("app_version", appVersion);
    map.put("fcm_key",fcmKey);


    repo.customersRegister(new ProgressInterface<HashMap<Status, String>>() {
      @Override
      public void onResult(@NonNull HashMap<Status, String> result) {


        //(Status.SUCCESS, response.body().getAccessToken())
        if (result.containsKey(Status.SUCCESS)) {

          Log.d(TAG, "onResponse: " + result);
          Toast.makeText(MainActivity.this, "Successfully. Log in again to continue",
                  Toast.LENGTH_LONG).show();
        } else if (result.containsKey(Status.FAIL)) {

          Log.d(TAG, "onResponse" + result);
          Toast.makeText(MainActivity.this, "FAIL.kindly confirm  your details and try again ",
                  Toast.LENGTH_LONG).show();

        }


      }
    }, map);

  }


  }

