package ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.util.Arrays;

public class TeacherLessons implements Serializable {

    public final static String TEACHER_LESSONS_KEY = "teacherLessons";

    private TeacherLesson[] lessons;

    public TeacherLessons(String jsonResponse) {
        try {
            JSONArray lessons = new JSONObject(jsonResponse).getJSONArray(TEACHER_LESSONS_KEY);
            this.lessons = new TeacherLesson[lessons.length()];
            for (int i = 0; i < this.lessons.length; i++) {
                System.out.println(lessons.getString(i));
               this.lessons[i] = new TeacherLesson(lessons.getJSONObject(i));
            }
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public TeacherLesson[] getLessons() {
        return lessons;
    }
}
