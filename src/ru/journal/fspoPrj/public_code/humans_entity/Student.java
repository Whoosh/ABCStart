package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONObject;

public class Student extends Human {

    private final int stream;
    private final int group;

    public Student(JSONObject element) {
        super(element);
        this.group = getIntValue(element, StudKeys.GROUP);
        this.stream = getIntValue(element, StudKeys.STREAM);
    }

    public int getIntegerStream() {
        return stream;
    }

    public int getIntegerGroup() {
        return group;
    }

    public String getStringStream() {
        return String.valueOf(stream);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append(stream);
        builder.append(SPACE);
        builder.append(group);
        builder.append(SPACE);
        return builder.toString();
    }

    public String getStringGroup() {
        return String.valueOf(group);
    }

    private static enum StudKeys implements Keys {
        STREAM("stream"),
        GROUP("offgr");

        private final String key;

        private StudKeys(String key) {
            this.key = key;
        }

        @Override
        public String getKey() {
            return key;
        }
    }
}
