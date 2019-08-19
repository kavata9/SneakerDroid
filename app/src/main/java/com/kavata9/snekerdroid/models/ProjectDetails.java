
package com.kavata9.snekerdroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDetails {

    @SerializedName("has_analytics")
    @Expose
    private boolean hasAnalytics;
    @SerializedName("probe_upload_frequency")
    @Expose
    private int probeUploadFrequency;
    @SerializedName("has_model_challenges")
    @Expose
    private boolean hasModelChallenges;
    @SerializedName("is_data_encrypted")
    @Expose
    private boolean isDataEncrypted;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    public boolean isHasAnalytics() {
        return hasAnalytics;
    }

    public void setHasAnalytics(boolean hasAnalytics) {
        this.hasAnalytics = hasAnalytics;
    }

    public int getProbeUploadFrequency() {
        return probeUploadFrequency;
    }

    public void setProbeUploadFrequency(int probeUploadFrequency) {
        this.probeUploadFrequency = probeUploadFrequency;
    }

    public boolean isHasModelChallenges() {
        return hasModelChallenges;
    }

    public void setHasModelChallenges(boolean hasModelChallenges) {
        this.hasModelChallenges = hasModelChallenges;
    }

    public boolean isIsDataEncrypted() {
        return isDataEncrypted;
    }

    public void setIsDataEncrypted(boolean isDataEncrypted) {
        this.isDataEncrypted = isDataEncrypted;
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

}
