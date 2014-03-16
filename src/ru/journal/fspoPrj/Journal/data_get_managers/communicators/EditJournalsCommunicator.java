package ru.journal.fspoPrj.journal.data_get_managers.communicators;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.journal.data_get_managers.visit_full.FullVisits;
import ru.journal.fspoPrj.journal.data_get_managers.visit_full.FullVisitsExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroupsExecutor;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisitExecutor;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class EditJournalsCommunicator extends ServerCommunicator {

    public static final int TEACHER_LESSONS_QUERY = 1;
    public static final int FULL_VISITS_QUERY = 2;

    private int lastQueryID;
    private Activity parentCaller;

    private String visitsQuery;
    private String teacherLessonsQuery;

    private TeacherLessons teacherLessons;
    private FullVisits fullVisits;

    private TeacherGroup teacherGroup;

    public EditJournalsCommunicator(Activity parentCaller) {
        this.parentCaller = parentCaller;
        sendTeacherLessonsQuery();
    }

    private void sendTeacherLessonsQuery() {
        teacherLessonsQuery = APIQuery.GET_TEACHER_LESSON.getLink(getToken(), getYearID(), getMyID());
        lastQueryID = TEACHER_LESSONS_QUERY;
        sendQueryToServer(parentCaller, makeExecutor());
    }

    public void resendLastQuery() {
        super.sendQueryToServer(parentCaller, makeExecutor());
    }

    @Override
    protected MainExecutor makeExecutor() {
        switch (lastQueryID) {
            case TEACHER_LESSONS_QUERY: {
                return new TeacherGroupsExecutor(teacherLessonsQuery, lastQueryID);
            }
            case FULL_VISITS_QUERY: {
                return new LightVisitExecutor(visitsQuery, lastQueryID);
            }
        }
        return null;
    }

    public void cacheData(Intent data, int resultCode) {
        switch (resultCode) {
            case FULL_VISITS_QUERY: {
                //      fullVisits = (FullVisits) data.getSerializableExtra(visitsQuery);
                data.removeExtra(visitsQuery);
            }
            break;
            case TEACHER_LESSONS_QUERY: {
                teacherLessons = (TeacherLessons) data.getSerializableExtra(teacherLessonsQuery);
                data.removeExtra(teacherLessonsQuery);
            }
            break;
        }
    }

    public boolean isEmpty() {
        return (fullVisits == null);
    }

    public TeacherLessons getTeacherLessons() {
        return this.teacherLessons;
    }

    public void setTeacherGroup(TeacherGroup teacherGroup) {
        this.teacherGroup = teacherGroup;
    }

    public TeacherGroup getSelectedGroup() {
        return teacherGroup;
    }

    public void sendVisitsQueryByGroup(TeacherGroup group) {
// FIXME
        visitsQuery = APIQuery.GET_JOURNAL_VISITS_BY_GROUP_LIGHT.
                getLink(getToken(), getYearID(), "96", "10");
        lastQueryID = FULL_VISITS_QUERY;
        super.sendQueryToServer(parentCaller, makeExecutor());
    }
}
