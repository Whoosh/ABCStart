package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentRemoveStatusPointExecutor extends MainExecutor {
    private final int resultCode;
    private String removePointStatusQuery;

    public StudentRemoveStatusPointExecutor(String removePointStatusQuery, int resultCode) {
        this.resultCode = resultCode;
        this.removePointStatusQuery = removePointStatusQuery;
        makeQuery(removePointStatusQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(removePointStatusQuery, results.get(removePointStatusQuery));
        progressActivity.setResult(resultCode, result);
    }
}
