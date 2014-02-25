package ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.util.Arrays;

public class TeacherLessons implements Serializable {

    public final static String TEACHER_LESSONS_KEY = "lessons";

    private TeacherLesson[] lessons;

    public TeacherLessons(String jsonResponse) {
        try {
            JSONArray lessons = new JSONObject(jsonResponse).getJSONArray(TEACHER_LESSONS_KEY);
            this.lessons = new TeacherLesson[lessons.length()];
            for (int i = 0; i < this.lessons.length; i++) {
                this.lessons[i] = new TeacherLesson(lessons.getJSONObject(i));
            }
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public TeacherLesson[] getLessons() {
        return lessons;
    }

    @Override
    public String toString() {
        return "TeacherLessons{" + "lessons=" + Arrays.toString(lessons) + '}';
    }

    public static class TeacherLesson implements Serializable {

        private final int lessonID;
        private final int semester;

        public TeacherLesson(JSONObject element) {
            this.lessonID = TeacherLessonKey.LESSON_ID.getIntValue(element);
            this.semester = TeacherLessonKey.SEMESTER.getIntValue(element);
        }

        public int getLessonID() {
            return lessonID;
        }

        public int getSemester() {
            return semester;
        }

        @Override
        public String toString() {
            return "TeacherLesson{" + "lessonID=" + lessonID + ", semester=" + semester + '}';
        }
    }

    public static enum TeacherLessonKey implements IKeyApi {

        LESSON_ID("les_id"),
        SEMESTER("semester");

        private final String key;

        private TeacherLessonKey(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public int getIntValue(JSONObject element) {
            return parser.parseInt(key, element);
        }

        @Override
        public String getStringValue(JSONObject element) {
            return parser.parseString(key, element);
        }
    }

}
