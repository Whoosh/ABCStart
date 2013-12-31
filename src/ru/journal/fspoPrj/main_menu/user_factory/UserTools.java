package ru.journal.fspoPrj.main_menu.user_factory;

import ru.journal.fspoPrj.main_menu.user_factory.mights.*;
import ru.journal.fspoPrj.server_java.MightInfo;

public class UserTools implements InfoGetter {

    private final InfoGetter tools;

    public UserTools(int mightCode) {
        switch (mightCode) {
            case MightInfo.PARENT_MIGHT_CODE:
                tools = new Parent();
                break;
            case MightInfo.ADMIN_MIGHT_CODE:
                tools = new Admin();
                break;
            case MightInfo.STUDENT_AKA_TEACHER_MIGHT_CODE:
                tools = new TeacherAkaStudent();
                break;
            case MightInfo.STUDENT_MIGHT_CODE:
                tools = new Student();
                break;
            case MightInfo.TEACHER_MIGHT_CODE:
                tools = new Teacher();
                break;
            case MightInfo.ANONYMOUS_MIGHT_CODE:
                tools = new Anonymous();
                break;
            default: {
                tools = new Anonymous();
            }
        }
    }

    @Override
    public Class<?> getToolClass(int index) {
        return tools.getToolClass(index);
    }

    @Override
    public String getToolName(int index) {
        return tools.getToolName(index);
    }

    @Override
    public int getToolsCount() {
        return tools.getToolsCount();
    }
}
