package com.se_team8.dongguk_usedbook_market;

import java.util.ArrayList;

/**
 * Created by Juhyeon on 2016-11-20.
 */

public class ListName {
    //Properties of Position
    public String listName;
    public String image;
    public ArrayList<String> users = new ArrayList<String>();

    public ListName(String listName){
        this.listName = listName;
    }

    public String toString () {
        return listName;
    }
}
