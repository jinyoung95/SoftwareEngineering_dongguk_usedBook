package com.se_team8.dongguk_usedbook_market;

/**
 * Created by Eomji(2014112041김엄지) on 2016-11-25.
 */

/** 로그인할 때 사용자의 정보 저장할 클래스*/
public class Person {
    private String id;
    private String password;
    private String name;
    private String overlap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverlap() {
        return overlap;
    }

    public void setOverlap(String overlap) {
        this.overlap = overlap;
    }

    @Override
    public String toString() {
        return "ID=" + id + ", PW=" + password +", name=" + name+", overlap=" + overlap;
    }
}