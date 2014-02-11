package ru.journal.fspoPrj.public_code;

public enum Month {

    JANUARY("Январь"), FEBRUARY("Февраль"), MARCH("Март"),
    APRIL("Апрель"), MAY("Май"), JUNE("Июнь"),
    JULY("Июль"), AUGUST("Август"), SEPTEMBER("Сентябрь"),
    OCTOBER("Октябрь"), NOVEMBER("Ноябрь"), DECEMBER("Декабрь");

    public static final int JULIAN_CALENDAR = 12;

    private final String month;

    private Month(final String month) {
        this.month = month;
    }

    public static String getMonth(int number) {
        return values()[number % JULIAN_CALENDAR].thisMonth();
    }

    public String getMonth() {
        return month;
    }

    private String thisMonth() {
        return this.month;
    }

    public enum FistSemester {
        SEMESTER(Month.SEPTEMBER), OCTOBER(Month.OCTOBER), NOVEMBER(Month.NOVEMBER), DECEMBER(Month.DECEMBER);

        private final Month month;

        FistSemester(Month month) {
            this.month = month;
        }

        public Month getMonth() {
            return month;
        }
    }

    public enum LastSemester {
        JANUARY(Month.JANUARY), FEBRUARY(Month.FEBRUARY), MARCH(Month.MARCH), APRIL(Month.APRIL), MAY(Month.MAY), JUNE(Month.JUNE);

        private final Month month;

        LastSemester(Month month) {
            this.month = month;
        }

        public Month getMonth() {
            return month;
        }
    }

}