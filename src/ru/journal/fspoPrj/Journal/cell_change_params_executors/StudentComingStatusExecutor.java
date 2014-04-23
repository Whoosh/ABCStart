package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentComingStatusExecutor extends MainExecutor {

    private String studentComingParamChangeQuery;
    private final int resultCode;

    public StudentComingStatusExecutor(String studentComingParamChangeQuery, int resultCode) {
        this.resultCode = resultCode;
        this.studentComingParamChangeQuery = studentComingParamChangeQuery;
        makeQuery(studentComingParamChangeQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(studentComingParamChangeQuery, results.get(studentComingParamChangeQuery));
        progressActivity.setResult(resultCode, result);
    }
}
