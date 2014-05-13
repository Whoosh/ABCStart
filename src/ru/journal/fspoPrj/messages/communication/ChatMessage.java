package ru.journal.fspoPrj.messages.communication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

public class ChatMessage {

    public static final String SHIT_DATA = "/img/users/";
    public static final String EMPTY = "";

    private String fromFirstName = "";
    private String fromLastName = "";
    private String toFirstName = "";
    private String toLastName = "";
    private String toPhotoLink = "";
    private String messageText = "";
    private String messageTime = "";
    private String messageDate = "";
    private String fromPhotoLink = "";
    private int fromID;
    private int msgID;
    private int toID;

    private boolean myMessage;

    public ChatMessage(JSONObject jsonResponse) {
        fromFirstName = ChatMessageKeys.FROM_FIRST_NAME.getStringValue(jsonResponse);
        fromLastName = ChatMessageKeys.FROM_LAST_NAME.getStringValue(jsonResponse);
        toFirstName = ChatMessageKeys.TO_FIRST_NAME.getStringValue(jsonResponse);
        toLastName = ChatMessageKeys.TO_LAST_NAME.getStringValue(jsonResponse);
        toPhotoLink = ChatMessageKeys.TO_PHOTO_LINK.getStringValue(jsonResponse);
        messageText = ChatMessageKeys.TEXT.getStringValue(jsonResponse);
        messageTime = ChatMessageKeys.MESSAGE_TIME.getStringValue(jsonResponse);
        messageDate = ChatMessageKeys.MESSAGE_DATE.getStringValue(jsonResponse);
        fromPhotoLink = ChatMessageKeys.FROM_PHOTO_LINK.getStringValue(jsonResponse);
        fromID = ChatMessageKeys.FROM_ID.getIntValue(jsonResponse);
        msgID = ChatMessageKeys.MESSAGE_ID.getIntValue(jsonResponse);
        toID = ChatMessageKeys.TO_ID.getIntValue(jsonResponse);

        separatePhotoLink();
    }


    @Override
    public String toString() {
        return "ChatMessage{" +
                "fromFirstName='" + fromFirstName + '\'' +
                ", fromLastName='" + fromLastName + '\'' +
                ", toFirstName='" + toFirstName + '\'' +
                ", toLastName='" + toLastName + '\'' +
                ", toPhotoLink='" + toPhotoLink + '\'' +
                ", messageText='" + messageText + '\'' +
                ", messageTime='" + messageTime + '\'' +
                ", messageDate='" + messageDate + '\'' +
                ", fromPhotoLink='" + fromPhotoLink + '\'' +
                ", fromID=" + fromID +
                ", msgID=" + msgID +
                ", toID=" + toID +
                ", myMessage=" + myMessage +
                '}';
    }

    private void separatePhotoLink() {
        fromPhotoLink = fromPhotoLink.replace(SHIT_DATA, EMPTY);
        toPhotoLink = toPhotoLink.replace(SHIT_DATA, EMPTY);
    }

    public String getFromIDString() {
        return String.valueOf(fromID);
    }

    public String getMessageID() {
        return String.valueOf(msgID);
    }

    public String getTOIDString() {
        return String.valueOf(toID);
    }

    public String getFromFirstName() {
        return fromFirstName;
    }

    public String getFromLastName() {
        return fromLastName;
    }

    public String getToFirstName() {
        return toFirstName;
    }

    public String getToLastName() {
        return toLastName;
    }

    public String getToPhotoLink() {
        return toPhotoLink;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public String getFromPhotoLink() {
        return fromPhotoLink;
    }

    public int getFromID() {
        return fromID;
    }

    public int getMsgID() {
        return msgID;
    }

    public int getToID() {
        return toID;
    }

    public void setMyMessage(boolean myMessage) {
        this.myMessage = myMessage;
    }

    public boolean isMyMessage() {
        return myMessage;
    }

    private enum ChatMessageKeys implements IKeyApi {
        FROM_LAST_NAME("fromlast"),
        FROM_FIRST_NAME("fromfirst"),
        FROM_ID("fromid"),
        MESSAGE_ID("msgid"),
        FROM_PHOTO_LINK("fromphoto"),
        TO_FIRST_NAME("tofirst"),
        TO_LAST_NAME("tolast"),
        TO_ID("toid"),
        TO_PHOTO_LINK("tophoto"),
        TEXT("text"),
        MESSAGE_TIME("msgtime"),
        MESSAGE_DATE("msgdate");

        private final String key;

        private ChatMessageKeys(String key) {
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
