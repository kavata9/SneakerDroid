
package com.kavata9.snekerdroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Probe {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("sampling_frequency")
    @Expose
    private int samplingFrequency;
    @SerializedName("configs")
    @Expose
    private Configs configs;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("data_collection_type")
    @Expose
    private String dataCollectionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSamplingFrequency() {
        return samplingFrequency;
    }

    public void setSamplingFrequency(int samplingFrequency) {
        this.samplingFrequency = samplingFrequency;
    }

    public Configs getConfigs() {
        return configs;
    }

    public void setConfigs(Configs configs) {
        this.configs = configs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataCollectionType() {
        return dataCollectionType;
    }

    public void setDataCollectionType(String dataCollectionType) {
        this.dataCollectionType = dataCollectionType;
    }

}
