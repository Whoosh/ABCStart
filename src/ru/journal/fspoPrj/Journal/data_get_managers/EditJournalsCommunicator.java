package ru.journal.fspoPrj.journal.data_get_managers;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessons;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherLessonsExecutor;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

public class EditJournalsCommunicator extends LookingJournalsCommunicator {

    public static final int TEACHER_LESSONS_QUERY = 3;

    protected String teacherLessonsKeyQuery;
    protected TeacherLessons teacherLessons;

    public EditJournalsCommunicator(Activity caller) {
        super(caller);
    }

    @Override
    protected MainExecutor makeQueryExecutor() {
        switch (lastQueryID) {
            case TEACHER_LESSONS_QUERY: {
                return new TeacherLessonsExecutor(teacherLessonsKeyQuery, TEACHER_LESSONS_QUERY);
            }
            default: {
                return super.makeQueryExecutor();
            }
        }
    }

    @Override
    public void cacheData(Intent data, int caller) {
        switch (caller) {
            case TEACHER_LESSONS_QUERY: {
                teacherLessons = (TeacherLessons) data.getSerializableExtra(teacherLessonsKeyQuery);
                data.removeExtra(teacherLessonsKeyQuery);
                for (TeacherLessons.TeacherLesson lesson : teacherLessons.getLessons()) {
                    System.out.println(lesson);
                }
            }
            default: {
                super.cacheData(data, caller);
            }
        }
    }

    public void sendTeacherLessonsQuery(Activity caller) {
        teacherLessonsKeyQuery = APIQuery.GET_TEACHER_LESSON.getLink(getToken(), getYearID(), getMyID());
        lastQueryID = TEACHER_LESSONS_QUERY;
        super.sendQueryToServer(caller, makeQueryExecutor());
    }

}
