package com.se_team8.dongguk_usedbook_market;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class RequestListItem {
    private String[] data = new String[2];
    private String requestID;
    private String requestName;

    public RequestListItem(String requestID, String requestName){
        this.requestID = requestID;
        this.requestName = requestName;
        this.data[0] = requestID;
        this.data[1] = requestName;
    }

    public String[] getData() {
        return data;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }
}
