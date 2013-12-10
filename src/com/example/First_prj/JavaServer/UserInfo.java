package com.example.First_prj.JavaServer;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {

    public static final String STATUS_TAG = "status";
    public static final String LAST_NAME_TAG = "lastname";
    public static final String MIDDLE_NAME_TAG = "middlename";
    public static final String FIRST_NAME_TAG = "firstname";
    public static final String EMAIL_TAG = "email";
    public static final String PHOTO_TAG = "photo";

    private String status;
    private String lastName;
    private String middleName;
    private String firstName;
    private String email;
    private String photoLink;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public void printUserInfo() {
        System.out.println("First name = " + firstName);
        System.out.println("Middle name = " + middleName);
        System.out.println("Last name = " + lastName);
        System.out.println("Email address = " + email);
        System.out.println("Photo link = " + photoLink);
        System.out.println("Status = " + status);
    }

    public void setAllParamsEmpty() {
        status = "";
        lastName = "";
        middleName = "";
        firstName = "";
        email = "";
        photoLink = "";
    }

    public void setDataFromJson(JSONObject jsonUserInfo) {
        try {
            firstName = jsonUserInfo.get(UserInfo.FIRST_NAME_TAG).toString();
        } catch (JSONException e) {
            firstName = "";
        }
        try {
            middleName = jsonUserInfo.get(UserInfo.MIDDLE_NAME_TAG).toString();
        } catch (JSONException e) {
            middleName = "";
        }
        try {
            lastName = jsonUserInfo.get(UserInfo.LAST_NAME_TAG).toString();
        } catch (JSONException e) {
            lastName = "";
        }
        try {
            email = jsonUserInfo.get(UserInfo.EMAIL_TAG).toString();
        } catch (JSONException e) {
            email = "";
        }
        try {
            status = jsonUserInfo.get(UserInfo.STATUS_TAG).toString();
        } catch (JSONException e) {
            status = "";
        }
        try {
            photoLink = jsonUserInfo.get(UserInfo.PHOTO_TAG).toString();
        } catch (JSONException e) {
            photoLink = "";
        }
    }
}
