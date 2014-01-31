package ru.journal.fspoPrj.server_java.storage;


import ru.journal.fspoPrj.public_code.Logger;

import java.util.HashSet;

public class BufferedLinker {

    private static final String START_SEPARATOR = "GET /";
    public static final String END_SEPARATOR = "___";

    private static int queryIDGeneratedValue;
    private final int queryID;
    private final String queryLink;

    public BufferedLinker(String queryLink) {
        this.queryID = generateNewID();
        this.queryLink = queryLink;
    }

    public int getRequestCode() {
        return queryID;
    }

    public String getQueryLink() {
        return queryLink;
    }

    public int generateNewID() {
        return ++queryIDGeneratedValue;
    }

    public HashSet<String> getLinks() {
        String buffer = queryLink;
        HashSet<String> results = new HashSet<>();
        while (true) {
            try {
                buffer = buffer.substring(buffer.indexOf(START_SEPARATOR), buffer.indexOf(END_SEPARATOR));
                results.add(buffer);
                System.out.println(buffer);
                buffer = queryLink.substring(queryLink.indexOf(buffer) + buffer.length());
            } catch (IndexOutOfBoundsException e) {
                Logger.printError(e, getClass());
                for (String s : results) {
                    System.out.println(s);
                }//TODO
                return results;
            }
        }
    }

}
