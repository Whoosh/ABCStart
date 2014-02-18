package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.keys_manager.IKeyApi;

public class ProfileInfo extends Human {

    private final String phone; // TODO может быть несколько если стандартизируют формат
    private final String photoLink;
    private final String mail;

    protected ProfileInfo(JSONObject element) {
        super(element);
        this.photoLink = ProfileKeys.PHOTO.getStringValue(element);
        this.mail = ProfileKeys.EMAIL.getStringValue(element);
        this.phone = ProfileKeys.PHONE.getStringValue(element);
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public String getPhones() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    private static enum ProfileKeys implements IKeyApi {
        PHOTO("stream"),
        EMAIL("offgr"),
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
