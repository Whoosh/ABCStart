package ru.journal.fspoPrj.schedule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.schedule.comunicators.ScheduleCommunicator;
import ru.journal.fspoPrj.server_java.server_managers.ServerCommunicator;

public class ScheduleActivity extends Activity {

    private static ScheduleCommunicator sC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (sC == null) {
            sC = new ScheduleCommunicator(this);
        }
        setContentView(R.layout.schedule_main_lay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ServerCommunicator.RESULT_FAIL) {
            finish();
        } else if (data == null) return;

        switch (resultCode) {
            case ScheduleCommunicator.SCHEDULES_QUERY: {
                sC.cacheData(data, resultCode);
            }
            break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
