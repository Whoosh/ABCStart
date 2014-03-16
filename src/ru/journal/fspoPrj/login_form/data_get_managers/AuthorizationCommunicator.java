package ru.journal.fspoPrj.login_form.data_get_managers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_info.CommunicationInfo;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.Query;

public class AuthorizationCommunicator extends ServerCommunicator {

    public static final int AUTH_REQUEST = 1;

    private OnAuthCallBack onAuthCallBack;

    public void setAuthCallBack(OnAuthCallBack callBack) {
        this.onAuthCallBack = callBack;
    }

    public void authNormal(Activity caller, String name, String password) {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        sendAuthorizationQuery(caller, name, password);
    }

    public void authWithProxy(Activity caller, String name, String password, String address, int port) {
        HOST = address;
        PORT = port;
        sendAuthorizationQuery(caller, name, password);
    }

    public void handleResponse(Intent data, int caller) {
        if (caller == AUTH_REQUEST) {
            setCommunicationInfo((CommunicationInfo) data.getSerializableExtra(AuthorizationExecutor.COMMUNICATION_KEY));
            data.removeExtra(AuthorizationExecutor.COMMUNICATION_KEY);
            onAuthCallBack.authSuccessful(data);
        }
    }

    // TODO
    private void sendAuthorizationQuery(Activity caller, String name, String password) {
        super.sendQueryToServer(caller, new AuthorizationExecutor(APIQuery.AUTHORIZATION.getLink(name, password), AUTH_REQUEST));
    }

    public void disconnect() {
        try {
            if (communicationInfo.tokenIsValid()) {
                new Thread(new SilenceExitQuery()).start();
                communicationInfo.dropInfo();
            }
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }

    @Override
    protected MainExecutor makeExecutor() {
        return null;
    }

    public static interface OnAuthCallBack {
        void authSuccessful(Intent resources);
    }

    private class SilenceExitQuery implements Runnable {
        @Override
        public void run() {
            try {
                new Query(APIQuery.LOG_OUT.getLink(getToken())).call();
            } catch (Exception e) {
                Logger.printError(e, getClass());
            }
        }
    }
}
