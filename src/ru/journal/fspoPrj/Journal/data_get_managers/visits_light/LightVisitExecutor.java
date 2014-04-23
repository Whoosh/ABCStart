package ru.journal.fspoPrj.journal.data_get_managers.visits_light;

import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class LightVisitExecutor extends MainExecutor {

    private final int caller;
    private String lightVisitQuery;

    public LightVisitExecutor(String lightVisitQuery, int caller) {
        this.lightVisitQuery = lightVisitQuery;
        this.caller = caller;
        super.makeQuery(lightVisitQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent intent = new Intent();
        intent.putExtra(lightVisitQuery, new LightVisits(results.remove(lightVisitQuery), false));
        progressActivity.setResult(caller, intent);
    }
}
