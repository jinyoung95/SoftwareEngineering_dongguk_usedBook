package com.se_team8.dongguk_usedbook_market;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class RequestListItem {
    private String[] data = new String[2];
<<<<<<< HEAD
    private String requestID;
    private String requestName;

    public RequestListItem(String requestID, String requestName){
        this.requestID = requestID;
        this.requestName = requestName;
        this.data[0] = requestID;
        this.data[1] = requestName;
    }
=======
    private String requestStudentName;
    private String requestStudentID;

    /** 변수들에 대한 Getter and Setter 메소드 */
>>>>>>> Eomji

    public String[] getData() {
        return data;
    }

<<<<<<< HEAD
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
=======
    public void setData(String[] data) {
        this.data = data;
    }

    public String getRequestStudentName() {
        return requestStudentName;
    }

    public void setRequestStudentName(String requestStudentName) {
        data[1] = requestStudentName;
        this.requestStudentName = requestStudentName;
    }

    public String getRequestStudentID() {
        return requestStudentID;
    }

    public void setRequestStudentID(String requestStudentID) {
        data[0] = requestStudentID;
        this.requestStudentID = requestStudentID;
>>>>>>> Eomji
    }
}
