package com.example.First_prj.MenuAndSwitchers;

public abstract class FunctionsIDAndNames {
//
    private final static String[] keysForActivity = {
            // чтоб глазами не считать.
            "Занятия", // 0
            "Общение", // 1
            "Журналы", // 2
            "Пользователи", // 3
            "Студенты", // 4
            "Мой профиль", // 5
            "чтото ещё", // 6
            "чтото ещё", // 7
            "!!!!", // 8
            "чтото ещё", // 9
            "чтото ещё", // 10
            "чтото ещё", // 11
            "чтото ещё", // 12
            "чтото ещё", // 13
            "чтото ещё", // 14
            "чтото ещё", // 15
            "чтото ещё", // 16
            "Доступна информация"};

    public final static byte JOURNAL_INDEX_AKA_JOURNAL_ACTIVITY_ID = 2;
    public final static byte ERROR_INDEX_AKA_INFORM_ACTIVITY_ID = 17;


    public static int getLength() {
        return keysForActivity.length;
    }

    public static String getFunctionName(int index) {
        return keysForActivity[index];
    }

    public static int[] getTeacherSet() {
        return new int[]{0, 1};
    }

    public static int[] getStudentSet() {
        return new int[]{0};
    }

    public static int[] getParentSet() {
        return new int[]{0};
    }

    public static int[] getTeacherAKAStudentSet() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 8};
    }

    public static int[] getAdminSet() {
        return new int[]{0};
    }

    public static int[] getInformerForAnonSet() {
        return new int[]{ERROR_INDEX_AKA_INFORM_ACTIVITY_ID};
        // тут привязь к активити, с информацией.
    }
}
