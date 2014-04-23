package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentExclusivePointStateExecutor extends MainExecutor {

    private String studentExclusivePointStatusQuery;
    private final int resultCode;

    public StudentExclusivePointStateExecutor(String studentExclusivePointStatusQuery, int resultCode) {
        this.resultCode = resultCode;
        this.studentExclusivePointStatusQuery = studentExclusivePointStatusQuery;
        makeQuery(studentExclusivePointStatusQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(studentExclusivePointStatusQuery, results.get(studentExclusivePointStatusQuery));
        progressActivity.setResult(resultCode, result);
    }

}
