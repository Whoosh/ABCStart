package ru.journal.fspoPrj.messages.communication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.util.ArrayList;

public class MessagesInfo implements Serializable {

    private ArrayList<LightMessage> lightMessages;

    public MessagesInfo(String jsonResponse) {
        JSONArray messages = makeJsonArray(jsonResponse);
        this.lightMessages = new ArrayList<>();
        for (int i = 0; i < messages.length(); i++) {
            this.lightMessages.add(getJObject(messages, i));
        }
    }

    public ArrayList<LightMessage> getLightMessages() {
        return lightMessages;
    }

    private LightMessage getJObject(JSONArray messages, int i) {
        try {
            return new LightMessage(messages.getJSONObject(i));
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return new LightMessage();
        }
    }

    private JSONArray makeJsonArray(String jsonResponse) {
        try {
            return new JSONObject(jsonResponse).getJSONArray(MessagesKeys.MESSAGES.getKey());
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return new JSONArray();
        }
    }

    private static enum MessagesKeys implements IKeyApi {

        STATUS("status"),
        MESSAGES("messages");

        private final String key;

        private MessagesKeys(String key) {
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
