package ru.journal.fspoPrj.server_java.profile_info;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

import java.util.ArrayList;

public class UserProfile {

    private static final String NULLABLE_STING = "null";

    private ArrayList<String> profileStorage;

    public UserProfile(JSONObject jsonUserInfo) {
        profileStorage = new ArrayList<>();
        for (int i = 0; i < JsonKeys.values().length; i++) {
            try {
                profileStorage.add(jsonUserInfo.getString(JsonKeys.values()[i].getJsonKey()));
            } catch (JSONException e) {
                profileStorage.add(GlobalConfig.EMPTY_STRING);
                Logger.printError(e, getClass());
            }
        }
        profileStorage.trimToSize();
        dropNullStringsValues();
    }

    public UserProfile() {
        profileStorage = new ArrayList<>();
        for (int i = 0; i < JsonKeys.values().length; i++) {
            profileStorage.add(GlobalConfig.EMPTY_STRING);
        }
        profileStorage.trimToSize();
    }

    public String getInfo(int index) {
        return profileStorage.get(index);
    }

    public void dropNullStringsValues() {
        for (int i = 0; i < profileStorage.size(); i++) {
            if (profileStorage.get(i).equals(NULLABLE_STING)) {
                profileStorage.set(i, GlobalConfig.EMPTY_STRING);
            }
        }
    }
}
