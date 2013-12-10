package com.example.First_prj.JavaServer;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.*;

public class Server {

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

    private static final byte DEFAULT_WAIT_RESPONSE_DELAY = 5;
    private static final byte SHORT_RESPONSE_DELAY = 1;

    private static String TOKEN = " ";

    private static byte indexer;

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
        return TOKEN.length() > 5;
    }

    private static void startServerConnection(Context context, String name, String password) throws TimeoutException {

        if (executorService != null)
            executorService.shutdown();

        executorService = Executors.newFixedThreadPool(1); // 1 поток на исполнение
        activityContext = context;

        Future<String> response = executorService.submit(new Query(AUTHORIZATION_QUERY_ID, new String[]{name, password}));

        waitResponse(response, DEFAULT_WAIT_RESPONSE_DELAY);

        String jsonString = getResponseString(response);

        try {
            TOKEN = new JSONObject(jsonString).get(TOKEN_TAG).toString();
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
        int startTime = new Date().getSeconds() + new Date().getMinutes() * 60;
        while (!response.isDone()) {
            if (((new Date().getSeconds() + new Date().getMinutes() * 60) - startTime) > delaySec)
                throw new TimeoutException();
            try {
                Thread.sleep(100);
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
        executorService = Executors.newFixedThreadPool(1);
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
        return "";
    }

    public static void disconnect() {
        if (executorService != null) {
            executorService.submit(new Query(EXIT_QUERY_ID));
            executorService.shutdown();
        }
        TOKEN = "";
        executorService = null;
    }

    private static class Query implements Callable<String> {

        private final byte iDQuery;
        private Socket socket;
        private String[] params;

        public Query(byte iDQuery) {
            this.iDQuery = iDQuery;
        }

        public Query(byte iDQuery, String[] params) {
            this.iDQuery = iDQuery;
            this.params = params;
        }

        public String call() throws Exception {
            return sendQuery(QueryManager.getQueryString(iDQuery, params));
        }

        private String sendQuery(String queryString) {

            StringBuilder result = new StringBuilder("");

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
        // @TODO стоит ли обернуть в enum ?
        // @TODO подумать...
        private static String getQueryString(byte iDQuery, String[] params) {
            switch (iDQuery) {
                case AUTHORIZATION_QUERY_ID:
                    return DEFAULT_API_LINK + "/authentication/?login=" + params[0] + "&pass=" + params[1];
                case EXIT_QUERY_ID:
                    return DEFAULT_API_LINK + "/logout/?ssid=" + TOKEN;
                case USER_INFO_QUERY_ID:
                    return DEFAULT_API_LINK + "/getprofile/?ssid=" + TOKEN;
                case EMPTY_QUERY_ID:
                default:
                    return DEFAULT_API_LINK;
            }
        }
    }

}
