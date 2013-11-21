package com.example.First_prj.MenuAndSwitchers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.First_prj.Journal.JournalTab;

public class MenuSwitcherActivity {

    private final static String[] keysForActivity = {"Занятия", "Общение", "Журналы", "Пользователи",
            "Студенты", "Мой профиль", "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё"
            , "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё", "чтото ещё"};
    Context context;

    public MenuSwitcherActivity(Context context) {
        this.context = context;
        //@TODO запилить загрузку всех возможных ключей для активити, взять записи из конфига
    }

    public int getActivityCounts() {
        return keysForActivity.length;
    }

    public String getActivityKey(int keyNumber) {
        return keysForActivity[keyNumber];
    }

    //@TODO В каждом кейсе вызов своего активити.
    public void startActivity(int indexOfValue) {
        //System.out.println(keysForActivity[indexOfValue]);
        switch (indexOfValue) {
            case 0: {
                Intent intent = new Intent(context, JournalTab.class);
                context.startActivity(intent);
                break;
            }
        }
    }
}
