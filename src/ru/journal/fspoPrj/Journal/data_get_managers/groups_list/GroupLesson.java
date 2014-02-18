package ru.journal.fspoPrj.journal.data_get_managers.groups_list;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;

public class GroupLesson implements Serializable {

    private final int lessonID;
    private final int groupID;
    private final String name;
    private final String shortName;
    private final String semester;

    public GroupLesson(JSONObject element) {
        this.lessonID = LessonKey.LESSON_ID.getIntValue(element);
        this.groupID = LessonKey.GROUP_ID.getIntValue(element);
        this.name = LessonKey.NAME.getStringValue(element);
        this.shortName = LessonKey.SHORT_NAME.getStringValue(element);
        this.semester = LessonKey.SEMESTER.getStringValue(element);
    }

    public String getStringLessonID() {
        return String.valueOf(getLessonID());
    }

    public String getStringGroupID() {
        return String.valueOf(getGroupID());
    }


    public int getLessonID() {
        return lessonID;
    }

    public int getGroupID() {
        return groupID;
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

    private static enum LessonKey implements IKeyApi {

        LESSON_ID("lesson_id"),
        GROUP_ID("group_id"),
        NAME("name"),
        SHORT_NAME("shortname"),
        SEMESTER("semester");

        private final String key;

        private LessonKey(String key) {
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
