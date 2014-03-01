package ru.journal.fspoPrj.server_java.might_info.mights_function_kits;

import ru.journal.fspoPrj.server_java.might_info.Functions;
import ru.journal.fspoPrj.server_java.might_info.Tools.ToolKitsManager;

import java.io.Serializable;

public class TeacherAkaStudent extends ToolKitsManager {

    public TeacherAkaStudent() {
        super(
                Functions.LOOK_JOURNALS,
                Functions.PROFILE,
                Functions.ERROR_INFORMER,
                Functions.MESSAGES,
                Functions.RESULT_RATING,
                Functions.LESSONS_MANAGER,
                Functions.SCHEDULE,
                Functions.TEACHERS
        );
    }
}
