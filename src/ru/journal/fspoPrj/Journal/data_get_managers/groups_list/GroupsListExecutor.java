package ru.journal.fspoPrj.journal.data_get_managers.groups_list;

import android.content.Intent;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class GroupsListExecutor extends MainExecutor {

    private int resultCode;
    private final String groupListQuery;
    private final String lessonsListQuery;

    public GroupsListExecutor(String groupListQuery, String lessonsListQuery, int resultCode) {
        this.resultCode = resultCode;
        this.groupListQuery = groupListQuery;
        this.lessonsListQuery = lessonsListQuery;
        super.makeQuery(groupListQuery);
        super.makeQuery(lessonsListQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        result.putExtra(groupListQuery, new GroupsList(results.remove(groupListQuery), results.remove(lessonsListQuery)));
        progressActivity.setResult(resultCode, result);
        progressActivity.finish();
    }
}
