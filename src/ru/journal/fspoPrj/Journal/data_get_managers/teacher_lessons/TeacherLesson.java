package ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;

public class TeacherLesson implements Serializable {

    private final String name;
    private final int lessonID;
    private final int semester;
    private final String shortName;

    private TeacherGroup[] groups;

    public TeacherLesson(JSONObject element) {
        name = TeacherLessonKey.NAME.getStringValue(element);
        lessonID = TeacherLessonKey.LESSON_ID.getIntValue(element);
        semester = TeacherLessonKey.SEMESTER.getIntValue(element);
        shortName = TeacherLessonKey.SHORT_NAME.getStringValue(element);
        makeTeacherGroups(element);
    }

    private void makeTeacherGroups(JSONObject element) {
        try {
            JSONArray groups = element.getJSONArray(TeacherLessonKey.LESSONS_GROUP.getKey());
            this.groups = new TeacherGroup[groups.length()];
            for (int i = 0; i < groups.length(); i++) {
                this.groups[i] = new TeacherGroup(groups.getJSONObject(i), lessonID, name);
            }
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public int getLessonID() {
        return lessonID;
    }

    public int getSemester() {
        return semester;
    }

    public TeacherGroup[] getGroups() {
        return groups;
    }

    public static enum TeacherLessonKey implements IKeyApi {

        LESSON_ID("lesson_id"),
        SEMESTER("semester"),
        NAME("name"),
        LESSONS_GROUP("lessonGroups"),
        SHORT_NAME("shortname");

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