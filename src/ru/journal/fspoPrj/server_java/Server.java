package ru.journal.fspoPrj.server_java;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.Progress;
import ru.journal.fspoPrj.server_java.server_managers.Query;
import ru.journal.fspoPrj.server_java.storage.BufferedLink;
import ru.journal.fspoPrj.server_java.storage.CachedStorage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Server {

    private static final String DEFAULT_HOST = "fspo.segrys.ru";
    private static final char DEFAULT_PORT = 80;

    public static int PORT;
    public static String HOST;

    private static ExecutorService executorService;

    static {
        executorService = Executors.newCachedThreadPool();
    }

    private static BufferedLink pushAuthorizationQuery(String name, String password, Context context) {
        return sendQueryToServer(APIQuery.AUTHORIZATION.getLink(name, password), context);
    }

    private static BufferedLink sendQueryToServer(String queryLink, Context context) {
        CachedStorage.addExecutingQuery(queryLink, executorService.submit(new Query(queryLink)));
        return startQueryHandler(context, new BufferedLink(queryLink));
    }

    private static BufferedLink startQueryHandler(Context context, BufferedLink bufferedLink) {
        Intent intent = new Intent(context, Progress.class);
        ((Activity) context).startActivityForResult(intent, bufferedLink.getRequestCode());
        return bufferedLink;
    }


    public static BufferedLink connect(String name, String password, Context context) {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        return pushAuthorizationQuery(name, password, context);
    }

    public static void disconnect() {
        CachedStorage.clearToken();
    }

    public static BufferedLink connect(String name, String password, String address, int port, Context context) {
        HOST = address;
        PORT = port;
        return pushAuthorizationQuery(name, password, context);
    }

    public static boolean isNotMakingQueryNow(String queryCode) {
        return CachedStorage.getFutureResponse(queryCode);
    }

}
