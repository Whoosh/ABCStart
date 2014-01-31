package ru.journal.fspoPrj.server_java.server_managers;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import ru.journal.fspoPrj.login_form.query_manager.AuthorizationExecutor;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.Server;

public class ProgressActivity extends Activity {

    private ProgressBar progressBar;
    private MainExecutor linkOnExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linkOnExecutor = (MainExecutor) getLastNonConfigurationInstance();
        if (progressBar == null) {
            postElementsOnScreen();
        }
        if (linkOnExecutor == null) {
            startNewExecuting();
        }
        linkOnExecutor.setOldLinkChain(this);
    }

    private void startNewExecuting() {
        try {
            linkOnExecutor = (MainExecutor) this.getIntent().getSerializableExtra(Server.EXECUTOR_TAG);
            linkOnExecutor.execute();
        } catch (NullPointerException ex) {
            Logger.printError(ex, getClass());
        }
    }

    private void postElementsOnScreen() {
        progressBar = new ProgressBar(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setGravity(Gravity.CENTER);
        layout.addView(progressBar);
        layout.setBackgroundColor(Color.TRANSPARENT);
        setContentView(layout);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        linkOnExecutor.dropLinkChain();
        return linkOnExecutor;
    }

    public void setVisibleStatus(int status) {
        progressBar.setVisibility(status);
    }

    @Override
    protected void onDestroy() {
        Server.setMakingQueryNow(false);
        super.onDestroy();
    }
}
