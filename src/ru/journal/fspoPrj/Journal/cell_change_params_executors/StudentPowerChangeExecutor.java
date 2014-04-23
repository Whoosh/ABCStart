package ru.journal.fspoPrj.journal.cell_change_params_executors;

import android.content.Intent;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class StudentPowerChangeExecutor extends MainExecutor {

    private final int resultCode;
    private String powerQuery;

    public StudentPowerChangeExecutor(String powerQuery, int resultCode) {
        this.resultCode = resultCode;
        this.powerQuery = powerQuery;
        makeQuery(powerQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(powerQuery, results.get(powerQuery));
        progressActivity.setResult(resultCode,result);
    }
}
