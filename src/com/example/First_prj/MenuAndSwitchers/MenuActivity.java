package com.example.First_prj.MenuAndSwitchers;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.example.First_prj.ForAllCode.Constants;

public class MenuActivity extends Activity {

    private Menu mainMenu;
    private int mightCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mightCode = getIntent().getIntExtra("MightCode", Constants.ERROR_CODE);
        mightCode = Constants.TEACHER_CODE;
        mainMenu = new Menu(this, mightCode);
        setContentView(mainMenu);
    }

    @Override
    protected void onResume() {
        mainMenu.setBack(); // если что-то отрисовали, вернём всё назад
        super.onResume();
    }

}
