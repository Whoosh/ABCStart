package ru.journal.fspoPrj.main_menu.user_factory;

import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.main_menu.BugReportInformerActivity;
import ru.journal.fspoPrj.user_profile.ProfileActivity;

public enum Functions {

    JOURNALS("Журналы", LookingJournalActivity.class),
    PROFILE("Мой профиль", ProfileActivity.class),
    ERROR_INFORMER("Доступна информация", BugReportInformerActivity.class);

    private final Class<?> functionClass;
    private final String functionName;

    private Functions(String functionName, Class<?> functionClass) {
        this.functionClass = functionClass;
        this.functionName = functionName;
    }

    public Class<?> getToolClass() {
        return functionClass;
    }

    public String getToolName() {
        return functionName;
    }

}
