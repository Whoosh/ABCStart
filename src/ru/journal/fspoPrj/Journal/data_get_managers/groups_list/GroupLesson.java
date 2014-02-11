package ru.journal.fspoPrj.journal.data_get_managers.groups_list;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;

import java.io.Serializable;

public class GroupLesson implements Serializable {

    public static final String EMPTY = "";
    public static final int NO_LESSON = 0;
    public static final String ENTER = "\n";
    public static final String TAB = "  \t";
    private final int ID;
    private final String name;
    private final String shortName;
    private final String semester;

    public GroupLesson(JSONObject lesson) {
        this.ID = parseLessonID(lesson);
        this.name = parseLessonName(lesson);
        this.shortName = parseLessonShortName(lesson);
        this.semester = parseLessonSemester(lesson);
    }

    private String parseLessonSemester(JSONObject lesson) {
        return stringOrEmpty(lesson, LessonKey.SEMESTER);
    }

    private String parseLessonShortName(JSONObject lesson) {
        return stringOrEmpty(lesson, LessonKey.SHORT_NAME);
    }

    private String parseLessonName(JSONObject lesson) {
        return stringOrEmpty(lesson, LessonKey.NAME);
    }

    private String stringOrEmpty(JSONObject lesson, LessonKey key) {
        try {
            return lesson.getString(key.getKey());
        } catch (JSONException e) {
            return EMPTY;
        }
    }

    private int parseLessonID(JSONObject lesson) {
        try {
            return lesson.getInt(LessonKey.LESSON_ID.getKey());
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return NO_LESSON;
        }
    }

    public int getID() {
        return ID;
    }

    public int getStringID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSemester() {
        return semester;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(ENTER);
        builder.append(getID());
        builder.append(TAB);
        builder.append(getName());
        builder.append(TAB);
        builder.append(getShortName());
        builder.append(TAB);
        builder.append(getSemester());
        builder.append(TAB);
        return builder.toString();
    }

    private static enum LessonKey {

        LESSON_ID("id"),
        NAME("name"),
        SHORT_NAME("shortname"),
        SEMESTER("semester");

        private final String key;

        private LessonKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }
}
