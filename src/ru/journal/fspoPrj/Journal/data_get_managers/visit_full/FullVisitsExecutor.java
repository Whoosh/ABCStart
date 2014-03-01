package ru.journal.fspoPrj.journal.data_get_managers.visit_full;

import android.content.Intent;
import android.widget.CheckBox;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class FullVisitsExecutor extends MainExecutor{

    private String fullVisitsKey;
    private int resultCode;

    public FullVisitsExecutor(String fullVisitsKey, int resultCode) {
        this.fullVisitsKey = fullVisitsKey;
        this.resultCode = resultCode;
        super.makeQuery(fullVisitsKey);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, AuthorizationExecutor.WrongPasswordException, TimeoutException {
        System.out.println(results.get(fullVisitsKey));
    }
}
