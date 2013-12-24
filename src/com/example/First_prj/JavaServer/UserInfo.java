package com.example.First_prj.JavaServer;

import com.example.First_prj.ForAllCode.Configs.GlobalConfig;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {
//
    public static final String STATUS_TAG = "status";
    public static final String LAST_NAME_TAG = "lastname";
    public static final String MIDDLE_NAME_TAG = "middlename";
    public static final String FIRST_NAME_TAG = "firstname";
    public static final String EMAIL_TAG = "email";
    public static final String PHOTO_TAG = "photo";
    public static final String PHONE_TAG = "phone";

    private String status;
    private String lastName;
    private String middleName;
    private String firstName;
    private String email;
    private String photoLink;
    private String phone;

    public void printUserInfo() {
        System.out.println("First name = " + firstName);
        System.out.println("Middle name = " + middleName);
        System.out.println("Last name = " + lastName);
        System.out.println("Email address = " + email);
        System.out.println("Photo link = " + photoLink);
        System.out.println("Status = " + status);
        System.out.println("Phone = " + phone);

    }

    public void setAllParamsEmpty() {
        status = GlobalConfig.EMPTY_STRING;
        lastName = GlobalConfig.EMPTY_STRING;
        middleName = GlobalConfig.EMPTY_STRING;
        firstName = GlobalConfig.EMPTY_STRING;
        email = GlobalConfig.EMPTY_STRING;
        photoLink = GlobalConfig.EMPTY_STRING;
        phone = GlobalConfig.EMPTY_STRING;
    }

    public void setDataFromJson(JSONObject jsonUserInfo) {
        try {
            firstName = jsonUserInfo.get(UserInfo.FIRST_NAME_TAG).toString();
        } catch (JSONException e) {
            firstName = GlobalConfig.EMPTY_STRING;
        }
        try {
            middleName = jsonUserInfo.get(UserInfo.MIDDLE_NAME_TAG).toString();
        } catch (JSONException e) {
            middleName = GlobalConfig.EMPTY_STRING;
        }
        try {
            lastName = jsonUserInfo.get(UserInfo.LAST_NAME_TAG).toString();
        } catch (JSONException e) {
            lastName = GlobalConfig.EMPTY_STRING;
        }
        try {
            email = jsonUserInfo.get(UserInfo.EMAIL_TAG).toString();
        } catch (JSONException e) {
            email = GlobalConfig.EMPTY_STRING;
        }
        try {
            status = jsonUserInfo.get(UserInfo.STATUS_TAG).toString();
        } catch (JSONException e) {
            status = GlobalConfig.EMPTY_STRING;
        }
        try {
            photoLink = jsonUserInfo.get(UserInfo.PHOTO_TAG).toString();
        } catch (JSONException e) {
            photoLink = GlobalConfig.EMPTY_STRING;
        }
        try {
            phone = jsonUserInfo.get(UserInfo.PHONE_TAG).toString();
        } catch (JSONException e) {
            photoLink = GlobalConfig.EMPTY_STRING;
        }
    }
}