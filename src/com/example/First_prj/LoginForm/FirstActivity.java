package com.example.First_prj.LoginForm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.example.First_prj.FirstActivitySettings.LoginFormSettingsActivity;
import com.example.First_prj.FirstActivitySettings.SurfaceBackground;
import com.example.First_prj.MenuLogicStarter.MenuLogicStarter;
import com.example.First_prj.R;

public class FirstActivity extends Activity implements View.OnClickListener {

    private View mainLayout;
    private Button enterButton;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private CheckBox rememberMeCheckBox;
    private String userName = "";
    private String password = "";
    private String proxyAddress = "";
    private String proxyPort = "";
    private boolean isProxySet = false;
    private SharedPreferences keyValueStorage;
    private int mightCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        setIDForAllElement();
        setListenerForAllElement();
        loadInfoFromStorage();
    }

    @Override
    protected void onPause() {
        saveInfoFromForm();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onResume() {
          loadInfoFromStorage();
        super.onResume();
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


    // @TODO можно запилить обработку форм
    public void onClick(View elements) {
        switch (elements.getId()) {
            case R.id.enterButton:
                buttonLogic();
                break;
            case R.id.mainLayout:
                closeVirtualKeyBoard();
                break;
        }
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

    private void saveInfoFromForm() {
        keyValueStorage = getSharedPreferences("UserSettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValueStorage.edit();
        editor.putString("UserName", userNameEditText.getText().toString());
        editor.putString("UserPassword", passwordEditText.getText().toString());
        editor.putBoolean("SaveMeCheckBox", rememberMeCheckBox.isChecked());
        editor.commit();
    }


    private void loadInfoFromStorage() {
        keyValueStorage = getSharedPreferences("UserSettings", MODE_PRIVATE);
        if (keyValueStorage.getBoolean("SaveMeCheckBox", false)) {
            userNameEditText.setText(keyValueStorage.getString("UserName", ""));
            passwordEditText.setText(keyValueStorage.getString("UserPassword", ""));
            rememberMeCheckBox.setChecked(true);
        }
    }


    private void closeVirtualKeyBoard() {
        ((InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setListenerForAllElement() {
        this.enterButton.setOnClickListener(this);
        this.rememberMeCheckBox.setOnClickListener(this);
        this.mainLayout.setOnClickListener(this);
    }

    private void setIDForAllElement() {
        this.enterButton = (Button) findViewById(R.id.enterButton);
        this.passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        this.userNameEditText = (EditText) findViewById(R.id.nameEditText);
        this.rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);
        this.mainLayout = findViewById(R.id.mainLayout);
    }


}
