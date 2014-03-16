package ru.journal.fspoPrj.schedule.executors;

import android.content.Intent;
import ru.journal.fspoPrj.public_code.humans_entity.ProfileInfo;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class ScheduleExecutor extends MainExecutor {

    private final int resultID;
    private final String dataKey;
    private String profileQueryKey;
    private ProfileInfo im;

    public ScheduleExecutor(String dataKey, int resultID, String token, String myID) {
        this.resultID = resultID;
        this.dataKey = dataKey;
        this.profileQueryKey = APIQuery.GET_PROFILE.getLink(token, myID);
        super.makeQuery(profileQueryKey);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        System.out.println(results.get(profileQueryKey));
        returnResult();
    }

    private void returnResult() {
        Intent result = new Intent();
        result.putExtra(dataKey, dataKey);
        progressActivity.setResult(resultID, result);
    }
}
