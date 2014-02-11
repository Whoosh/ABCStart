package ru.journal.fspoPrj.journal.data_get_managers;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupsList;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupsListExecutor;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.Student;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

import java.util.ArrayList;

public class JournalsCommunicator extends ServerCommunicator {

    public static final int GROUPS_LIST_QUERY = 1;
    public static final int RESEND_GROUP_LIST_FROM_GROUP_BUTTON = 2;
    public static final int RESEND_GROUP_LIST_FORM_REFRESH_BUTTON = 3;

    private final String groupsListKeyQuery;
    private final String lessonListKeyQuery;
    private GroupsList groupsList;

    public JournalsCommunicator(Activity caller) {
        groupsListKeyQuery = APIQuery.GET_GROUP_LIST.getLink(getToken(), getYearID());
        lessonListKeyQuery = APIQuery.GET_GROUP_JOURNAL.getLink(getToken(), getYearID());
        sendGroupsInfoQuery(caller, GROUPS_LIST_QUERY);
    }

    public void sendGroupsInfoQuery(Activity caller, int resultCode) {
        super.sendQueryToServer(caller, new GroupsListExecutor(groupsListKeyQuery, lessonListKeyQuery, resultCode));
    }

    public void cacheData(Intent data, int caller) {
        switch (caller) {
            case RESEND_GROUP_LIST_FORM_REFRESH_BUTTON:
            case RESEND_GROUP_LIST_FROM_GROUP_BUTTON:
            case GROUPS_LIST_QUERY: {
                groupsList = (GroupsList) data.getSerializableExtra(groupsListKeyQuery);
                data.removeExtra(groupsListKeyQuery);
            }
            break;
        }
    }

    public String[] getSortedGroups() {
        try {
            return groupsList.getSortedGroups();
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
            return new String[0];
        }
    }

    public String[] getStudentsName(String group) {
        Student[] students = groupsList.getStudents(group);
        String[] studentsName = new String[students.length];
        for (int i = 0; i < students.length; i++) {
            studentsName[i] = students[i].getShortName();
        }
        return studentsName;
    }

    public String[] getLessonsName(String group) {
        GroupLesson[] groupLessons = groupsList.getLessons(group);
        String[] lessonsName = new String[groupLessons.length];
        for(int i =0; i<lessonsName.length; i++){
            lessonsName[i] = groupLessons[i].getShortName();
        }
        return lessonsName;
    }
}
