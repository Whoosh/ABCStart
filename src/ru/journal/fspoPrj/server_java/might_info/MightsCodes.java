package ru.journal.fspoPrj.server_java.might_info;

import ru.journal.fspoPrj.server_java.might_info.mights_function_kits.*;
import ru.journal.fspoPrj.public_code.Logger;

public enum MightsCodes {

    ANONYMOUS_CODE(Anonymous.class, CurrentRolesInfo.RESERVED_TO_ANONYMOUS),
    TEACHER_CODE(Teacher.class, CurrentRolesInfo.TEACHER),
    STUDENT_CODE(Student.class, CurrentRolesInfo.STUDENT),
    PARENT_CODE(Parent.class, CurrentRolesInfo.PARENT),
    ADMIN_CODE(Admin.class, CurrentRolesInfo.ADMIN),
    STUDENT_AKA_TEACHER(TeacherAkaStudent.class, generateCode(TEACHER_CODE.code, STUDENT_CODE.code));

    private static int generateCode(int... codes) {
        StringBuilder builder = new StringBuilder();
        for (int code : codes) builder.append(code);
        return Integer.parseInt(builder.toString());
    }

    private final Class<?> toolsClass;
    private final int code;

    private MightsCodes(Class<?> toolsClass, CurrentRolesInfo roles) {
        this.code = roles.ordinal();
        this.toolsClass = toolsClass;
    }

    private MightsCodes(Class<?> toolsClass, int code) {
        this.code = code;
        this.toolsClass = toolsClass;
    }

    public int getCode() {
        return code;
    }

    public ToolKitsManager getToolsKit() {
        try {
            return (ToolKitsManager) toolsClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            Logger.printError(e, getClass());
        }
        return null;
    }
}