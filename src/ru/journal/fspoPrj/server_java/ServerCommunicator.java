package ru.journal.fspoPrj.server_java;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ProgressActivity;

public class ServerCommunicator {

    public static int PORT;
    public static String HOST;

    public static final String SERVER_COMMUTATION_KEY = "svCom";

    protected static final String DEFAULT_HOST = "fspo.segrys.ru";
    protected static final char DEFAULT_PORT = 80;

    protected static String TOKEN = "";
    protected static String MY_ID = "";
    protected static String YEAR_ID = "";

    private static final String TOKEN_KEY = "ssid";
    private static final String USER_ID_KEY = "user_id";
    private static final String YEAR_ID_KEY = "year_id";

    private static final byte THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN = 5;

    public void clearToken() {
        TOKEN = "";
    }

    public String getToken() {
        return TOKEN;
    }

    public String getMyID() {
        return MY_ID;
    }

    public boolean tokenIsValid() {
        return (TOKEN.length() > THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN);
    }

    protected void sendQueryToServer(Context context, MainExecutor executor) {
        startQueryHandler(context, executor);
    }

    protected void startQueryHandler(Context context, MainExecutor executor) {
        Intent intent = new Intent(context, ProgressActivity.class);
        intent.putExtra(SERVER_COMMUTATION_KEY, executor);
        context.startActivity(intent);
    }

    public void cacheCommunicationInfo(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            TOKEN = jsonObject.getString(TOKEN_KEY);
            MY_ID = jsonObject.getString(USER_ID_KEY);
            YEAR_ID = jsonObject.getString(YEAR_ID_KEY);
        } catch (JSONException e) {
            clearToken();
            Logger.printError(e, getClass());
        }
    }
}
