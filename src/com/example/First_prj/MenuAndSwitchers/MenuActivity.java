package com.example.First_prj.MenuAndSwitchers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.example.First_prj.JavaServer.MightInfo;
//
public class MenuActivity extends Activity {
    // TODO private class to MainMenu
    private MainMenu mainMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mainMainMenu = new MainMenu(this, MightInfo.getCurrentMightCode());
        setContentView(mainMainMenu);
    }

    @Override
    protected void onResume() {
        mainMainMenu.setBack(); // если что-то отрисовали, вернём всё назад
        super.onResume();
    }

}
