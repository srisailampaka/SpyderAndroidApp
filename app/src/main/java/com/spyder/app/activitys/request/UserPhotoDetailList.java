package com.spyder.app.activitys.request;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserPhotoDetailList {

    @SerializedName("photoDetails")
    @Expose
    private List<PhotoDetail> photoDetails = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserPhotoDetailList() {
    }

    /**
     *
     * @param photoDetails
     */
    public UserPhotoDetailList(List<PhotoDetail> photoDetails) {
        super();
        this.photoDetails = photoDetails;
    }

    public List<PhotoDetail> getPhotoDetails() {
        return photoDetails;
    }

    public void setPhotoDetails(List<PhotoDetail> photoDetails) {
        this.photoDetails = photoDetails;
    }

}
