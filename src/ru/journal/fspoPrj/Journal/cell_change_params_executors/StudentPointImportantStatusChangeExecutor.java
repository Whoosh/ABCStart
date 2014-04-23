package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentPointImportantStatusChangeExecutor extends MainExecutor{
    private String studentPointImportantStatusChange;
    private final int resultCode;

    public StudentPointImportantStatusChangeExecutor(String studentPointImportantStatusChange, int resultCode) {
        this.resultCode = resultCode;
        this.studentPointImportantStatusChange = studentPointImportantStatusChange;
        makeQuery(studentPointImportantStatusChange);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(studentPointImportantStatusChange, results.get(studentPointImportantStatusChange));
        progressActivity.setResult(resultCode, result);
    }
}
