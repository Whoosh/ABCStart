package ru.journal.fspoPrj.server_java.might_info;

public enum JsonValues {

    RESERVED_TO_ANONYMOUS("", true),
    TEACHER("teacher", false),
    STUDENT("student", false),
    PARENT("parent", false),
    ADMIN("admin", false);

    private final String jsonKey;
    private boolean statusInQuery;

    private JsonValues(String jsonKey, boolean statusInQuery) {
        this.jsonKey = jsonKey;
        this.statusInQuery = statusInQuery;
    }

    public boolean getStatus() {
        return statusInQuery;
    }

    public void setStatus(boolean statusInQuery) {
        this.statusInQuery = statusInQuery;
    }

    public String getJsonKey() {
        return jsonKey;
    }

    public static int getStartNumber() {
        return TEACHER.ordinal();
    }
}
