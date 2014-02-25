package ru.journal.fspoPrj.journal.data_get_managers.communicators;

import android.content.Intent;
import ru.journal.fspoPrj.journal.JournalActivity;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroupsExecutor;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

public class EditJournalsCommunicator extends JournalsCommunicator {

    public EditJournalsCommunicator(JournalActivity parentCaller) {
        super(parentCaller);
    }

    @Override
    public void cacheData(Intent data, int caller) {
        super.cacheData(data, caller);
    }

    @Override
    protected MainExecutor makeQueryExecutor() {
        switch (lastQueryID) {
            case GROUPS_LIST_QUERY: {
                return new TeacherGroupsExecutor(APIQuery.GET_TEACHER_LESSON.getLink(getToken(), getYearID(), getMyID()),
                        groupsListKeyQuery, lessonListKeyQuery, GROUPS_LIST_QUERY);
            }
            default: {
                return super.makeQueryExecutor();
            }
        }
    }

}
