package ru.journal.fspoPrj.server_java;

import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
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

    public static final String PREFIX_FIRST_NAME = "\tИмя : ";
    public static final String PREFIX_MIDDLE_NAME = "\tОтчество : ";
    public static final String PREFIX_LAST_NAME = "\tФамилия : ";
    public static final String PREFIX_PHONE = "\t\tТелефон : ";
    public static final String PREFIX_MAIL = "\t\tПочта : ";

    public String status;
    public String lastName;
    public String middleName;
    public String firstName;
    public String email;
    public String photoLink;
    public String phone;

    public void printUserInfo() {
        System.out.println(PREFIX_FIRST_NAME + firstName);
        System.out.println(PREFIX_MIDDLE_NAME + middleName);
        System.out.println(PREFIX_LAST_NAME + lastName);
        System.out.println(PREFIX_MAIL + email);
        System.out.println("Photo link = " + photoLink);
        System.out.println("Status = " + status);
        System.out.println(PREFIX_PHONE + phone);

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
        setAllParamsEmpty();
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
        checkThisNull();
    }

    // сервер может возвращать этот калл
    private void checkThisNull() {
        if (firstName.equals("null")) firstName = GlobalConfig.EMPTY_STRING;
        if (middleName.equals("null")) middleName = GlobalConfig.EMPTY_STRING;
        if (lastName.equals("null")) lastName = GlobalConfig.EMPTY_STRING;
        if (email.equals("null")) email = GlobalConfig.EMPTY_STRING;
        if (photoLink.equals("null")) photoLink = GlobalConfig.EMPTY_STRING;
        if (phone.equals("null")) phone = GlobalConfig.EMPTY_STRING;
    }
}