package ru.journal.fspoPrj.public_code.humans_entity;

import org.json.JSONObject;

public class ProfileInfo extends Human {

    private final String phone; // TODO может быть несколько если стандартизируют формат
    private final String photoLink;
    private final String mail;

    protected ProfileInfo(JSONObject element) {
        super(element);
        this.photoLink = getStringValue(element, ProfileKeys.PHOTO);
        this.mail = getStringValue(element, ProfileKeys.EMAIL);
        this.phone = getStringValue(element, ProfileKeys.PHONE);
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

    private static enum ProfileKeys implements Keys {
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
    }
}
