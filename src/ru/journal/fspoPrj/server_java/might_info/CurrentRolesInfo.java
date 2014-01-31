package ru.journal.fspoPrj.server_java.might_info;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.server_java.might_info.mights_function_kits.ToolKitsManager;
import ru.journal.fspoPrj.public_code.Logger;


public enum CurrentRolesInfo {

    RESERVED_TO_ANONYMOUS("", true),
    TEACHER("teacher", false),
    STUDENT("student", false),
    PARENT("parent", false),
    ADMIN("admin", false);

    private final String jsonKey;
    private boolean statusInQuery;

    private CurrentRolesInfo(String jsonKey, boolean statusInQuery) {
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

    public static void setDataFromJson(JSONObject rolesInfo) {
        for (CurrentRolesInfo value : CurrentRolesInfo.values()) {
            try {
                if (value.getJsonKey().isEmpty()) continue;
                value.setStatus(rolesInfo.getBoolean(value.getJsonKey()));
            } catch (JSONException e) {
                value.setStatus(false);
                Logger.printError(e, CurrentRolesInfo.class);
            }
        }
    }

    public static int getCurrentMightCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CurrentRolesInfo values : CurrentRolesInfo.values()) {
            if (values.getStatus()) {
                stringBuilder.append(values.ordinal());
            }
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    public static ToolKitsManager getToolsKit() {
        for (MightsCodes values : MightsCodes.values()) {
            if (values.getCode() == getCurrentMightCode()) {
                return values.getToolsKit();
            }
        }
        Logger.printError(new RolesCodeException(), CurrentRolesInfo.class);
        return MightsCodes.ANONYMOUS_CODE.getToolsKit();
    }

    private static class RolesCodeException extends Exception {
    }
}
