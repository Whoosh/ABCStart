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
    private TeacherLessons teacherLessons;

    public TeacherGroupsExecutor(String teacherLessonsQuery, int resultCode) {
        this.resultCode = resultCode;
        this.teacherLessonsQuery = teacherLessonsQuery;
        super.makeQuery(teacherLessonsQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        handleResult(results);
        Intent result = new Intent();
        result.putExtra(teacherLessonsQuery,teacherLessons);
        progressActivity.setResult(resultCode, result);
    }

    private void handleResult(HashMap<String, String> result) {
        teacherLessons = new TeacherLessons(result.remove(teacherLessonsQuery));
    }
}
