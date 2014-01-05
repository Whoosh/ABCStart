package ru.journal.fspoPrj.server_java.might_info;

import ru.journal.fspoPrj.main_menu.user_factory.ToolsGetter;
import ru.journal.fspoPrj.main_menu.user_factory.mights_function_kits.*;

public enum MightsCodes {

    ANONYMOUS_CODE(
            new Anonymous(),
            JsonValues.RESERVED_TO_ANONYMOUS.ordinal()
    ),
    TEACHER_CODE(
            new Teacher(),
            JsonValues.TEACHER.ordinal()
    ),
    STUDENT_CODE(
            new Student(),
            JsonValues.STUDENT.ordinal()
    ),
    PARENT_CODE(
            new Parent(),
            JsonValues.PARENT.ordinal()
    ),
    ADMIN_CODE(
            new Admin(),
            JsonValues.ADMIN.ordinal()
    ),
    STUDENT_AKA_TEACHER(
            new TeacherAkaStudent(),
            generateCode(TEACHER_CODE.code, STUDENT_CODE.code)
    );


    private static int generateCode(int... codes) {
        StringBuilder builder = new StringBuilder();
        for (int code : codes) builder.append(code);
        return Integer.parseInt(builder.toString());
    }

    private final User tool;
    private final int code;

    private MightsCodes(User tool, int code) {
        this.code = code;
        this.tool = tool;
    }

    public int getCode() {
        return code;
    }

    public ToolsGetter getToolsKit() {
        return tool;
    }
}