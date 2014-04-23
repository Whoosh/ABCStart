package ru.journal.fspoPrj.journal.action_crud;

import android.content.Intent;
import ru.journal.fspoPrj.journal.data_get_managers.edit_visit_full.TeacherVisits;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class DeleteExerciseExecutor extends MainExecutor {

    private final int resultCode;
    private String deleteExerciseQuery;

    public DeleteExerciseExecutor(String query, int resultCode) {
        this.resultCode = resultCode;
        deleteExerciseQuery = query;
        super.makeQuery(deleteExerciseQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(deleteExerciseQuery, results.get(deleteExerciseQuery));
        progressActivity.setResult(resultCode, result);
        System.out.println(results.get(deleteExerciseQuery));
    }
}
