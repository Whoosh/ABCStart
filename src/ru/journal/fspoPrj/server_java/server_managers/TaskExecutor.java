package ru.journal.fspoPrj.server_java.server_managers;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.storage.CachedStorage;
import ru.journal.fspoPrj.server_java.server_info.ServerErrors;

import java.util.concurrent.*;

class TaskExecutor extends AsyncTask<String, Integer, Void> {

    private static final int SHOW_PROGRESS_CODE = 0;
    private static final int STOP_EXECUTING_CODE = 1;
    private static final int SHOW_SERVER_MESSAGE_TTL_CODE = 2;
    private static final int SHOW_SERVER_MESSAGE_DIE_CODE = 3;

    private static final int WAIT_TIME = 10;

    private Progress progress;
    private static Toast errorMessageShower;

    public void setOldLinkChain(Progress progress) {
        this.progress = progress;
    }

    public void dropLinkChain() {
        progress = null;
    }

    @Override
    protected Void doInBackground(String... params) {
        publishProgress(SHOW_PROGRESS_CODE);
        try {
            loadDataFromServerToStorage();
        } catch (ExecutionException e) {
            publishProgress(SHOW_SERVER_MESSAGE_DIE_CODE);
        } catch (InterruptedException | TimeoutException e) {
            publishProgress(SHOW_SERVER_MESSAGE_TTL_CODE);
        }
        publishProgress(STOP_EXECUTING_CODE);
        CachedStorage.dropFutureResponsesQuery();
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        switch (values[0]) {
            case STOP_EXECUTING_CODE: {
                progress.finish();
                setProgressStatus(false);
                break;
            }
            case SHOW_PROGRESS_CODE: {
                setProgressStatus(true);
                break;
            }
            case SHOW_SERVER_MESSAGE_TTL_CODE: {
                showErrorMessage(ServerErrors.SERVER_TTL_QUERY_ERROR);
                break;
            }
            case SHOW_SERVER_MESSAGE_DIE_CODE: {
                showErrorMessage(ServerErrors.SERVER_IS_DOWN);
                break;
            }
        }
    }

    private void loadDataFromServerToStorage() throws InterruptedException, ExecutionException, TimeoutException {
        try {
            for (ConcurrentHashMap.Entry<String, Future<String>> query : CachedStorage.getFutureResponses()) {
                CachedStorage.saveResponse(query.getKey(), query.getValue().get(WAIT_TIME, TimeUnit.SECONDS));
            }
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }

    private void showErrorMessage(ServerErrors error) {
        if (errorMessageShower == null) {
            errorMessageShower = Toast.makeText(progress, error.message(), Toast.LENGTH_SHORT);
        } else {
            errorMessageShower.setText(error.message());
        }
        errorMessageShower.show();
    }

    private void setProgressStatus(Boolean progressStatus) {
        try {
            progress.setVisibleStatus(progressStatus ? View.VISIBLE : View.INVISIBLE);
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }
}