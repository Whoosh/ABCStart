package ru.journal.fspoPrj.journal.data_get_managers.groups;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.io.Serializable;
import java.util.*;

public class GroupsList implements Serializable {
    // TODO
    private static final String GROUPS_LIST_KEY = "groupsList";
    private static final String GROUPS_JOURNALS_KEY = "groupsJournals";
    private static final int ANY_STUDENTS_HERE = 3;
    private static final int DEFAULT_GROUP_COUNT = 24;

    private HashMap<String, Group> groups;

    public GroupsList(String groupsListResponse, String groupsJournalResponse) {
        groups = new HashMap<>(DEFAULT_GROUP_COUNT);
        try {
            JSONObject groupsList = new JSONObject(groupsListResponse).getJSONObject(GROUPS_LIST_KEY);
            JSONArray groupsNumberName = groupsList.names();
            for (int i = 0; i < groupsNumberName.length(); i++) {
                JSONArray group = groupsList.getJSONArray(groupsNumberName.getString(i));
                if (group.length() > ANY_STUDENTS_HERE) {
                    groups.put(groupsNumberName.getString(i), new Group(groupsNumberName.getInt(i), group));
                }
            }
            parseLessonForGroups(groupsJournalResponse);
        } catch (Exception e) {
            Logger.printError(e, getClass());
        }
    }

    private void parseLessonForGroups(String groupJournalResponse) throws JSONException {
        JSONObject groupsLessonsList = new JSONObject(groupJournalResponse).getJSONObject(GROUPS_JOURNALS_KEY);
        JSONArray groupsNumberName = groupsLessonsList.names();
        for (int i = 0; i < groupsNumberName.length(); i++) {
            Group group = groups.get(groupsNumberName.getString(i));
            if (group != null) {
                group.setGroupLessons(groupsLessonsList.getJSONArray(groupsNumberName.getString(i)));
            }
        }
    }

    public String[] getSortedGroups() {
        String[] sGroups = groups.keySet().toArray(new String[groups.size()]);
        Arrays.sort(sGroups);
        return sGroups;
    }

    public ArrayList<Student> getStudents(String group) {
        return groups.get(group).getStudents();
    }

    public GroupLesson[] getLessons(String group, int semester) {
        return groups.get(group).getGroupLessons(semester);
    }

    public Group getGroup(String groupNumber) {
        return groups.get(groupNumber);
    }

    public int getFirstPossiblySemester(Group selectedGroup) {
        return groups.get(selectedGroup.getStringGroupNumber()).getFirstPossiblySemester();
    }


    public Integer[] getAllSemesters(String groupNumber) {
        return groups.get(groupNumber).getAllSemesters();
    }

}
