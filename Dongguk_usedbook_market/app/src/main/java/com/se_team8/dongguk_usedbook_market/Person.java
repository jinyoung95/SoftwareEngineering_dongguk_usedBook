package com.se_team8.dongguk_usedbook_market;

/**
 * Created by Eomji on 2016-11-25.
 */
public class Person {
    private String id;
    private String password;

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

    @Override
    public String toString() {
        return "ID=" + id + ", PW=" + password;
    }
}