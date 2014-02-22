package ru.journal.fspoPrj.journal.data_get_managers;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupsList;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupsListExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisitExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.Student;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class JournalsCommunicator extends ServerCommunicator {

    public static final int GROUPS_LIST_QUERY = 1;
    public static final int LIGHT_VISITS_QUERY = 2;

    private static int lastQueryID;

    private String groupsListKeyQuery;
    private String lessonListKeyQuery;
    private String lightVisitsQuery;

    private GroupsList groupsList;
    private LightVisits lightVisits;

    public JournalsCommunicator(Activity caller) {
        sendGroupListQuery(caller);
    }

    public void resendLastQuery(Activity caller) {
        super.sendQueryToServer(caller, makeQueryExecutor());
    }

    private MainExecutor makeQueryExecutor() {
        switch (lastQueryID) {
            case GROUPS_LIST_QUERY: {
                return new GroupsListExecutor(groupsListKeyQuery, lessonListKeyQuery, GROUPS_LIST_QUERY);
            }
            case LIGHT_VISITS_QUERY: {
                return new LightVisitExecutor(lightVisitsQuery, LIGHT_VISITS_QUERY);
            }
        }
        return null;
    }

    public void cacheData(Intent data, int caller) {
        switch (caller) {
            case GROUPS_LIST_QUERY: {
                groupsList = (GroupsList) data.getSerializableExtra(groupsListKeyQuery);
                data.removeExtra(groupsListKeyQuery);
            }
            break;
            case LIGHT_VISITS_QUERY: {
                lightVisits = (LightVisits) data.getSerializableExtra(lightVisitsQuery);
                data.removeExtra(lightVisitsQuery);
            }
        }
    }

    private void sendGroupListQuery(Activity caller) {
        groupsListKeyQuery = APIQuery.GET_GROUP_LIST.getLink(getToken(), getYearID());
        lessonListKeyQuery = APIQuery.GET_GROUP_JOURNAL.getLink(getToken(), getYearID());
        lastQueryID = GROUPS_LIST_QUERY;
        super.sendQueryToServer(caller, makeQueryExecutor());
    }

    public void sendGroupVisitsLightQuery(Activity caller, String lessonID, String groupID) {
        lightVisitsQuery = APIQuery.GET_JOURNAL_VISITS_BY_GROUP_LIGHT.getLink(getToken(), getYearID(), lessonID, groupID);
        lastQueryID = LIGHT_VISITS_QUERY;
        super.sendQueryToServer(caller, makeQueryExecutor());
    }

    public LightVisits getLightVisits() {
        return lightVisits;
    }

    public String[] getSortedGroups() {
        try {
            return groupsList.getSortedGroups();
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
            return new String[0];
        }
    }

    public Student[] getStudents(String group) {
        return groupsList.getStudents(group);
    }

    public GroupLesson[] getLessons(String group) {
        return groupsList.getLessons(group);
    }

    public GroupLesson[] getLessons(String group, int semester) {
        return groupsList.getLessons(group, semester);
    }

    public String getStringGroupID(String group) {
        return groupsList.getLessons(group)[0].getStringGroupID();
    }
}
