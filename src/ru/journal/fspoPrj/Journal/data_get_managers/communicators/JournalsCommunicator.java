package ru.journal.fspoPrj.journal.data_get_managers.communicators;

import android.content.Intent;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupsList;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupsListExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisitExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.humans_entity.Student;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

import java.util.ArrayList;

public abstract class JournalsCommunicator extends ServerCommunicator implements JournalCommunicator {

    protected String groupsListKeyQuery;
    protected String lessonListKeyQuery;
    protected String lightVisitsQuery;

    protected GroupsList groupsList;
    protected LightVisits lightVisits;
    protected JournalActivity parentCaller;

    protected static boolean workingNow;

    public JournalsCommunicator(JournalActivity parentCaller) {
        this.parentCaller = parentCaller;
        sendGroupListQuery();
    }

    @Override
    public boolean isWorkingNow() {
        return workingNow;
    }

    @Override
    public void setWorkingNow(boolean workingNow) {
        JournalsCommunicator.workingNow = workingNow;
    }

    @Override
    public void setCallerLink(JournalActivity parentCaller) {
        this.parentCaller = parentCaller;
    }

    public void resendLastQuery() {
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    @Override
    protected MainExecutor makeExecutor() {
        setWorkingNow(true);
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

    public void sendGroupVisitsQuery(GroupLesson lesson) {
        lightVisitsQuery = APIQuery.GET_JOURNAL_VISITS_BY_GROUP_LIGHT
                .getLink(getToken(), getYearID(), lesson.getStringLessonID(), lesson.getStringGroupID());
        lastQueryID = LIGHT_VISITS_QUERY;
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    public LightVisits getVisits() {
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

    public ArrayList<Student> getStudents(Group group) {
        return groupsList.getStudents(group.getStringGroupNumber());
    }

    public GroupLesson[] getLessons(Group group, int semester) {
        return groupsList.getLessons(group.getStringGroupNumber(), semester);
    }

    @Override
    public Group getGroup(String groupNumber) {
        return groupsList.getGroup(groupNumber);
    }

    public int getFirstPossiblySemester(Group selectedGroup) {
        return groupsList.getFirstPossiblySemester(selectedGroup);
    }

    public Integer[] getAllSemesters(Group selectedGroup) {
        return groupsList.getAllSemesters(selectedGroup.getStringGroupNumber());
    }

    protected void sendGroupListQuery() {
        groupsListKeyQuery = APIQuery.GET_GROUP_LIST.getLink(getToken(), getYearID());
        lessonListKeyQuery = APIQuery.GET_GROUP_JOURNAL.getLink(getToken(), getYearID());
        lastQueryID = GROUPS_LIST_QUERY;
        super.sendQueryToServer(parentCaller, makeExecutor());
    }
}
