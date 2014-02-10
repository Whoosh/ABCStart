package ru.journal.fspoPrj.server_java.server_managers;

import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_info.ClientInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

public class Query implements Callable<String> {

    private static final String JSON_START_SCOPE = "{";
    private static final String EMPTY = "";

    private String queryLink;

    public Query(String queryLink) {
        this.queryLink = queryLink;
    }

    @Override
    public String call() throws Exception {
        return sendQuery(queryLink);
    }

    private String sendQuery(String queryString) {

        StringBuilder result = new StringBuilder(EMPTY);

        try {
            Socket socket = new Socket(ServerCommunicator.HOST, ServerCommunicator.PORT);
            PrintWriter query = new PrintWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            query.print(queryString);
            query.println(ClientInfo.CLIENT_INFO.get());
            query.flush();

            for (String buffer; (buffer = bufferedReader.readLine()) != null; ) result.append(buffer);

            query.close();
            bufferedReader.close();
            socket.close();
        } catch (IOException e) {
            Logger.printError(e, getClass());
        }
        return result.substring(result.indexOf(JSON_START_SCOPE));
    }
}