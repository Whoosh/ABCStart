package ru.journal.fspoPrj.server_java.server_managers;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import ru.journal.fspoPrj.login_form.data_get_managers.AuthorizationExecutor;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.server_info.ServerErrors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;


public abstract class MainExecutor extends AsyncTask<String, Integer, Void> implements Serializable {

    protected static final int DEFAULT_WAIT_TIME = 10;

    private static final int SHOW_PROGRESS_CODE = 0;
    private static final int STOP_EXECUTING_CODE = 1;
    private static final int SHOW_SERVER_MESSAGE_TTL_CODE = 2;
    private static final int SHOW_SERVER_MESSAGE_DIE_CODE = 3;
    private static final int PASSWORD_IS_WRONG_CODE = 4;

    protected ProgressActivity progressActivity;

    private static Toast errorMessageShower;
    private static ExecutorService executorService;
    private static HashMap<String, Future<String>> futureResponsesStorage;
    private static HashMap<String, String> resultsStorage;

    static {
        executorService = Executors.newCachedThreadPool();
        futureResponsesStorage = new HashMap<>();
        resultsStorage = new HashMap<>();
    }

    public void restoreLinkToThisActivity(ProgressActivity progressActivity) {
        this.progressActivity = progressActivity;
    }

    public void dropLinkToThisActivity() {
        progressActivity = null;
    }

    @Override
    protected void onPreExecute() {
        publishProgress(SHOW_PROGRESS_CODE);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            doExecute();
        } catch (ExecutionException e) {
            publishProgress(SHOW_SERVER_MESSAGE_DIE_CODE);
        } catch (InterruptedException | TimeoutException e) {
            publishProgress(SHOW_SERVER_MESSAGE_TTL_CODE);
        } catch (AuthorizationExecutor.WrongPasswordException e) {
            publishProgress(PASSWORD_IS_WRONG_CODE);
        } catch (Exception e) {
            // There is error if app is not closed manual can be ignored
            Logger.printError(e, getClass());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        switch (values[0]) {
            case SHOW_PROGRESS_CODE: {
                setProgressStatus(true);
                break;
            }
            case SHOW_SERVER_MESSAGE_TTL_CODE: {
                showErrorMessage(ServerErrors.SERVER_TTL_QUERY_ERROR);
                stopThisOperation();
                return;
            }
            case SHOW_SERVER_MESSAGE_DIE_CODE: {
                showErrorMessage(ServerErrors.SERVER_IS_DOWN);
                stopThisOperation();
                return;
            }
            case PASSWORD_IS_WRONG_CODE: {
                showErrorMessage(ServerErrors.LOGIN_OR_PASSWORD_ERROR);
            }
            case STOP_EXECUTING_CODE: {
                stopThisOperation();
            }
        }
    }

    @Override
    protected void onPostExecute(Void res) {
        resultsStorage.clear();
        futureResponsesStorage.clear();
        setProgressStatus(false);
        progressActivity.finish();
        super.onPostExecute(res);
    }

    protected abstract void queryResults(HashMap<String, String> results)
            throws InterruptedException, ExecutionException, AuthorizationExecutor.WrongPasswordException, TimeoutException;

    protected void doExecute()
            throws AuthorizationExecutor.WrongPasswordException, InterruptedException, ExecutionException, TimeoutException {

        for (Map.Entry<String, Future<String>> query : futureResponsesStorage.entrySet()) {
            resultsStorage.put(query.getKey(), query.getValue().get(DEFAULT_WAIT_TIME, TimeUnit.SECONDS));
        }

        futureResponsesStorage.clear();
        queryResults(resultsStorage);
    }

    protected void makeQuery(String queryLink) {
        futureResponsesStorage.put(queryLink, executorService.submit(new Query(queryLink)));
    }

    private void showErrorMessage(ServerErrors error) {
        if (errorMessageShower == null) {
            errorMessageShower = Toast.makeText(progressActivity, error.message(), Toast.LENGTH_SHORT);
        } else {
            errorMessageShower.setText(error.message());
        }
        errorMessageShower.show();
    }

    protected void stopThisOperation() {
        setProgressStatus(false);
        progressActivity.setResult(ServerCommunicator.RESULT_FAIL);
        progressActivity.finish();
    }

    private void setProgressStatus(Boolean progressStatus) {
        try {
            progressActivity.setVisibleStatus(progressStatus ? View.VISIBLE : View.INVISIBLE);
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }
}