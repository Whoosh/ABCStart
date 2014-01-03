package ru.journal.fspoPrj.main_menu.user_factory.mights_function_kits;

import ru.journal.fspoPrj.main_menu.user_factory.Functions;

public class TeacherAkaStudent extends User {

    public TeacherAkaStudent() {
        super(
                Functions.JOURNALS.ordinal(),
                Functions.PROFILE.ordinal(),
                Functions.ERROR_INFORMER.ordinal()
        );
    }
}
