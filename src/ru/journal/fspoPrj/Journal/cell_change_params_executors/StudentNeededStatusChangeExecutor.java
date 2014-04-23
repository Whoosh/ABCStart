package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentNeededStatusChangeExecutor extends MainExecutor {

    private String studentNeededStatusChange;
    private final int resultCode;

    public StudentNeededStatusChangeExecutor(String studentNeededStatusChange, int resultCode) {
        this.resultCode = resultCode;
        this.studentNeededStatusChange = studentNeededStatusChange;
        makeQuery(studentNeededStatusChange);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(studentNeededStatusChange, results.get(studentNeededStatusChange));
        progressActivity.setResult(resultCode, result);
    }
}
