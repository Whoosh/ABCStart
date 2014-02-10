package ru.journal.fspoPrj.public_code;

public enum Month {

    January("Январь"), February("Февраль"), March("Март"),
    April("Апрель"), May("Май"), June("Июнь"),
    July("Июль"), August("Август"), September("Сентябрь"),
    October("Октябрь"), November("Ноябрь"), December("Декабрь");

    public static final int JULIAN_CALENDAR = 12;

    private final String month;

    private Month(final String month) {
        this.month = month;
    }

    public static String getMonth(int number) {
        return values()[number % JULIAN_CALENDAR].thisMonth();
    }

    private String thisMonth() {
        return this.month;
    }
}