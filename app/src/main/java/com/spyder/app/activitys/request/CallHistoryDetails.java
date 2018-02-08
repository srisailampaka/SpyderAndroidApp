package com.spyder.app.activitys.request;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CallHistoryDetails {

    @SerializedName("callHistoryDetails")
    @Expose
    private List<CallHistoryDetailsPojo> callHistoryDetailsPojo = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public CallHistoryDetails() {
    }

    /**
     *
     * @param callHistoryDetailsPojo
     */
    public CallHistoryDetails(List<CallHistoryDetailsPojo> callHistoryDetailsPojo) {
        super();
        this.callHistoryDetailsPojo = callHistoryDetailsPojo;
    }

    public List<CallHistoryDetailsPojo> getCallHistoryDetails() {
        return callHistoryDetailsPojo;
    }

    public void setCallHistoryDetails(List<CallHistoryDetailsPojo> callHistoryDetailsPojo) {
        this.callHistoryDetailsPojo = callHistoryDetailsPojo;
    }

}