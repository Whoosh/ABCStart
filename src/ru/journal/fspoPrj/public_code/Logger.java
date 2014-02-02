package ru.journal.fspoPrj.public_code;

import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

public abstract class Logger {

    public static final int LAST_LINE = 1;

    public static void printError(Exception error, Class<?> anyClass) {
        System.err.println("Error is " + error.toString());
        System.out.println("In Line - " + new Exception().getStackTrace()[LAST_LINE].getLineNumber());
        System.err.println("In the " + anyClass.toString());
    }
}
