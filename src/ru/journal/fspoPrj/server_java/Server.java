package ru.journal.fspoPrj.server_java;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import ru.journal.fspoPrj.login_form.query_manager.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ProgressActivity;
import ru.journal.fspoPrj.server_java.server_managers.Query;
import ru.journal.fspoPrj.server_java.storage.BufferedLinker;
import ru.journal.fspoPrj.server_java.storage.CachedStorage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Server {

    private static final String DEFAULT_HOST = "fspo.segrys.ru";
    private static final char DEFAULT_PORT = 80;

    public static final String EXECUTOR_TAG = "executor";

    public static int PORT;
    public static String HOST;

    private static boolean executingErrorStatus;
    private static boolean makingQueryNow;

    private static ExecutorService executorService;

    static {
        executorService = Executors.newCachedThreadPool();
    }

    public static BufferedLinker authorizationQuery(String name, String password, Context context) {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        return pushAuthorizationQuery(name, password, context);
    }

    public static BufferedLinker authorizationQuery(String name, String password, String address, int port, Context context) {
        HOST = address;
        PORT = port;
        return pushAuthorizationQuery(name, password, context);
    }

    public static void disconnect(Context context) {
        if (CachedStorage.tokenIsValid()) {
            sendQueryToServer(APIQuery.LOG_OUT.getLink(CachedStorage.getToken()), context, new MainExecutor());
            CachedStorage.clearToken();
        }
    }

    public static BufferedLinker setFutureQuery(String queryLink) {
        CachedStorage.addExecutingQuery(queryLink, executorService.submit(new Query(queryLink)));
        return new BufferedLinker(queryLink);
    }

    private static BufferedLinker pushAuthorizationQuery(String name, String password, Context context) {
        String queryLink = APIQuery.AUTHORIZATION.getLink(name, password);
        return sendQueryToServer(queryLink, context, new AuthorizationExecutor(queryLink));
    }

    private static BufferedLinker sendQueryToServer(String queryLink, Context context, MainExecutor executor) {
        CachedStorage.addExecutingQuery(queryLink, executorService.submit(new Query(queryLink)));
        return startQueryHandler(context, new BufferedLinker(queryLink), executor);
    }

    private static BufferedLinker startQueryHandler(Context context, BufferedLinker bufferedLinker, MainExecutor executor) {
        Intent intent = new Intent(context, ProgressActivity.class);
        intent.putExtra(EXECUTOR_TAG, executor);
        makingQueryNow = true;
        ((Activity) context).startActivityForResult(intent, bufferedLinker.getRequestCode());
        return bufferedLinker;
    }

    public static boolean isMakingQueryNow() {
        return makingQueryNow;
    }

    public static void setMakingQueryNow(boolean status) {
        makingQueryNow = status;
    }

    public static boolean haveErrorsWhenExecutingQuery() {
        return executingErrorStatus;
    }

    public static void setExecutingErrorStatus(boolean status) {
        executingErrorStatus = status;
    }

}
