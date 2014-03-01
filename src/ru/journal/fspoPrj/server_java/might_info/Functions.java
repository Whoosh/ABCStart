package ru.journal.fspoPrj.server_java.might_info;

import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.journal.edit_journal.TeacherJournalActivity;
import ru.journal.fspoPrj.journal.looking_journal.LookingJournalActivity;
import ru.journal.fspoPrj.main_menu.BugReportInformerActivity;
import ru.journal.fspoPrj.search_users.search_all.SearchAllProfilesActivity;

public enum Functions {

    // WARNING Имена не могут быть одинаковыми
    // При внесении класса, незабыть добавить его в манифест !

    LOOK_JOURNALS("Просмотр журналов групп", LookingJournalActivity.class, R.drawable.ic_journal),

    PROFILE("Поиск пользователя", SearchAllProfilesActivity.class, R.drawable.ic_people),

    LESSONS_MANAGER("Мои журналы", TeacherJournalActivity.class, R.drawable.ic_lessons),

    ERROR_INFORMER("Свяжитесь с автором", BugReportInformerActivity.class, R.drawable.ic_some_wrong),

    MESSAGES("Сообщения", BugReportInformerActivity.class, R.drawable.ic_chat),

    RESULT_RATING("Итоговые оценки", BugReportInformerActivity.class, R.drawable.ic_result_rating),

    SCHEDULE("Расписание", BugReportInformerActivity.class, R.drawable.ic_schedule),

    TEACHERS("Преподователи", BugReportInformerActivity.class, R.drawable.ic_people);

    private final Class<?> functionClass;
    private final String functionName;
    private final int toolIconID;

    private Functions(String functionName, Class<?> functionClass, int toolIconID) {
        this.functionClass = functionClass;
        this.functionName = functionName;
        this.toolIconID = toolIconID;
    }

    public Class<?> getToolClass() {
        return functionClass;
    }

    public String getToolName() {
        return functionName;
    }

    public int getToolIconID() {
        return toolIconID;
    }
}
