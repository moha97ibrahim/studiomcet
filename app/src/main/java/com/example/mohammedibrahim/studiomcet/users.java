package com.example.mohammedibrahim.studiomcet;



/**
 * Created by mohammedibrahim on 23/03/18.
 */

public class users {
    private String email1;
    private String name1;



    private String mobilenum1;


    public users(String s, String uid) {
    }

    public users(String Email, String Name  , String MobileNum) {

        email1 = Email;
        name1 =Name;

        mobilenum1 = MobileNum;

    }


    public String getEmail1() {
        return email1;
    }
    public String getName1() {
        return name1;
    }
    public String getMobilenum1() {
        return mobilenum1;
    }
}
