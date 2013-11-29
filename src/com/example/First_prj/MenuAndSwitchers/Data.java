package com.example.First_prj.MenuAndSwitchers;

public class Data {

    private final static String[] keysForActivity = {"Занятия", "Общение", "Журналы", "Пользователи",
            "Студенты", "Мой профиль", "чтото ещё", "чтото ещё", "!!!!", "чтото ещё", "чтото ещё"
            , "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё"};

    public Data() {
        //@TODO загрузку с файла ?
    }

    public static int getLength() {
        return keysForActivity.length;
    }

    public static String getFunctionName(int index) {
        return keysForActivity[index];
    }

    public static int[] getTeacherSet(){
        return new int[]{0,1,2,3,4,5,6,8};
    }

    public static int[] getStudentSet(){
        return new int[]{0};
    }

    public static int[] getParentSet(){
        return new int[]{0};
    }

    public static int[] getTeacherStudentSet(){
        return new int[]{0};
    }
}
