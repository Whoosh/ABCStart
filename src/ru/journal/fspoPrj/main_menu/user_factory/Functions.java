package ru.journal.fspoPrj.main_menu.user_factory;

import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.main_menu.BugReportInformerActivity;
import ru.journal.fspoPrj.user_profile.ProfileActivity;

public enum Functions {

    JOURNALS("Журналы", LookingJournalActivity.class),
    PROFILE("Мой профиль", ProfileActivity.class),
    ERROR_INFORMER("Доступна информация", BugReportInformerActivity.class);

    private final Function function;

    private Functions(String functionName, Class<?> functionClass) {
        this.function = new Function(functionName, functionClass);
    }

    public Class<?> getToolClass() {
        return function.functionClass;
    }

    public String getToolName() {
        return function.functionName;
    }

    private class Function {
        public String functionName;
        public Class<?> functionClass;

        public Function(String functionName, Class<?> functionClass) {
            this.functionClass = functionClass;
            this.functionName = functionName;
        }
    }
}
