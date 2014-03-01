package ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;

public class TeacherGroup implements Serializable {

    private final int groupID;
    private final int lessonID;
    private final String groupName;
    private final String groupLesson;

    public TeacherGroup(JSONObject groupElement, int lessonID,String groupLesson) {
        groupID = GroupKeys.GROUP_ID.getIntValue(groupElement);
        groupName = GroupKeys.NAME.getStringValue(groupElement);
        this.lessonID = lessonID;
        this.groupLesson = groupLesson;
    }

    public String getStringLessonID(){
        return String.valueOf(lessonID);
    }

    public String getStringGroupID(){
        return String.valueOf(groupID);
    }

    public String getGroupLesson() {
        return groupLesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeacherGroup group = (TeacherGroup) o;
        return groupID == group.groupID && lessonID == group.lessonID && groupName.equals(group.groupName);
    }

    @Override
    public int hashCode() {
        int result = groupID;
        result = 31 * result + lessonID;
        result = 31 * result + groupName.hashCode();
        return result;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getLessonID() {
        return lessonID;
    }

    @Override
    public String toString() {
        return "TeacherGroup{" +
                "groupID=" + groupID +
                ", groupName='" + groupName + '\'' +
                '}';
    }

    private static enum GroupKeys implements IKeyApi {
        NAME("name"),
        GROUP_ID("group_id");

        private final String key;

        private GroupKeys(String key) {
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
