package ru.journal.fspoPrj.journal.data_get_managers.edit_visit_full;

import android.content.Intent;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_info.CommunicationInfo;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class TeacherVisitsExecutor extends MainExecutor {

    private final int resultCode;
    private String journalQuery;

    public TeacherVisitsExecutor(String query, int resultCode) {
        this.resultCode = resultCode;
        journalQuery = query;
        super.makeQuery(journalQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent intent = new Intent();
        intent.putExtra(journalQuery, new TeacherVisits(results.get(journalQuery)));
        progressActivity.setResult(resultCode, intent);
    }
}
