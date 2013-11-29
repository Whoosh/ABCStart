package com.example.First_prj.LoginForm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import com.example.First_prj.FirstActivitySettings.LoginFormSettingsActivity;
import com.example.First_prj.R;

public class FirstActivity extends Activity implements View.OnClickListener {

    private MainWindow mainWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mainWindow = new MainWindow(this);
        mainWindow.setOnClickListener(this);
        setContentView(mainWindow);
    }

    @Override
    protected void onPause() {
        mainWindow.saveWindowInfo();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.loginmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.loginSettingsMenu:
                startActivity(new Intent(this, LoginFormSettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mainWindow)) {
            try {// закрываем виртуальную клавиатуру по клику на пустое место.
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (NullPointerException ex) {
            }
        }

    }
}
