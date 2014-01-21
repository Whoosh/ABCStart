package ru.journal.fspoPrj.server_java.server_managers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class Progress extends Activity {

    private ProgressBar progressBar;
    private TaskExecutor linkOnExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linkOnExecutor = (TaskExecutor) getLastNonConfigurationInstance();
        if(progressBar == null){
            postElementsOnScreen();
        }
        if (linkOnExecutor == null) {
            startNewExecuting();
        }
        linkOnExecutor.setOldLinkChain(this);
    }

    private void startNewExecuting() {
        linkOnExecutor = new TaskExecutor();
        linkOnExecutor.execute();
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

}
