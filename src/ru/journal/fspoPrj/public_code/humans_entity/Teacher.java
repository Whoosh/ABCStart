package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONObject;

public class Teacher extends Human {

    protected Teacher(JSONObject element) {
        super(element);
    }

    private static enum TeacherKeys implements Keys {
        ; // TODO
        private final String key;

        private TeacherKeys(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }
}
