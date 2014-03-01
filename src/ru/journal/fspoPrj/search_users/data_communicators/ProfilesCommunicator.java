package ru.journal.fspoPrj.search_users.data_communicators;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class ProfilesCommunicator extends ServerCommunicator {

    public static final int ALL_PROFILES_QUERY = 1;

    private static final int UPPER = 1;
    private static final int LOWER = -1;
    private static final int EQUALS = 0;

    private Activity parent;
    private ArrayList<ProfileInfo> usersInfo;

    private String usersProfileQuery;

    public ProfilesCommunicator(Activity parent) {
        this.parent = parent;
        makeAllProfileQuery();
    }

    public void setCaller(Activity parent) {
        this.parent = parent;
    }

    private void makeAllProfileQuery() {
        usersProfileQuery = APIQuery.GET_USERS_LIST.getLink(getToken(), getYearID());
        lastQueryID = ALL_PROFILES_QUERY;
        super.sendQueryToServer(parent, makeExecutor());
    }

    private MainExecutor makeExecutor() {
        switch (lastQueryID) {
            case ALL_PROFILES_QUERY: {
                return new UsersProfileExecutor(usersProfileQuery, lastQueryID);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void cacheData(Intent data, int resultCode) {
        switch (resultCode) {
            case ALL_PROFILES_QUERY: {
                usersInfo = (ArrayList<ProfileInfo>) data.getSerializableExtra(usersProfileQuery);
                data.removeExtra(usersProfileQuery);
            }
            break;
        }
    }

    public ArrayList<ProfileInfo> getUsersInfo() {
        return usersInfo;
    }

    public void sortUsersByFirstName() {
        Collections.sort(usersInfo, new Comparator<ProfileInfo>() {
            @Override
            public int compare(ProfileInfo profileInfo, ProfileInfo profileInfo2) {
                return compareAnyName(profileInfo.getFirstName(), profileInfo2.getFirstName());
            }
        });
    }

    public void sortUsersByLastName() {
        Collections.sort(usersInfo, new Comparator<ProfileInfo>() {
            @Override
            public int compare(ProfileInfo profileInfo, ProfileInfo profileInfo2) {
                return compareAnyName(profileInfo.getLastName(), profileInfo2.getLastName());
            }
        });
    }

    public void sortUsersByMiddleName() {
        Collections.sort(usersInfo, new Comparator<ProfileInfo>() {
            @Override
            public int compare(ProfileInfo profileInfo, ProfileInfo profileInfo2) {
                return compareAnyName(profileInfo.getMiddleName(), profileInfo2.getMiddleName());
            }
        });
    }

    private int compareAnyName(String name, String name2) {
        return name.charAt(0) < name2.charAt(0) ? LOWER : name.charAt(0) > name2.charAt(0) ? UPPER : EQUALS;
    }
}