package ru.journal.fspoPrj.server_java.might_info;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.main_menu.user_factory.ToolsGetter;
import ru.journal.fspoPrj.public_code.Logger;

import java.util.Arrays;

public abstract class MightInfo {

    public static void setDataFromJson(JSONObject jsonMightInfo) {
        for (int i = JsonValues.getStartNumber(); i < JsonValues.values().length; i++) {
            try {
                JsonValues.values()[i].setStatus(jsonMightInfo.getBoolean(JsonValues.values()[i].getJsonKey()));
            } catch (JSONException e) {
                Logger.printError(e, MightInfo.class);
                JsonValues.values()[i].setStatus(false);
            }
        }
    }

    public static int getCurrentMightCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < JsonValues.values().length; i++) {
            if (JsonValues.values()[i].getStatus()) {
                stringBuilder.append(i);
            }
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    public static ToolsGetter getToolsKit() {
        for (int i = 0; i < MightsCodes.values().length; i++) {
            if (MightsCodes.values()[i].getCode() == getCurrentMightCode()) {
                return MightsCodes.values()[i].getToolsKit();
            }
        }
        Logger.printError(new ArrayStoreException(), MightInfo.class);
        return MightsCodes.ANONYMOUS_CODE.getToolsKit();
    }
}
