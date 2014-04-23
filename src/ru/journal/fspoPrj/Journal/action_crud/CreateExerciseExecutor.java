package ru.journal.fspoPrj.journal.action_crud;

import android.content.Intent;
import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.Visit;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class CreateExerciseExecutor extends MainExecutor {

    private final int resultCode;
    private String createExerciseQuery;

    public CreateExerciseExecutor(String query, int resultCode) {
        this.resultCode = resultCode;
        createExerciseQuery = query;
        super.makeQuery(createExerciseQuery);
    }

    @Override
    protected void queryResults(HashMap<String, String> results) throws InterruptedException, ExecutionException, TimeoutException {
        Intent result = new Intent();
        try {
            result.putExtra(createExerciseQuery, new LightExercisesInfo(new JSONObject(results.get(createExerciseQuery)),
                    LightExercisesInfo.TypeState.NORMAL_LESSON.ordinal()));
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
        progressActivity.setResult(resultCode, result);
    }
}
