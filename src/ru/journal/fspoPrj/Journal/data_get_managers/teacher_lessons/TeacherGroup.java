package ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

public class TeacherGroup {

    private final int groupID;
    private final String groupName;

    public TeacherGroup(JSONObject groupElement) {
        groupID = GroupKeys.GROUP_ID.getIntValue(groupElement);
        groupName = GroupKeys.NAME.getStringValue(groupElement);
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
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
