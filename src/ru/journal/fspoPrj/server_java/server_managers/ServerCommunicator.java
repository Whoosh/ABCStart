package ru.journal.fspoPrj.server_java.server_managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;
import ru.journal.fspoPrj.server_java.server_info.CommunicationInfo;

public abstract class ServerCommunicator {

    public static final int REQUEST_CODE = 123111;
    public static final int RESULT_FAIL = 2547812;

    public static int PORT;
    public static String HOST;

    public static final String SERVER_COMMUTATION_KEY = "svCom";

    protected static final String DEFAULT_HOST = "fspo.segrys.ru";
    protected static final char DEFAULT_PORT = 80;

    protected static int lastQueryID;
    protected static CommunicationInfo communicationInfo;

    public String getToken() {
        return communicationInfo.getToken();
    }

    public String getMyID() {
        return communicationInfo.getMyID();
    }

    public String getYearID() {
        return communicationInfo.getYearID();
    }

    public void setCommunicationInfo(CommunicationInfo communicationInfo) {
        ServerCommunicator.communicationInfo = communicationInfo;
    }

    protected void sendQueryToServer(Activity caller, MainExecutor executor) {
        startQueryHandler(caller, executor);
    }

    private void startQueryHandler(Activity caller, MainExecutor executor) {
        Intent intent = new Intent(caller, ProgressActivity.class);
        intent.putExtra(SERVER_COMMUTATION_KEY, executor);
        caller.startActivityForResult(intent, REQUEST_CODE);
    }

    protected abstract MainExecutor makeExecutor();
}
