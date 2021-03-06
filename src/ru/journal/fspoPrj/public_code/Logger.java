package ru.journal.fspoPrj.public_code;

import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;

public abstract class Logger {
    // TODO logging on mail
    public static final int LAST_LINE = 1;
    public static final int CALLER_CLASS_INDEX = 3;

    public static void printError(Exception error, Class<?> anyClass) {
        System.err.println("Error is " + error.toString());
        System.out.println("In Line - " + new Exception().getStackTrace()[LAST_LINE].getLineNumber());
        System.err.println("In the " + anyClass.toString());
    }

    public static void printIKeyApiError(Exception error) {
        // FIXME что то вменяемое, когда к примеру профиля действительно нет ключа.
        /*/ для трассировки, можно врубать.
        StackTraceElement[] elements = error.getStackTrace();
        System.out.println("================================");
        System.err.println(elements[CALLER_CLASS_INDEX]);
        System.out.println("================================");
        error.printStackTrace();
        /*/
    }
}
