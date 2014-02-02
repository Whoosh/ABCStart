package ru.journal.fspoPrj.server_java.server_managers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.ServerCommunicator;

public class ProgressActivity extends Activity {

    private ProgressBar progressBar;
    private MainExecutor oldExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (progressBar == null) createNewProgress();
        oldExecutor = getLastNonConfigurationInstance();
        if (oldExecutor == null) startNewExecuting();
        oldExecutor.restoreLinkToThisActivity(this);
    }

    public void setVisibleStatus(int status) {
        progressBar.setVisibility(status);
    }

    @Nullable
    @Override
    public MainExecutor getLastNonConfigurationInstance() {
        return (MainExecutor) super.getLastNonConfigurationInstance();
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        oldExecutor.dropLinkToThisActivity();
        return oldExecutor;
    }

    private void createNewProgress() {
        progressBar = new ProgressBar(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setGravity(Gravity.CENTER);
        layout.addView(progressBar);
        layout.setBackgroundColor(Color.TRANSPARENT);
        setContentView(layout);
    }

    private void startNewExecuting() {
        try {
            Intent intent = this.getIntent();
            oldExecutor = (MainExecutor) intent.getSerializableExtra(ServerCommunicator.SERVER_COMMUTATION_KEY);
            intent.removeExtra(ServerCommunicator.SERVER_COMMUTATION_KEY);
            oldExecutor.execute();
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }
}
