package ru.journal.fspoPrj.journal.action_crud;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class UpdateTypeExerciseExecutor extends MainExecutor {

    private final int resultCode;
    private String updateExerciseQuery;

    public UpdateTypeExerciseExecutor(String query, int resultCode) {
        this.resultCode = resultCode;
        updateExerciseQuery = query;
        super.makeQuery(updateExerciseQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(updateExerciseQuery, results.get(updateExerciseQuery));
        progressActivity.setResult(resultCode, result);
    }
}
