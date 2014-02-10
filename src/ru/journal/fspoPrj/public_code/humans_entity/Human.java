package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;

import java.io.Serializable;

public abstract class Human implements Serializable {

    public static final int DEFAULT_VALUE = 0;

    protected final static String EMPTY = "";
    protected final static String SPACE = " ";

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final int ID;

    protected Human(JSONObject element) {
        this.firstName = getStringValue(element, HumanKeys.FIRST_NAME);
        this.middleName = getStringValue(element, HumanKeys.MIDDLE_NAME);
        this.lastName = getStringValue(element, HumanKeys.LAST_NAME);
        this.ID = getIntValue(element, HumanKeys.HUMAN_ID);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getIntegerID() {
        return ID;
    }

    public String getStringID() {
        return String.valueOf(ID);
    }

    @Override
    public String toString() {
        // TODO
        StringBuilder builder = new StringBuilder(ID);
        builder.append(SPACE);
        builder.append(firstName);
        builder.append(SPACE);
        builder.append(middleName);
        builder.append(SPACE);
        builder.append(lastName);
        builder.append(SPACE);
        return builder.toString();
    }

    protected String getStringValue(JSONObject element, Keys key) {
        try {
            return element.getString(key.getKey());
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return EMPTY;
        }
    }

    protected int getIntValue(JSONObject element, Keys key) {
        try {
            return element.getInt(key.getKey());
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return DEFAULT_VALUE;
        }
    }

    public static interface Keys {
        String getKey();
    }

    public static enum HumanKeys implements Keys {

        STATUS("status"),
        LAST_NAME("lastname"),
        MIDDLE_NAME("middlename"),
        FIRST_NAME("firstname"),
        HUMAN_ID("id");

        private final String jsonKey;

        private HumanKeys(String jsonKey) {
            this.jsonKey = jsonKey;
        }

        @Override
        public String getKey() {
            return jsonKey;
        }

    }

}
