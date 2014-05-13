package ru.journal.fspoPrj.messages.communication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.util.ArrayList;

public class ChatInfoBuffer {

    public static final String EMPTY = "";
    private ArrayList<ChatMessage> chatMessages;
    private String normalFromPhotoLink = "";
    private String normalToPhotoLink = "";

    public ChatInfoBuffer(String jsonResponse) {
        JSONArray messages = makeJsonArray(jsonResponse);
        chatMessages = new ArrayList<>();
        for (int i = messages.length() - 1; i >= 0; i--) {
            chatMessages.add(new ChatMessage(getJObject(messages, i)));
        }
    }

    public void markMessages(String myID) {
        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage.getFromIDString().equals(myID)) {
                chatMessage.setMyMessage(true);
            }
        }
        if (chatMessages.get(0).isMyMessage()) {
            normalFromPhotoLink = chatMessages.get(0).getFromPhotoLink();
            normalToPhotoLink = chatMessages.get(0).getToPhotoLink();
        }else {
            normalToPhotoLink = chatMessages.get(0).getFromPhotoLink();
            normalFromPhotoLink = chatMessages.get(0).getToPhotoLink();
        }
    }

    public String getNormalFromPhotoLink() {
        return normalFromPhotoLink;
    }

    public String getNormalToPhotoLink() {
        return normalToPhotoLink;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    private JSONArray makeJsonArray(String jsonResponse) {
        try {
            return new JSONObject(jsonResponse).getJSONArray(ChatKeys.MESSAGES.getKey());
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return new JSONArray();
        }
    }

    private JSONObject getJObject(JSONArray messages, int i) {
        try {
            return messages.getJSONObject(i);
        } catch (JSONException e) {
            Logger.printError(e, getClass());
            return null;
        }
    }

    private static enum ChatKeys implements IKeyApi {

        STATUS("status"),
        MESSAGES("messages");

        private final String key;

        private ChatKeys(String key) {
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
