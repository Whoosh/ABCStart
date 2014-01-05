package ru.journal.fspoPrj.server_java.profile_info;

public enum JsonKeys {

    STATUS("status"),
    LAST_NAME("lastname"),
    MIDDLE_NAME("middlename"),
    FIRST_NAME("firstname"),
    EMAIL("email"),
    PHOTO("photo"),
    PHONE("phone");

    private final String jsonKey;

    private JsonKeys(String jsonKey) {
        this.jsonKey = jsonKey;
    }

    public String getJsonKey() {
        return jsonKey;
    }

    public int getChainKey() {
        return ordinal();
    }
}
