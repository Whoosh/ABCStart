package ru.journal.fspoPrj.messages.communication;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

import java.io.Serializable;
import java.sql.Struct;

public class LightMessage implements Serializable {

    public static final String SHIT_DATA = "/img/users/";
    public static final String EMPTY = "";
    private int chatUserID;
    private String photoLink = "";
    private String firstName = "";
    private String lastName = "";
    private String day = "";
    private String month = "";
    private String time = "";
    private String text = "";

    public LightMessage() {
        chatUserID = 0;
    }

    public LightMessage(JSONObject jsonResponse) {
        chatUserID = MessageKeys.USER_CHAT_ID.getIntValue(jsonResponse);
        photoLink = MessageKeys.PHOTO_LINK.getStringValue(jsonResponse);
        firstName = MessageKeys.FIRST_NAME.getStringValue(jsonResponse);
        lastName = MessageKeys.LAST_NAME.getStringValue(jsonResponse);
        day = MessageKeys.DAY.getStringValue(jsonResponse);
        month = MessageKeys.MONTH.getStringValue(jsonResponse);
        time = MessageKeys.TIME.getStringValue(jsonResponse);
        text = MessageKeys.TEXT.getStringValue(jsonResponse);

        separatePhotoLink();
    }

    private void separatePhotoLink() {
        photoLink = photoLink.replace(SHIT_DATA, EMPTY);
    }

    public String getStringChatUserID() {
        return String.valueOf(chatUserID);
    }

    public int getChatUserID() {
        return chatUserID;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }


    @Override
    public String toString() {
        return "LightMessage{" +
                "chatUserID=" + chatUserID +
                ", photoLink='" + photoLink + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", time='" + time + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    private static enum MessageKeys implements IKeyApi {

        USER_CHAT_ID("chat_user_id"),
        PHOTO_LINK("chat_user_img"),
        FIRST_NAME("chat_user_firstname"),
        LAST_NAME("chat_user_lastname"),
        DAY("day"),
        MONTH("month"),
        TIME("time"),
        TEXT("text");

        private final String key;

        private MessageKeys(String key) {
            this.key = key;
        }

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
