package com.example.First_prj.MenuLogicStarter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.example.First_prj.MenuAndSwitchers.MenuLayout;

public class MenuLogicStarter extends Activity {
    private MenuLayout mainMenu;
    private int mightCode;
    private final int ERROR_CODE = -1;
    public static final int TEACHER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mightCode = getIntent().getIntExtra("MightCode", ERROR_CODE);
        mainMenu = new MenuLayout(this, getSections());
        setContentView(mainMenu);
    }


    @Override
    protected void onResume() {
        mainMenu.setSelectedItemColorBack();
        super.onResume();
    }

    public String[] getSections() {
        switch (mightCode) {
            case TEACHER: { //@TODO список параметров в меню для каждой сущьности к примеру преподователь = 0
                // @ TODO Идея, хранить нзвание под ключами, возвращать функцию обработки и взятия по ключам.
                return new String[] {"Занятия", "Общение", "Журналы", "Пользователи", "Студенты", "Мой профиль"};
                // пока нет реализации с сервисом можно такой говнокод..
            }
            case ERROR_CODE:{
                // Учесть обработку ошибки, с ссылкой на контакты.
            }
            default: {
                System.out.println("Несуществующее значение сущности");
                //@TODO подумать над обработкой.
                // можно запретить вызов или передачу параметра сюда, если значения нет в кейсе.
                return null;
            }
        }
    }
}
