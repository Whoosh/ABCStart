package ru.journal.fspoPrj.messages.communication;

import android.content.Intent;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ChatMessagesExecutor extends MainExecutor {

    private int requestCode;
    private String chatMessageQuery;

    public ChatMessagesExecutor(int requestCode, String chatMessageQuery) {
        this.requestCode = requestCode;
        this.chatMessageQuery = chatMessageQuery;
        makeQuery(chatMessageQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(chatMessageQuery, results.get(chatMessageQuery));
        progressActivity.setResult(requestCode, result);
    }
}
