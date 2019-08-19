
package com.kavata9.snekerdroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseBody {

    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("probes")
    @Expose
    private List<Probe> probes = null;
    @SerializedName("user-details")
    @Expose
    private UserDetails userDetails;
    @SerializedName("project-details")
    @Expose
    private ProjectDetails projectDetails;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<Probe> getProbes() {
        return probes;
    }

    public void setProbes(List<Probe> probes) {
        this.probes = probes;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails participantDetails) {
        this.userDetails = participantDetails;
    }

    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

}
