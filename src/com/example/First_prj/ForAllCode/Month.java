package com.example.First_prj.ForAllCode;

public enum Month {
    January("Январь"), February("Февраль"), March("Март"),
    April("Апрель"), May("Май"), June("Июнь"),
    July("Июль"), August("Август"), September("Сентябрь"),
    October("Октябрь"), November("Ноябрь"), December("Декабрь");

    private final String month;

    private Month(final String month) {
        this.month = month;
    }

    public static String getMonth(int number) {
        return values()[number % 12].thisMonth();
    }

    private String thisMonth() {
        return this.month;
    }
}