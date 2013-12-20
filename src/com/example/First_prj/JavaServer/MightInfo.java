package com.example.First_prj.JavaServer;

import com.example.First_prj.ForAllCode.GlobalConstants;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class MightInfo {

    public static final byte ANONYMOUS_MIGHT_CODE = 0;
    public static final byte TEACHER_MIGHT_CODE = 1;
    public static final byte PARENT_MIGHT_CODE = 2;
    public static final byte STUDENT_MIGHT_CODE = 3;
    public static final byte ADMIN_MIGHT_CODE = 4;
    public static final char STUDENT_AKA_TEACHER_MIGHT_CODE = 31;

    private static final byte COUNT_UNIQUE_MIGHT_CODES = 5;
    private static final byte TEACHER_INDEX = 1;
    private static final byte PARENT_INDEX = 2;
    private static final byte STUDENT_INDEX = 3;
    private static final byte ADMIN_INDEX = 4;

    public static final String STATUS_TAG = "status";
    public static final String TEACHER_TAG = "teacher";
    public static final String STUDENT_TAG = "student";
    public static final String PARENT_TAG = "parent";
    public static final String ADMIN_TAG = "admin";

    private static String status;
    private static String teacher;
    private static String student;
    private static String parent;
    private static String admin;

    private static int currentMightCode;

    private static boolean mights[];

    private static final String JSON_TRUE_STRING = "true";

    public static void printMightInfo() {
        System.out.println("Teacher = " + teacher);
        System.out.println("Student = " + student);
        System.out.println("Parent = " + parent);
        System.out.println("Admin = " + admin);
        System.out.println("Status = " + status);
    }

    public static void setAllEmpty() {
        status = GlobalConstants.EMPTY_STRING;
        teacher = GlobalConstants.EMPTY_STRING;
        student = GlobalConstants.EMPTY_STRING;
        parent = GlobalConstants.EMPTY_STRING;
        admin = GlobalConstants.EMPTY_STRING;
    }

    public static void setDataFromJson(JSONObject jsonUserInfo) {
        mights = new boolean[COUNT_UNIQUE_MIGHT_CODES];
        try {
            teacher = jsonUserInfo.get(TEACHER_TAG).toString();
            if (teacher.equals(JSON_TRUE_STRING)) mights[TEACHER_INDEX] = true;
        } catch (JSONException e) {
            teacher = GlobalConstants.EMPTY_STRING;
        }
        try {
            student = jsonUserInfo.get(STUDENT_TAG).toString();
            if (student.equals(JSON_TRUE_STRING)) mights[STUDENT_INDEX] = true;
        } catch (JSONException e) {
            student = GlobalConstants.EMPTY_STRING;
        }
        try {
            parent = jsonUserInfo.get(PARENT_TAG).toString();
            if (parent.equals(JSON_TRUE_STRING)) mights[PARENT_INDEX] = true;
        } catch (JSONException e) {
            parent = GlobalConstants.EMPTY_STRING;
        }
        try {
            admin = jsonUserInfo.get(ADMIN_TAG).toString();
            if (admin.equals(JSON_TRUE_STRING)) mights[ADMIN_INDEX] = true;
        } catch (JSONException e) {
            admin = GlobalConstants.EMPTY_STRING;
        }
        try {
            status = jsonUserInfo.get(STATUS_TAG).toString();
        } catch (JSONException e) {
            status = GlobalConstants.EMPTY_STRING;
        }
        setCurrentMightCode();
    }

    public static int getCurrentMightCode() {
        return currentMightCode;
    }

    private static void setCurrentMightCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = mights.length - GlobalConstants.ONE; i > 0; i--)
            if (mights[i]) stringBuilder.append(i);
        try {
            currentMightCode = Integer.parseInt(stringBuilder.toString());
        } catch (NumberFormatException ex) {
            currentMightCode = ANONYMOUS_MIGHT_CODE;
        }
    }

}
