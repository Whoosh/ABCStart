package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

public class Student extends Human {

    private final int stream;
    private final int group;

    public Student(JSONObject element) {
        super(element);
        this.group = StudKeys.GROUP.getIntValue(element);
        this.stream = StudKeys.STREAM.getIntValue(element);
    }

    public int getIntegerStream() {
        return stream;
    }

    public int getIntegerGroup() {
        return group;
    }

    public String getStringGroup() {
        return String.valueOf(getIntegerGroup());
    }

    public String getStringStream() {
        return String.valueOf(getIntegerStream());
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


    private static enum StudKeys implements IKeyApi {
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
