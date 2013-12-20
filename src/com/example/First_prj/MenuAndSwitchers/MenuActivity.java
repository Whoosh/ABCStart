package com.example.First_prj.MenuAndSwitchers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.example.First_prj.JavaServer.MightInfo;
import com.example.First_prj.JavaServer.Server;

public class MenuActivity extends Activity {
    // TODO private class to Menu
    private Menu mainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mainMenu = new Menu(this, MightInfo.getCurrentMightCode());
        setContentView(mainMenu);
    }

    @Override
    protected void onResume() {
        mainMenu.setBack(); // если что-то отрисовали, вернём всё назад
        super.onResume();
    }

}
