package com.example.First_prj.ForAllCode;

public class Constants {
    public static final byte ONE = 1;
    public static final byte DEFAULT_TEXT_SIZE = 15;
    public static final byte ERROR_CODE = -1;
    public static final byte TEACHER_CODE = 1;
    public static final byte STUDENT_CODE = 2;
    public static final byte PARENT_CODE = 3;
    public static final byte STUDENT_TEACHER_CODE = 4;
    public static final byte JOURNAL_ACTIVITY_ID = 2;


    public static enum Month {

        January("Янврь"), February("Февраль"), March("Март"),
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

}
