package ru.journal.fspoPrj.public_code;

import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

public class Logger {

    public static void printError(Exception error, Class<?> anyClass) {
        System.err.println("Error is " + error.toString());
        System.out.println("In Line - " + new Exception().getStackTrace()[GlobalConfig.ONE].getLineNumber());
        System.err.println("In the " + anyClass.toString());
    }
}
