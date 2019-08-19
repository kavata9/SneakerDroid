
package com.kavata9.snekerdroid.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configs {

    @SerializedName("regex")
    @Expose
    private List<Object> regex = null;

    public List<Object> getRegex() {
        return regex;
    }

    public void setRegex(List<Object> regex) {
        this.regex = regex;
    }

}
