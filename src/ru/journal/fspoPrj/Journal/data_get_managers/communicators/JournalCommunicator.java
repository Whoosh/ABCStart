package ru.journal.fspoPrj.journal.data_get_managers.communicators;

import android.content.Intent;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.public_code.humans_entity.Student;

import java.util.ArrayDeque;
import java.util.ArrayList;

public interface JournalCommunicator {

    int GROUPS_LIST_QUERY = 1;
    int LIGHT_VISITS_QUERY = 2;

    Group getGroup(String groupNumber);

    LightVisits getVisits();

    Integer[] getAllSemesters(Group group);

    GroupLesson[] getLessons(Group group, int semester);

    String[] getSortedGroups();

    ArrayList<Student> getStudents(Group selectedGroup);

    int getFirstPossiblySemester(Group selectedGroup);

    void cacheData(Intent data, int resultCode);

    void resendLastQuery();

    void sendGroupVisitsQuery(GroupLesson lesson);

    void setCallerLink(JournalActivity parentCaller);

    GroupLesson getLesson(String string, Group selectedGroup, int semester);
}
