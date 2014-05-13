package ru.journal.fspoPrj.messages.communication;

import android.content.Intent;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class MessageExecutor extends MainExecutor {

    private String myMessageQuery;
    private int resultCode;

    public MessageExecutor(String myMessageQuery, int resultCode) {
        this.myMessageQuery = myMessageQuery;
        this.resultCode = resultCode;
        super.makeQuery(myMessageQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(myMessageQuery, results.get(myMessageQuery));
        progressActivity.setResult(resultCode, result);
    }
}
