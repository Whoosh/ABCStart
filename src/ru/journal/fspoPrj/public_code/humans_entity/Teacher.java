package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

public class Teacher extends Human {

    protected Teacher(JSONObject element) {
        super(element);
    }

    private static enum TeacherKeys implements IKeyApi {
        ; // TODO
        private final String key;

        private TeacherKeys(String key) {
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
