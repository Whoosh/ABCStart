package ru.journal.fspoPrj.server_java.storage;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import ru.journal.fspoPrj.server_java.Server;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public abstract class CachedStorage {

    private static final String TOKEN_KEY = "ssid";
    private static final String USER_ID_KEY = "user_id";
    private static final String ROLES_KEY = "roles";
    private static final String YEAR_ID_KEY = "year_id";

    private static final byte THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN = 5;

    private static String TOKEN = "";
    private static String MY_ID = "";
    private static String YEAR_ID = "";

    private static ConcurrentHashMap<String, Future<String>> futureResponsesStorage;
    private static ConcurrentHashMap<String, String> responseStorage;

    static {
        responseStorage = new ConcurrentHashMap<>();
        futureResponsesStorage = new ConcurrentHashMap<>();
    }

    public static void addExecutingQuery(String queryLink, Future<String> futureResponse) {
        futureResponsesStorage.put(queryLink, futureResponse);
    }

    public static void cachedAuthorizationInfo(String queryCode) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(peekResponse(queryCode));
            TOKEN = jsonObject.getString(TOKEN_KEY);
            MY_ID = jsonObject.getString(USER_ID_KEY);
            YEAR_ID = jsonObject.getString(YEAR_ID_KEY);
        } catch (JSONException e) {
            Logger.printError(e, Server.class);
        }
    }

    public static String getResponse(String queryCode) {
        return pullResponse(queryCode, false);
    }

    public static String peekResponse(String queryCode) {
        return pullResponse(queryCode, true);
    }

    private static String pullResponse(String queryCode, boolean deleteAfter) {
        try {
            String response = deleteAfter ? responseStorage.remove(queryCode) : responseStorage.get(queryCode);
            return response == null ? GlobalConfig.EMPTY_STRING : response;
        } catch (NullPointerException ex) {
            Logger.printError(ex, CachedStorage.class);
            return GlobalConfig.EMPTY_STRING;
        }
    }

    public static void saveResponse(String key, String value) {
        responseStorage.put(key, value);
    }

    public static void dropFutureResponsesQuery() {
        futureResponsesStorage.clear();
    }

    public static Set<Map.Entry<String, Future<String>>> getFutureResponses() {
        return futureResponsesStorage.entrySet();
    }

    public static boolean isHavingDataFor(String queryCode) {
        try {
            return !responseStorage.get(queryCode).equals(GlobalConfig.EMPTY_STRING);
        } catch (NullPointerException ex) {
            return false;
        }
    }

    public static boolean isTokenValid() {
        return TOKEN.length() > THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN;
    }

    public static boolean getFutureResponse(String queryCode) {
        try {
            return futureResponsesStorage.get(queryCode).isDone();
        } catch (NullPointerException ex) {
            return true;
        }
    }

    public static void clearToken() {
        TOKEN = GlobalConfig.EMPTY_STRING;
    }
}
