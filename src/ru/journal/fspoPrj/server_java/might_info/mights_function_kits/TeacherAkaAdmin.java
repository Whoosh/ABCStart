package ru.journal.fspoPrj.server_java.might_info.mights_function_kits;

import ru.journal.fspoPrj.server_java.might_info.Functions;
import ru.journal.fspoPrj.server_java.might_info.Tools.ToolKitsManager;

public class TeacherAkaAdmin extends ToolKitsManager {

    public TeacherAkaAdmin() {
        super(
                Functions.LOOK_JOURNALS,
                Functions.USERS_PROFILES,
                Functions.ERROR_INFORMER,
                Functions.LESSONS_MANAGER,
                Functions.MESSAGES
        );
    }
}
