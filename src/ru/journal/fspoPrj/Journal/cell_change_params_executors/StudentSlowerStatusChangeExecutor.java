package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentSlowerStatusChangeExecutor extends MainExecutor{
    private String studentSlowerStatusChange;
    private final int resultCode;

    public StudentSlowerStatusChangeExecutor(String studentSlowerStatusChange, int resultCode) {
        this.resultCode = resultCode;
        this.studentSlowerStatusChange = studentSlowerStatusChange;
        makeQuery(studentSlowerStatusChange);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(studentSlowerStatusChange, results.get(studentSlowerStatusChange));
        progressActivity.setResult(resultCode, result);
    }
}
