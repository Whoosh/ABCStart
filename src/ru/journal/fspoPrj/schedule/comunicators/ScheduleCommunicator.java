package ru.journal.fspoPrj.schedule.comunicators;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.schedule.executors.ScheduleExecutor;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

import java.text.DateFormat;
import java.util.*;

public class ScheduleCommunicator extends ServerCommunicator {

    public final static int SCHEDULES_QUERY = 1;

    private String dataKey = "hmm";
    private Activity caller;

    public ScheduleCommunicator(Activity caller) {
        this.caller = caller;
        sendScheduleRequest();

    }

    private void sendScheduleRequest() {
        lastQueryID = SCHEDULES_QUERY;
        super.sendQueryToServer(caller, makeExecutor());
    }

    protected MainExecutor makeExecutor() {
        switch (lastQueryID) {
            case SCHEDULES_QUERY: {
                return new ScheduleExecutor(dataKey, lastQueryID, getToken(), getMyID());
            }
        };
        return null;
    }

    public void cacheData(Intent data, int resultID) {
        switch (resultID) {
            case SCHEDULES_QUERY: {
                System.out.println(data.getStringExtra(dataKey));
                data.removeExtra(dataKey);
            }
            break;
        }
    }

}
