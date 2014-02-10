package ru.journal.fspoPrj.journal.data_get_managers.groups_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class GroupsList implements Serializable {

    private static final String GROUPS_LIST_KEY = "groupsList";
    private static final int ANY_STUDENT_HERE = 1;

    private ArrayList<Group> groups;

    public GroupsList(String jsonResponse) {
        try {
            JSONObject groupsList = new JSONObject(new JSONObject(jsonResponse).getString(GROUPS_LIST_KEY));
            JSONArray groupList = groupsList.names();
            groups = new ArrayList<>(groupList.length());
            for (int i = 0; i < groupList.length(); i++) {
                JSONArray group = groupsList.getJSONArray(groupList.getString(i));
                if (group.length() > ANY_STUDENT_HERE) {
                    groups.add(new Group(groupList.getInt(i), group));
                }
            }
            groups.trimToSize();
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
        Collections.sort(groups);
    }

    public String[] getGroupsArray() {
        String[] groups = new String[this.groups.size()];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = String.valueOf(this.groups.get(i).getGroupNumber());
        }
        return groups;
    }

    public CharSequence[] getSequenceGroups() {
        CharSequence[] groups = new CharSequence[this.groups.size()];
        for (int i = 0; i < groups.length; i++) {
            groups[i] = String.valueOf(this.groups.get(i).getGroupNumber());
        }
        return groups;
    }
}
