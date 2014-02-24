package ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons;

import android.content.Intent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class TeacherLessonsExecutor extends MainExecutor {

    private int resultCode;
    private String teacherLessonsQuery;

    public TeacherLessonsExecutor(String teacherLessonsQuery, int resultCode) {
        this.resultCode = resultCode;
        this.teacherLessonsQuery = teacherLessonsQuery;
        super.makeQuery(teacherLessonsQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(teacherLessonsQuery, makeTeacherLessons(results.remove(teacherLessonsQuery)));
        progressActivity.setResult(resultCode, result);
    }

    private TeacherLessons makeTeacherLessons(String jsonResponse) {
        JSONArray jsonLessons = null;
        try {
            jsonLessons = new JSONObject(jsonResponse).getJSONArray(TeacherLessons.TEACHER_LESSONS_KEY);
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
        return new TeacherLessons(jsonLessons);
    }


}
