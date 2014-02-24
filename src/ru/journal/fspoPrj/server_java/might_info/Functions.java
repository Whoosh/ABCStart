package ru.journal.fspoPrj.server_java.might_info;

import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.journal.LookingJournalActivity;
import ru.journal.fspoPrj.journal.TeacherJournalsActivity;
import ru.journal.fspoPrj.main_menu.BugReportInformerActivity;
import ru.journal.fspoPrj.user_profile.ProfileActivity;

public enum Functions {

    // WARNING Имена не могут быть одинаковыми
    // При внесении класса, незабыть добавить его в манифест !

    LOOK_JOURNALS("Просмотр журналов групп", LookingJournalActivity.class, R.drawable.ic_journal),

    PROFILE("Мой профиль", ProfileActivity.class, R.drawable.ic_my_profile),

    ERROR_INFORMER("Свяжитесь с автором", BugReportInformerActivity.class, R.drawable.ic_some_wrong),

    MESSAGES("Сообщения", BugReportInformerActivity.class, R.drawable.ic_chat),

    RESULT_RATING("Итоговые оценки", BugReportInformerActivity.class, R.drawable.ic_result_rating),

    LESSONS_MANAGER("Мои журналы", TeacherJournalsActivity.class, R.drawable.ic_lessons),

    SCHEDULE("Расписание", BugReportInformerActivity.class, R.drawable.ic_schedule),

    STUDENTS("Студенты", BugReportInformerActivity.class, R.drawable.ic_people),

    TEACHERS("Преподователи", BugReportInformerActivity.class, R.drawable.ic_people),

    TEST7("TEST7", BugReportInformerActivity.class, R.drawable.ic_some_wrong);

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
