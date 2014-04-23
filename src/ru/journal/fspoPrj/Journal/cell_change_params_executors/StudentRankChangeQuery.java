package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentRankChangeQuery extends MainExecutor {
    private final int resultCode;
    private String rankChangeQuery;

    public StudentRankChangeQuery(String rankChangeQuery, int resultCode) {
        this.resultCode = resultCode;
        this.rankChangeQuery = rankChangeQuery;
        makeQuery(rankChangeQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(rankChangeQuery, results.get(rankChangeQuery));
        progressActivity.setResult(resultCode, result);
    }
}
