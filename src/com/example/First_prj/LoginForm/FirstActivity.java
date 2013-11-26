package com.example.First_prj.LoginForm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.First_prj.FirstActivitySettings.LoginFormSettingsActivity;
import com.example.First_prj.MenuLogicStarter.MenuLogicStarter;
import com.example.First_prj.R;

public class FirstActivity extends Activity implements View.OnClickListener {

    private String proxyAddress = "";
    private String proxyPort = "";
    private boolean isProxySet = false;
    private int mightCode;

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
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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

    private void buttonLogic() {
        // TODO сюда проверку подключения перед включением.
        lookingForProxy(); // берём настройки прокси.
        //if (isProxySeted) // TODO берём прокси и заходим
        // стартуем пока как учитель, всегда.
        if (true) {
            Intent intent = new Intent(this, MenuLogicStarter.class);
            //@TODO mightCode = Server.getMightCode(); // код указывающий могущество сущности
            mightCode = MenuLogicStarter.TEACHER;
            intent.putExtra("MightCode", mightCode);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Неверно введён логин или пароль", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    private void lookingForProxy() {
        SharedPreferences proxyInfo = getSharedPreferences("proxySettings", MODE_PRIVATE);
        if (!proxyInfo.getBoolean("CheckBoxValue", false)) return;
        proxyAddress = proxyInfo.getString("IP", "");
        proxyPort = proxyInfo.getString("Port", "");
        isProxySet = true;
    }

    private void closeVirtualKeyBoard() {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(mainWindow))
            closeVirtualKeyBoard();
    }
}
