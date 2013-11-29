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
        mainMenu = new Menu(this, getSections());
        setContentView(mainMenu);
    }

    @Override
    protected void onResume() {
        mainMenu.setBack(); // если что-то отрисовали, вернём всё назад
        super.onResume();
    }

    public int[] getSections() {
        switch (mightCode) {
            case Constants.TEACHER_CODE:
                return Data.getTeacherSet();
            case Constants.STUDENT_CODE:
                return Data.getStudentSet();
            case Constants.PARENT_CODE:
                return Data.getParentSet();
            case Constants.STUDENT_TEACHER_CODE:
                return Data.getTeacherStudentSet();
            case Constants.ERROR_CODE: {
                //@TODO по какойто причине не смогли вытащить значение.. подумать как обработать ошибку.
            }
            default:{
                return new int[]{10,1};
            }
        }
    }
}
