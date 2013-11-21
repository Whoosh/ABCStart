package com.example.First_prj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.*;


public class FirstActivity extends Activity implements View.OnClickListener {

    private View mainLayout;
    private Button enterButton;
    private EditText passwordEditText;
    private EditText userNameEditText;
    private CheckBox rememberMeCheckBox;
    private final String settingFileName = "userSettings";
    private String userName = "";
    private String password = "";
    private final byte PROXY_ADDRESS_REQUEST_CODE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        setIDforAllElement();
        setLisenerForAllElement();
        checkedSettings(); // востанавливаем логин,пасс,чекбокс.
    }

    @Override
    protected void onDestroy() {
        srcManager();
        super.onDestroy();
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
            case R.id.loginSettingsMenu: {
                //@TODO вызов активити с найтроками.
                startSettingsActivityManager();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSettingsActivityManager() {
        Intent intent = new Intent(this, SettingsForFirstActivity.class);
        startActivityForResult(intent, PROXY_ADDRESS_REQUEST_CODE); //  код для прокси настроек
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            //@TODO берём данные из интент
            switch (requestCode) {
                case PROXY_ADDRESS_REQUEST_CODE: {
                    // TODO с определением какой интент нам пришёл
                    System.out.println(data.getStringExtra("IP"));
                    // TODO сделать разумные запросы, без хардкода
                    break;
                }
            }
        }
    }

    //@TODO додумыть функционал или удалить -_- ненужных кнопок.
    public void onClick(View elements) {
        switch (elements.getId()) {
            case R.id.passwordEditText:
                break;
            case R.id.nameEditText:
                break;
            case R.id.enterButton: {
                srcManager();
                setEasyStringUserInfo();
                break;
            }
            case R.id.rememberMeCheckBox:
                break;
            case R.id.mainLayout: {
                closeVirtualMenus();
                break;
            }
        }
    }

    private void closeVirtualMenus() {
        ((InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }


    private void setEasyStringUserInfo() {
        this.userName = this.userNameEditText.getText().toString();
        this.password = this.passwordEditText.getText().toString();
    }


    private void setLisenerForAllElement() {
        this.enterButton.setOnClickListener(this);
        this.passwordEditText.setOnClickListener(this);
        this.userNameEditText.setOnClickListener(this);
        this.rememberMeCheckBox.setOnClickListener(this);
        this.mainLayout.setOnClickListener(this);
    }

    private void setIDforAllElement() {
        this.enterButton = (Button) findViewById(R.id.enterButton);
        this.passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        this.userNameEditText = (EditText) findViewById(R.id.nameEditText);
        this.rememberMeCheckBox = (CheckBox) findViewById(R.id.rememberMeCheckBox);
        this.mainLayout = findViewById(R.id.mainLayout);
    }

    private void checkedSettings() {
        try {
            BufferedReader fileBuffer = new BufferedReader(new InputStreamReader(openFileInput(this.settingFileName)));
            this.rememberMeCheckBox.setChecked(true);
            loadSetting(fileBuffer);
            this.userNameEditText.setText(this.userName);
            this.passwordEditText.setText(this.password);
            fileBuffer.close();
        } catch (FileNotFoundException e) {
            this.rememberMeCheckBox.setChecked(false);
            srcManager();
        } catch (IOException e) {
            Log.d("ErrorIO", e.toString());
        }
    }

    private void loadSetting(BufferedReader fileStream) throws IOException {
        this.userName = fileStream.readLine();
        this.password = fileStream.readLine();
    }

    private void createdSettingFile() throws IOException {
        FileOutputStream fOut = openFileOutput(this.settingFileName, MODE_PRIVATE);
        BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(this.settingFileName)));
        if (br.readLine() == null) {
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            setUserInfoToFile(osw);
            osw.flush();
            osw.close();
        }
        br.close();
    }

    private void srcManager() {
        try {
            srcManagerEX();
        } catch (IOException e) {
            Log.d("ErrorIO", e.toString());
        }
    }

    private void srcManagerEX() throws IOException {
        if (!this.rememberMeCheckBox.isChecked())
            deleteFile(this.settingFileName);
        else createdSettingFile();
    }


    private void setUserInfoToFile(OutputStreamWriter streamToFile) throws IOException {
        streamToFile.write(this.userNameEditText.getText().toString() + '\n');
        streamToFile.write(this.passwordEditText.getText().toString() + '\n');
    }


}
