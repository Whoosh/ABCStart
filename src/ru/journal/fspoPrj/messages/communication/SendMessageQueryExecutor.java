package ru.journal.fspoPrj.messages.communication;

import android.content.Intent;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class SendMessageQueryExecutor extends MainExecutor {

    private int requestCode;
    private String sendMessageQuery;

    public SendMessageQueryExecutor(int requestCode, String sendMessageQuery) {
        this.requestCode = requestCode;
        this.sendMessageQuery = sendMessageQuery;
        makeQuery(sendMessageQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();

        System.out.println(results.get(sendMessageQuery));
        
        result.putExtra(sendMessageQuery, results.get(sendMessageQuery));
        progressActivity.setResult(requestCode, result);
    }
}
