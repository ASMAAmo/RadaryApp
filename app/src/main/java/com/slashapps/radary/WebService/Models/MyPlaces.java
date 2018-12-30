package com.slashapps.radary.WebService.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 29/12/2018.
 */

public class MyPlaces {
    @SerializedName("apiVersion")
    @Expose
    private String apiVersion;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Exception")
    @Expose
    private String exception;
    @SerializedName("total_date")
    @Expose
    private Integer totalDate;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public Integer getTotalDate() {
        return totalDate;
    }

    public void setTotalDate(Integer totalDate) {
        this.totalDate = totalDate;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
