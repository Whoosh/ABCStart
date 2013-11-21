package com.example.First_prj.FirstActivitySettings;

public class FormWorkFunctions {

    public static boolean isNumber(CharSequence str) {
        if (str.length() == 0) return true;
        byte result = 0;
        for (byte code = 48; code < 58; code++)
            for (byte i = 0; i < str.length(); i++)
                if (code == (byte) str.charAt(i))
                    result++;
        return (result == str.length());
    }
}
