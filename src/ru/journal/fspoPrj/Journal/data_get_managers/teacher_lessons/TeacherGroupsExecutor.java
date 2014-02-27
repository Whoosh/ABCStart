package ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons;

import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupsList;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class TeacherGroupsExecutor extends MainExecutor {

    private int resultCode;
    private String teacherLessonsQuery;
    private String groupsQuery;
    private String lessonsQuery;

    private TeacherLessons teacherLessons;
    private GroupsList groupsList;

    public TeacherGroupsExecutor(String teacherLessonsQuery, String groupsQuery, String lessonsQuery, int resultCode) {
        this.resultCode = resultCode;
        this.teacherLessonsQuery = teacherLessonsQuery;
        this.groupsQuery = groupsQuery;
        this.lessonsQuery = lessonsQuery;

        super.makeQuery(teacherLessonsQuery);
        super.makeQuery(groupsQuery);
        super.makeQuery(lessonsQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        handleResult(results);
        // Intent result = new Intent();
        // result.putExtra(groupsQuery, groupsList);
        //progressActivity.setResult(resultCode, result);
    }

    private void handleResult(HashMap<String, String> result) {
        //groupsList = new GroupsList(result.remove(groupsQuery), result.remove(lessonsQuery));
        teacherLessons = new TeacherLessons(result.remove(teacherLessonsQuery));
        prepareGroupList();
    }

    private void prepareGroupList() {
        //  groupsList.removeNonTeacherLessons(teacherLessons);
    }

}
