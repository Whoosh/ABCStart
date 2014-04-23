package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;

public abstract class Human implements Serializable {

    protected final static String SPACE = " ";
    protected final static String DOT = ".";
    protected static final String TABS = "\t\t";

    private static final String FIRST_NAME = "Имя : ";
    private static final String MIDDLE_NAME = "Отчество : ";
    private static final String LAST_NAME = "Фамилия : ";

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final int ID;

    protected Human(JSONObject element) {
        this.firstName = HumanKeys.FIRST_NAME.getStringValue(element);
        this.middleName = HumanKeys.MIDDLE_NAME.getStringValue(element);
        this.lastName = HumanKeys.LAST_NAME.getStringValue(element);
        this.ID = HumanKeys.HUMAN_ID.getIntValue(element);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || !(o == null || getClass() != o.getClass()) && ID == ((Human) o).ID;
    }

    @Override
    public int hashCode() {
        return ID;
    }

    @Override
    public String toString() {
        return "Human{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ID=" + ID +
                '}';
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

    public String getPointedFirstName() {
        return FIRST_NAME + firstName;
    }

    public String getPointedMiddleName() {
        return MIDDLE_NAME + middleName;
    }

    public String getPointedLastName() {
        return LAST_NAME + lastName;
    }

    public int getIntegerID() {
        return ID;
    }

    public String getStringID() {
        return String.valueOf(getIntegerID());
    }

    public String getShortName() {
        StringBuilder builder = new StringBuilder(TABS);
        builder.append(lastName);
        builder.append(SPACE);
        builder.append(firstName.charAt(0));
        builder.append(DOT);
        builder.append(middleName.charAt(0));
        builder.append(DOT);
        return builder.toString();
    }

    public static enum HumanKeys implements IKeyApi {

        STATUS("status"),
        LAST_NAME("lastname"),
        MIDDLE_NAME("middlename"),
        FIRST_NAME("firstname"),
        HUMAN_ID("id");

        private final String key;

        private HumanKeys(String key) {
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
