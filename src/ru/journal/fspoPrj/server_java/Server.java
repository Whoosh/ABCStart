package ru.journal.fspoPrj.server_java;

import ru.journal.fspoPrj.public_code.configs.GlobalConfig;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.server_java.might_info.MightInfo;
import ru.journal.fspoPrj.server_java.profile_info.ProfileInfo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.*;

public abstract class Server {

    private static final String DEFAULT_HOST = "fspo.segrys.ru";
    private static final byte DEFAULT_PORT = 80;

    private static final byte THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN = 5;

    private static final int THREAD_WAITING_DELAY = 100;
    private static final int SEC = 60;

    private static int PORT;
    private static String HOST;
    private static ExecutorService executorService;

    private static final String JSON_START_SCOPE = "{";
    private static final String TOKEN_TAG = "ssid";
    private static final String USER_ID_TAG = "user_id";

    private static final byte DEFAULT_WAIT_RESPONSE_DELAY = 3;
    private static final byte CHECKED_DELAY_RESPONSE = 1;

    private static String TOKEN = GlobalConfig.EMPTY_STRING;
    private static String MY_ID = GlobalConfig.EMPTY_STRING;

    public static void connect(String name, String password) throws TimeoutException {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        startServerConnection(name, password);
    }

    public static void connect(String name, String password, String proxyAddress, int port) throws TimeoutException {
        HOST = proxyAddress;
        PORT = port;
        startServerConnection(name, password);
    }

    public static boolean passwordIsWrong() {
        return TOKEN.length() < THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN;
    }

    private static void startServerConnection(String name, String password) throws TimeoutException {
        if (executorService != null) executorService.shutdown();
        executorService = Executors.newFixedThreadPool(GlobalConfig.ONE);
        Future<String> response = executorService.submit(new Query(APIQuery.AUTHORIZATION.getLink(name, password)));

        waitResponse(response, DEFAULT_WAIT_RESPONSE_DELAY);

        String jsonString = getResponseString(response);

        try {
            TOKEN = new JSONObject(jsonString).get(TOKEN_TAG).toString();
            MY_ID = new JSONObject(jsonString).get(USER_ID_TAG).toString();
            setMight();
        } catch (JSONException e) {
            System.err.println(e);
            throw new TimeoutException();
        }
    }

    private static void setMight() throws TimeoutException, JSONException {
        Future<String> response = executorService.submit(new Query(APIQuery.GET_MIGHT.getLink(TOKEN, MY_ID)));
        waitResponse(response, DEFAULT_WAIT_RESPONSE_DELAY);
        String jsonString = getResponseString(response);
        MightInfo.setDataFromJson(new JSONObject(jsonString));
    }

    public static void loadMyProfileInToProfileInfo() throws TimeoutException {
        Future<String> response = executorService.submit(new Query(APIQuery.GET_PROFILE.getLink(TOKEN, MY_ID)));
        requestProfile(response);
    }

    public static void loadAnyUserInfoInToProfileInfo(String userID) throws TimeoutException {
        Future<String> response = executorService.submit(new Query(APIQuery.GET_PROFILE.getLink(TOKEN, userID)));
        requestProfile(response);
    }

    private static void requestProfile(Future<String> response) throws TimeoutException {
        waitResponse(response, DEFAULT_WAIT_RESPONSE_DELAY);
        try {
            ProfileInfo.loadDataFromJson(new JSONObject(getResponseString(response)));
        } catch (JSONException e) {
            ProfileInfo.setProfileEmpty();
        }
    }

    private static void waitResponse(Future response, int delaySec) throws TimeoutException {
        int startTime = new Date().getSeconds() + new Date().getMinutes() * SEC;
        while (!response.isDone()) {
            if (((new Date().getSeconds() + new Date().getMinutes() * SEC) - startTime) > delaySec)
                throw new TimeoutException();
            try {
                Thread.sleep(THREAD_WAITING_DELAY);
            } catch (InterruptedException e) {
                throw new TimeoutException();
            }
        }
    }

    public static boolean isNotAlive(String address, int port) throws TimeoutException {
        HOST = address;
        PORT = port;
        return pushEmptyQuery();
    }

    public static boolean isNotAlive() throws TimeoutException {
        HOST = DEFAULT_HOST;
        PORT = DEFAULT_PORT;
        return pushEmptyQuery();
    }

    private static boolean pushEmptyQuery() throws TimeoutException {
        if (executorService != null) executorService.shutdown();
        executorService = Executors.newFixedThreadPool(GlobalConfig.ONE);

        Future<String> response = executorService.submit(new Query(APIQuery.EMPTY_QUERY.getLink()));
        waitResponse(response, CHECKED_DELAY_RESPONSE);

        return getResponseString(response).isEmpty();
    }

    private static String getResponseString(Future<String> response) {
        try {
            return response.get();
        } catch (InterruptedException | ExecutionException e) {
            return GlobalConfig.EMPTY_STRING;
        }
    }

    public static void disconnect() {
        if (executorService == null) return;

        executorService.submit(new Query(APIQuery.LOG_OUT.getLink(TOKEN)));
        executorService.shutdown();
        executorService = null;

        TOKEN = GlobalConfig.EMPTY_STRING;
    }

    private static class Query implements Callable<String> {

        private Socket socket;
        private String queryLink;


        public Query(String queryLink) {
            this.queryLink = queryLink;
        }

        @Override
        public String call() throws Exception {
            return sendQuery(queryLink);
        }

        private String sendQuery(String queryString) {

            StringBuilder result = new StringBuilder(GlobalConfig.EMPTY_STRING);

            try {
                socket = new Socket(HOST, PORT);

                PrintWriter query = new PrintWriter(socket.getOutputStream());
                query.print(queryString);
                query.println(ClientInfo.CLIENT_INFO.get());
                query.flush();

                InputStreamReader iSReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(iSReader);

                for (String buffer; (buffer = bufferedReader.readLine()) != null; ) result.append(buffer);

                query.close();
                iSReader.close();
                bufferedReader.close();
                socket.close();

            } catch (Exception e) {
                System.err.println(e);
            }
            return result.substring(result.indexOf(JSON_START_SCOPE));
        }
    }
}
