package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

public class ProfileInfo extends Human {

    private final String phone; // TODO может быть несколько если стандартизируют формат
    private final String photoLink;
    private final String mail;
    private int group;

    public ProfileInfo(JSONObject element) {
        super(element);
        this.photoLink = ProfileKeys.PHOTO.getStringValue(element);
        this.mail = ProfileKeys.EMAIL.getStringValue(element);
        this.phone = ProfileKeys.PHONE.getStringValue(element);
    }

    public ProfileInfo(JSONObject element, int group) {
        this(element);
        this.group = group;
    }

    @Override
    public String toString() {
        return "ProfileInfo{" +
                "phone='" + phone + '\'' +
                ", photoLink='" + photoLink + '\'' +
                ", mail='" + mail + '\'' +
                "} " + super.toString();
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public String getStringGroup() {
        return String.valueOf(group);
    }

    private static enum ProfileKeys implements IKeyApi {
        PHOTO("photo"),
        EMAIL("email"),
        PHONE("phone");

        private final String key;

        private ProfileKeys(String key) {
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
