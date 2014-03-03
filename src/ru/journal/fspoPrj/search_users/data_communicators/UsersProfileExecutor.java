package ru.journal.fspoPrj.search_users.data_communicators;

import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class UsersProfileExecutor extends MainExecutor {

    private static final int SOME_CAPACITY = 1000;
    private static final String USER_LIST_KEY = "userslist";
    private static final String STUDENTS_KEY = "students";
    private static final String TEACHERS_KEY = "teachers";

    private ArrayList<ProfileInfo> profilesInfo;
    private String profilesQuery;
    private int resultCode;

    public UsersProfileExecutor(String usersProfileQuery, int resultCode) {
        this.profilesQuery = usersProfileQuery;
        this.resultCode = resultCode;
        profilesInfo = new ArrayList<>(SOME_CAPACITY);
        super.makeQuery(profilesQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        prepareInfo(makeJsonObject(results.get(profilesQuery), USER_LIST_KEY));
        handlingInfo();
        commitResult();
    }

    private void prepareInfo(JSONObject jsonObject) {
        try {
            addStudentsOnList(jsonObject.getJSONObject(STUDENTS_KEY));
            addTeachersOnList(jsonObject.getJSONArray(TEACHERS_KEY));
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    private void addTeachersOnList(JSONArray teachers) throws JSONException {
        for (int i = 0; i < teachers.length(); i++) {
            ProfileInfo profileInfo = new ProfileInfo(teachers.getJSONObject(i), ProfileInfo.Status.TEACHER);
            if (profilesInfo.contains(profileInfo)) {
                profilesInfo.get(profilesInfo.indexOf(profileInfo)).setStatus(ProfileInfo.Status.TEACHER_AKA_STUDENT);
            } else {
                profilesInfo.add(profileInfo);
            }
        }
    }

    private void addStudentsOnList(JSONObject students) throws JSONException {
        JSONArray groups = students.names();
        for (int i = 0; i < groups.length(); i++) {
            addStudents(students.getJSONArray(groups.getString(i)), groups.getInt(i));
        }
    }

    private void addStudents(JSONArray students, int groupNumber) throws JSONException {
        for (int i = 0; i < students.length(); i++) {
            profilesInfo.add(new ProfileInfo(students.getJSONObject(i), groupNumber));
        }
    }

    private JSONObject makeJsonObject(String jsonProfile, String key) {
        try {
            return new JSONObject(jsonProfile).getJSONObject(key);
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return null;
        }
    }

    private void handlingInfo() {
        for (Iterator<ProfileInfo> iterator = profilesInfo.iterator(); iterator.hasNext(); ) {
            ProfileInfo profileInfo = iterator.next();
            if (profileInfo.getFirstName().isEmpty()) {
                iterator.remove();
            }
        }
        profilesInfo.trimToSize();
    }

    private void commitResult() {
        Intent data = new Intent();
        data.putExtra(profilesQuery, profilesInfo);
        progressActivity.setResult(resultCode, data);
    }
}
