package com.example.First_prj.LoginForm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import com.example.First_prj.FirstActivitySettings.LoginFormSettingsActivity;
import com.example.First_prj.JavaServer.Server;
import com.example.First_prj.R;

public class FirstActivity extends Activity implements View.OnClickListener {

    private MainWindow mainWindow;
    private ActionMode settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mainWindow = new MainWindow(this);
        mainWindow.setOnClickListener(this);
        setContentView(mainWindow);

        startActionMode(settingsBar);
    }

    @Override
    protected void onPause() {
        mainWindow.saveWindowInfo();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Server.disconnect();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Server.disconnect();
        mainWindow.loadWindowInfo();
        mainWindow.restoreVisualElementState();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("UserName", mainWindow.getUserName());
        outState.putString("Password", mainWindow.getPassword());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mainWindow.setUserName(savedInstanceState.get("UserName").toString());
        mainWindow.setPassword(savedInstanceState.get("Password").toString());
    }

    private ActionMode.Callback settingsBar = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.add("Настройки");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            mode.finish();
            startActivity(new Intent(getApplicationContext(), LoginFormSettingsActivity.class));
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            settings = null;
            mode.finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (settings == null) {
            settings = startActionMode(settingsBar);
        } else {
            settings.finish();
            settings = null;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mainWindow)) {
            try {// закрываем виртуальную клавиатуру по клику на пустое место.
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }

    }
}
