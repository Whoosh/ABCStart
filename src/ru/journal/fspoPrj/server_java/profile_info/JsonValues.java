package ru.journal.fspoPrj.server_java.profile_info;

public enum JsonValues {

    STATUS(""), LAST_NAME(""), MIDDLE_NAME(""), FIRST_NAME(""),
    EMAIL(""), PHOTO_LINK(""), PHONE("");

    private String value;

    private JsonValues(String value) {
        this.value = value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}