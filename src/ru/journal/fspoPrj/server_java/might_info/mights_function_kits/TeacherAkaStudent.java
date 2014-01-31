package ru.journal.fspoPrj.server_java.might_info.mights_function_kits;

import ru.journal.fspoPrj.server_java.might_info.Functions;

public class TeacherAkaStudent extends ToolKitsManager {

    public TeacherAkaStudent() {
        super(
                Functions.LOOK_JOURNALS.ordinal(),
                Functions.PROFILE.ordinal(),
                Functions.ERROR_INFORMER.ordinal(),
                Functions.MESSAGES.ordinal(),
                Functions.RESULT_RATING.ordinal(),
                Functions.LESSONS_MANAGER.ordinal(),
                Functions.SCHEDULE.ordinal(),
                Functions.STUDENTS.ordinal(),
                Functions.TEACHERS.ordinal(),
                Functions.TEST7.ordinal()// TODO
        );
    }
}
