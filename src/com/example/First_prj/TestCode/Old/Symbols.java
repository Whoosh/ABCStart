package com.example.First_prj.TestCode.Old;

import java.util.Random;

public class Symbols {
    int indexer = 0;
    int maxRandom = 11;
    String string;
    String symbols = "\u5100\u5101\u5102\u5103\u5104\u5105\u5106\u5107\u5108\u5109\u5110";

    public Symbols(int height) {
    }

    public int getStringLen() {
        string = new String();
        indexer = 0;
        int len = new Random().nextInt(maxRandom) + 5;
        for (int i = 0; i < len; i++)
            string += symbols.charAt(new Random().nextInt(symbols.length()));
        return len;
    }

    public String getString() {
        indexer = new Random().nextInt(string.length());
        return "" + string.charAt(indexer);
    }

    public String getString2() {
        return "" + string.charAt(indexer);
    }

}
