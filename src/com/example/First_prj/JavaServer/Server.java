package com.example.First_prj.JavaServer;

import android.content.Context;
import com.example.First_prj.ForAllCode.GlobalConstants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.*;

public abstract class Server {

    private static final String DEFAULT_HOST = "fspo.segrys.ru";
    private static final String DEFAULT_API_LINK = "GET /api";
    private static final String USER_CLIENT_INFO = "User-Agent: .\n";
    private static final String HTTP_VERSION = " HTTP/1.0";
    private static final String HOST_INFO = "Host: ";
    private static final byte DEFAULT_PORT = 80;

    private static String HOST;
    private static int PORT;

    private static ExecutorService executorService;
    private static Context activityContext;

    private static final byte AUTHORIZATION_QUERY_ID = 0;
    private static final byte EMPTY_QUERY_ID = 1;
    private static final byte EXIT_QUERY_ID = 2;
    private static final byte USER_INFO_QUERY_ID = 3;

    private static final String QUERY_SUCCEEDED = "Accept-Encoding";
    private static final String TOKEN_TAG = "ssid";
    private static final String USER_ID_TAG = "user_id";

    private static final byte DEFAULT_WAIT_RESPONSE_DELAY = 5;
    private static final byte SHORT_RESPONSE_DELAY = 1;

    private static String TOKEN = GlobalConstants.EMPTY_STRING;
    private static String MY_ID = GlobalConstants.EMPTY_STRING;

    private static byte indexer;

    public static final byte TEACHER_CODE = 1;
    public static final byte STUDENT_CODE = 2;
    public static final byte PARENT_CODE = 3;
    public static final byte STUDENT_TEACHER_CODE = 4;

    public static void connect(Context context, String name, String password) throws TimeoutException {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        startServerConnection(context, name, password);
    }

    public static void connect(Context context, String name, String password, String proxyAddress, int port) throws TimeoutException {
        HOST = proxyAddress;
        PORT = port;
        startServerConnection(context, name, password);
    }

    public static boolean isPasswordOK() {
        return TOKEN.length() > 5; // самый быстрый способ проверки на валидность токена. Null = 4;
    }

    private static void startServerConnection(Context context, String name, String password) throws TimeoutException {

        if (executorService != null)
            executorService.shutdown();

        executorService = Executors.newFixedThreadPool(GlobalConstants.ONE); // 1 поток на исполнение
        activityContext = context;

        Future<String> response = executorService.submit(new Query(AUTHORIZATION_QUERY_ID, name, password));

        waitResponse(response, DEFAULT_WAIT_RESPONSE_DELAY);

        String jsonString = getResponseString(response);
        System.out.println(jsonString);
        try {
            TOKEN = new JSONObject(jsonString).get(TOKEN_TAG).toString();
            MY_ID = new JSONObject(jsonString).get(USER_ID_TAG).toString();
        } catch (JSONException e) {
            startServerConnection(context, name, password);
            e.printStackTrace();
            if (3 < indexer++) {
                indexer = 0;
                throw new TimeoutException();
            }
        }
    }

    public static UserInfo getUserInfo() throws TimeoutException {
        UserInfo userInfo = new UserInfo();

        Future<String> response = executorService.submit(new Query(USER_INFO_QUERY_ID));
        waitResponse(response, DEFAULT_WAIT_RESPONSE_DELAY);

        String jsonString = getResponseString(response);

        try {
            userInfo.setDataFromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            userInfo.setAllParamsEmpty();
            e.printStackTrace();
        }

        return userInfo;
    }

    private static void waitResponse(Future response, int delaySec) throws TimeoutException {
        final byte secInMin = 60, defaultDelay = 100; // 100 - 0.1 секунда
        final Date date = new Date();
        int startTime = date.getSeconds() + date.getMinutes() * secInMin; // 60 сек
        while (!response.isDone()) {
            if (((date.getSeconds() + date.getMinutes() * secInMin) - startTime) > delaySec)
                throw new TimeoutException();
            try {
                Thread.sleep(defaultDelay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnect(Context context, String address, int port) throws TimeoutException {
        HOST = address;
        PORT = port;
        activityContext = context;
        return pushEmptyQuery();
    }

    public static boolean isConnect(Context context) throws TimeoutException {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        activityContext = context;
        return pushEmptyQuery();
    }

    private static boolean pushEmptyQuery() throws TimeoutException {
        if (executorService != null)
            executorService.shutdown();
        executorService = Executors.newFixedThreadPool(GlobalConstants.ONE);
        Future<String> response = executorService.submit(new Query(EMPTY_QUERY_ID));
        waitResponse(response, SHORT_RESPONSE_DELAY);
        return !getResponseString(response).isEmpty();
    }

    private static String getResponseString(Future<String> response) {
        try {
            return response.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return GlobalConstants.EMPTY_STRING;
    }

    public static void disconnect() {
        if (executorService != null) {
            executorService.submit(new Query(EXIT_QUERY_ID));
            executorService.shutdown();
        }
        TOKEN = GlobalConstants.EMPTY_STRING;
        executorService = null;
    }

    public static int getMightCode() {
        return TEACHER_CODE;
        // TODO понятие сущьности, под кем зашли
    }

    private static class Query implements Callable<String> {

        private final byte iDQuery;
        private Socket socket;
        private String[] values;

        public Query(byte iDQuery) {
            this.iDQuery = iDQuery;
        }

        public Query(byte iDQuery, String... values) {
            this.iDQuery = iDQuery;
            this.values = values;
        }

        public String call() throws Exception {
            return sendQuery(QueryManager.getQueryString(iDQuery, values));
        }

        private String sendQuery(String queryString) {

            StringBuilder result = new StringBuilder(GlobalConstants.EMPTY_STRING);

            try {
                socket = new Socket(HOST, PORT);

                PrintWriter query = new PrintWriter(socket.getOutputStream());
                query.print(queryString);
                query.println(HTTP_VERSION);
                query.println(HOST_INFO + DEFAULT_HOST);
                query.println(USER_CLIENT_INFO);
                query.flush();

                InputStreamReader iSReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(iSReader);

                for (String buffer; (buffer = bufferedReader.readLine()) != null; )
                    result.append(buffer);

                query.close();
                iSReader.close();
                bufferedReader.close();
                socket.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.substring(result.indexOf(QUERY_SUCCEEDED) + QUERY_SUCCEEDED.length());
        }
    }

    private static class QueryManager {

        private static final String AUTHORIZATION_METHOD = "/authentication/?";
        private static final String AUTHORIZATION_PARAM_ONE = "login=";
        private static final String AUTHORIZATION_PARAM_TWO = "&pass=";

        private static final String LOG_OUT_METHOD = "/logout/?";
        private static final String LOG_OUT_PARAM_ONE = "ssid=";

        private static final String PROFILE_METHOD = "/getProfile/?";
        private static final String PROFILE_PARAM_ONE = "ssid=";
        private static final String PROFILE_PARAM_TWO = "&user_id=";

        private static String[] valuesBuffer;

        private static String getQueryString(byte iDQuery, String... values) {
            valuesBuffer = values;
            switch (iDQuery) {
                case AUTHORIZATION_QUERY_ID:
                    return link(AUTHORIZATION_METHOD, AUTHORIZATION_PARAM_ONE, AUTHORIZATION_PARAM_TWO);
                case EXIT_QUERY_ID:
                    return link(LOG_OUT_METHOD, LOG_OUT_PARAM_ONE);
                case USER_INFO_QUERY_ID:
                    return DEFAULT_API_LINK + PROFILE_METHOD + PROFILE_PARAM_ONE + TOKEN + PROFILE_PARAM_TWO + MY_ID;
                case EMPTY_QUERY_ID:
                default:
                    return DEFAULT_API_LINK;
            }
        }

        private static String link(String method, String... params) {
            StringBuilder stringManager = new StringBuilder();
            stringManager.append(DEFAULT_API_LINK);
            stringManager.append(method);
            for (byte i = 0; i < params.length; i++) {
                stringManager.append(params[i]);
                if (params[i].equals(LOG_OUT_PARAM_ONE) || params[i].equals(PROFILE_PARAM_ONE))
                    stringManager.append(TOKEN);
                else
                    stringManager.append(valuesBuffer[i]);
            }
            return stringManager.toString();
        }
    }

}
