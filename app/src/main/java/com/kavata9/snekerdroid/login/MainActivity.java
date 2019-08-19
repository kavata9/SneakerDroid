package com.kavata9.snekerdroid.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

  //    userreg fields
  String firstname;
  String lastname;
  String phone;
  String projectCode;
  String appVersion;
  String fcmKey;

  //    create new instance of the DeviceDetails model class
  DeviceDetails mDeviceDetailss = new DeviceDetails();

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
    mDeviceDetailss.setDeviceModel(deviceModel);
    mDeviceDetailss.setDeviceType(deviceType);
    mDeviceDetailss.setHardware(hardware);
    mDeviceDetailss.setManufacturer(manufacture);

    getDeviceDetails();
  }

  private void getDeviceDetails() {
    Log.d(TAG, "getDeviceDetails: " + mDeviceDetailss.getDeviceModel());

    //   editText fcm key
    editTextfcmkey.setText(APIService.FCM_KEY);

    TextviewDevicemodel.setText(mDeviceDetailss.getDeviceModel());
    TextviewDeviceType.setText(mDeviceDetailss.getDeviceType());
    TextviewDeviceHardware.setText(mDeviceDetailss.getHardware());
    TextviewDevicemanufacturer.setText(mDeviceDetailss.getManufacturer());
  }

  @Override
  public void onClick(View v) {
    Log.d(TAG, "onClick: ");

    if (v.getId() == R.id.register) {

      phone = editTextphone.getText().toString();
      Log.d(TAG, "onClick: phone " + phone);

      //   get the correct phone number format (international format)
      selected_country_code = ccp.getSelectedCountryCodeWithPlus();

      //  form fields

      firstname = editTextfirstname.getText().toString();
      Log.d(TAG, "onClick: firstname " + firstname);
      lastname = editTextlastname.getText().toString();
      Log.d(TAG, "onClick: lastname " + lastname);
      fullNumber = selected_country_code + phone;
      Log.d(TAG, "Test user mobile " + fullNumber);
      projectCode = editTextprojectcode.getText().toString();
      Log.d(TAG, "onClick: projectCode " + projectCode);
      appVersion = editTextappversion.getText().toString();
      Log.d(TAG, "onClick: appVersion " + appVersion);
      fcmKey = editTextfcmkey.getText().toString();
      // and device details that are initialised above
      Log.d(TAG, "onClick: device details " + mDeviceDetailss);

      Log.d(TAG, "onClick: field items " + firstname +" "+ lastname +" "+ fullNumber +" "+ projectCode +" "+ appVersion +" "+ fcmKey +" "+ mDeviceDetailss);


      if(!firstname.isEmpty() && !lastname.isEmpty() && !fullNumber.isEmpty()) {

        register(firstname, lastname, fullNumber, projectCode, appVersion, fcmKey, mDeviceDetailss);

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
          View parentLayout = MainActivity.this.findViewById(android.R.id.content);
          Snackbar.make(parentLayout, "SUCCESS !", Snackbar.LENGTH_LONG).show();
        } else if (result.containsKey(Status.FAIL)) {

          Log.d(TAG, "onResponse" + result);

        }


      }
    }, map);

  }


  }

