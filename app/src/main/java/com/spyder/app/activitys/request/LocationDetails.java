package com.spyder.app.activitys.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationDetails {


    @SerializedName("locationDetails")
    @Expose
    private List<LocationDetail> locationDetails = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public LocationDetails() {
    }

    /**
     *
     * @param locationDetails
     */
    public LocationDetails(List<LocationDetail> locationDetails) {
        super();
        this.locationDetails = locationDetails;
    }

    public List<LocationDetail> getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(List<LocationDetail> locationDetails) {
        this.locationDetails = locationDetails;
    }

}