package com.slashapps.radary.WebService.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 25/12/2018.
 */

public class Registermodel {
    @SerializedName("apiVersion")
    @Expose
    private String apiVersion;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Exception")
    @Expose
    private String exception;
    @SerializedName("data")
    @Expose
    private Data_register data;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Data_register getData() {
        return data;
    }

    public void setData(Data_register data) {
        this.data = data;
    }
}
