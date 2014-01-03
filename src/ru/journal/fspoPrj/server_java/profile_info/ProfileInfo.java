package ru.journal.fspoPrj.server_java.profile_info;

import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class ProfileInfo {

    private static final String NULLABLE_STING = "null";

    public static void loadDataFromJson(JSONObject jsonUserInfo) {

        for (int i = 0; i < JsonValues.values().length; i++) {
            try {
                JsonValues.values()[i].setValue(jsonUserInfo.get(JsonKeys.values()[i].getKey()).toString());
            } catch (JSONException e) {
                Logger.printError(e, ProfileInfo.class);
            }
        }
        checkValuesForNull();
    }

    // сервер может возвращать этот калл
    private static void checkValuesForNull() {
        for (int i = 0; i < JsonValues.values().length; i++) {
            if (JsonValues.values()[i].getValue().equals(NULLABLE_STING)) {
                JsonValues.values()[i].setValue(GlobalConfig.EMPTY_STRING);
            }
        }
    }

    public static void setProfileEmpty() {
        for (int i = 0; i < JsonValues.values().length; i++) {
            JsonValues.values()[i].setValue(GlobalConfig.EMPTY_STRING);
        }
    }
}