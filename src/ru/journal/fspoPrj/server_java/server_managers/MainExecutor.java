package ru.journal.fspoPrj.server_java.server_managers;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import ru.journal.fspoPrj.login_form.query_manager.AuthorizationExecutor;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.server_java.storage.CachedStorage;
import ru.journal.fspoPrj.server_java.server_info.ServerErrors;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.*;

public class MainExecutor extends AsyncTask<String, Integer, Void> implements Serializable {

    private static final int SHOW_PROGRESS_CODE = 0;
    private static final int STOP_EXECUTING_CODE = 1;
    private static final int SHOW_SERVER_MESSAGE_TTL_CODE = 2;
    private static final int SHOW_SERVER_MESSAGE_DIE_CODE = 3;
    private static final int PASSWORD_IS_WRONG_CODE = 4;

    protected static final int WAIT_TIME = 10;
    protected String queryLink = "";

    private ProgressActivity progressActivity;
    private static Toast errorMessageShower;

    public void setOldLinkChain(ProgressActivity progressActivity) {
        this.progressActivity = progressActivity;
    }

    public void dropLinkChain() {
        progressActivity = null;
    }

    @Override
    protected Void doInBackground(String... params) {
        publishProgress(SHOW_PROGRESS_CODE);
        try {
            handleQuery();
            publishProgress(STOP_EXECUTING_CODE);
        } catch (ExecutionException e) {
            publishProgress(SHOW_SERVER_MESSAGE_DIE_CODE);
        } catch (InterruptedException | TimeoutException e) {
            publishProgress(SHOW_SERVER_MESSAGE_TTL_CODE);
        } catch (AuthorizationExecutor.WrongPasswordException e) {
            publishProgress(PASSWORD_IS_WRONG_CODE);
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
                Server.setExecutingErrorStatus(true);
                progressActivity.finish();
                break;
            }
            case SHOW_SERVER_MESSAGE_DIE_CODE: {
                showErrorMessage(ServerErrors.SERVER_IS_DOWN);
                Server.setExecutingErrorStatus(true);
                progressActivity.finish(); // TODO
                break;
            }
            case STOP_EXECUTING_CODE: {
                setProgressStatus(false);
                progressActivity.finish();
                Server.setExecutingErrorStatus(false);
                break;
            }
            case PASSWORD_IS_WRONG_CODE: {
                showErrorMessage(ServerErrors.LOGIN_OR_PASSWORD_ERROR);
                Server.setExecutingErrorStatus(true);
                progressActivity.finish();
                break;
            }
        }
    }

    protected void handleQuery() throws InterruptedException, ExecutionException, TimeoutException, WrongPasswordException {
        handlingAllFutures();
    }

    protected void handlingAllFutures() throws InterruptedException, ExecutionException, TimeoutException, WrongPasswordException {
        try {
            for (Map.Entry<String, Future<String>> futureRequests : CachedStorage.getFutureResponses()) {
                CachedStorage.saveResponse(futureRequests.getKey(), futureRequests.getValue().get(WAIT_TIME, TimeUnit.SECONDS));
            }
        } catch (NullPointerException e) {
            Logger.printError(e, getClass());
        }
        CachedStorage.dropFutureResponses();
    }

    private void showErrorMessage(ServerErrors error) {
        if (errorMessageShower == null) {
            errorMessageShower = Toast.makeText(progressActivity, error.message(), Toast.LENGTH_SHORT);
        } else {
            errorMessageShower.setText(error.message());
        }
        errorMessageShower.show();
    }

    private void setProgressStatus(Boolean progressStatus) {
        try {
            progressActivity.setVisibleStatus(progressStatus ? View.VISIBLE : View.INVISIBLE);
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }

    public class WrongPasswordException extends Throwable {
        public WrongPasswordException() {
            CachedStorage.clearToken();
        }
    }
}