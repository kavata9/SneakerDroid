
package com.kavata9.snekerdroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceDetails {

    @SerializedName("device_model")
    @Expose
    private String deviceModel;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("hardware")
    @Expose
    private String hardware;
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    @SerializedName("device_id")
    @Expose
    private String device_id;


    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

}
