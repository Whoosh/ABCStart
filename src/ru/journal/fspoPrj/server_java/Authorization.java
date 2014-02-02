package ru.journal.fspoPrj.server_java;

import android.content.Context;
import ru.journal.fspoPrj.login_form.query_manager.AuthorizationExecutor;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.might_info.mights_function_kits.ToolKitsManager;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.Query;

public class Authorization extends ServerCommunicator {

    private static AuthorizationExecutor authorizationExecutor;
    private OnAuthCallBack onAuthCallBack;

    public Authorization() {
    }

    public Authorization(OnAuthCallBack listener) {
        this.onAuthCallBack = listener;
        if (authorizationExecutor != null) {
            authorizationExecutor.resetAuthListener(listener);
        }
    }

    public OnAuthCallBack getOnAuthCallBack() {
        return onAuthCallBack;
    }

    public void over() {
        authorizationExecutor = null;
    }

    public boolean isOver() {
        try {
            return authorizationExecutor.isDone();
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public void authNormal(Context context, String name, String password) {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        sendAuthorizationQuery(context, name, password);
    }

    public void authWithProxy(Context context, String name, String password, String address, int port) {
        HOST = address;
        PORT = port;
        sendAuthorizationQuery(context, name, password);
    }

    public void disconnect() {
        if (super.tokenIsValid()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new Query(APIQuery.LOG_OUT.getLink(getToken())).call();
                        clearToken();
                    } catch (Exception e) {
                        Logger.printError(e, getClass());
                    }
                }
            }).start();
        }
    }

    private void sendAuthorizationQuery(Context context, String name, String password) {
        authorizationExecutor = new AuthorizationExecutor(APIQuery.AUTHORIZATION.getLink(name, password), this);
        super.sendQueryToServer(context, authorizationExecutor);
    }

    public static interface OnAuthCallBack {
        void authSuccessful(ToolKitsManager toolKits);
    }
}
