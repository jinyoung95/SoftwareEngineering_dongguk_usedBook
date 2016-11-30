package com.se_team8.dongguk_usedbook_market;

/**
 * Created by Juhyeon on 2016-11-26.
 */

public class RequestListItem {
    private String[] data = new String[2];
    private String requestStudentName;
    private String requestStudentID;

    /** 변수들에 대한 Getter and Setter 메소드 */

    public String[] getData() {
        return data;
    }

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
    }
}
