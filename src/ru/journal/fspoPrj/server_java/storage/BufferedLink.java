package ru.journal.fspoPrj.server_java.storage;


public class BufferedLink {

    private static int queryIDGeneratedValue;
    private final int queryID;
    private final String queryLink;

    public BufferedLink(String queryLink) {
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

}
