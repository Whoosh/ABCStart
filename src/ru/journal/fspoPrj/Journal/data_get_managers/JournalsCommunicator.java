package ru.journal.fspoPrj.journal.data_get_managers;

import android.content.Intent;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupsList;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupsListExecutor;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class JournalsCommunicator extends ServerCommunicator {

    public static final int GROUPS_LIST_QUERY = 1;

    private String getGroupsListKeyQuery = "";
    private GroupsList groupsList;

    public JournalsCommunicator(LookingJournalActivity activity) {
        getGroupsListKeyQuery = APIQuery.GET_GROUP_LIST.getLink(getToken(), getYearID());
        super.sendQueryToServer(activity, new GroupsListExecutor(getGroupsListKeyQuery, GROUPS_LIST_QUERY));
    }

    public void cacheData(Intent data, int caller) {
        switch (caller) {
            case GROUPS_LIST_QUERY: {
                groupsList = (GroupsList) data.getSerializableExtra(getGroupsListKeyQuery);
                data.removeExtra(getGroupsListKeyQuery);
            }
            break;
        }
    }

    public String[] getGroupArray() {
        return groupsList.getGroupsArray();
    }

    public CharSequence[] getSequenceGroups() {
        return groupsList.getSequenceGroups();
    }
}
