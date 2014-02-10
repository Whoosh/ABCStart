package ru.journal.fspoPrj.journal.data_get_managers.groups_list;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class GroupsListExecutor extends MainExecutor {

    private int caller;
    private String queryKey;

    public GroupsListExecutor(String query, int resultCode) {
        this.caller = resultCode;
        this.queryKey = query;
        super.makeQuery(query);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(queryKey, new GroupsList(results.remove(queryKey)));
        progressActivity.setResult(caller, result);
        progressActivity.finish();
    }
}
