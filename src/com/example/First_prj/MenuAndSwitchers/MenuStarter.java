package com.example.First_prj.MenuAndSwitchers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.First_prj.Journal.JournalTab;

public class MenuStarter {

    private Context context;

    public MenuStarter(Context context) {
        this.context = context;
    }

    public int getActivityCounts() {
        return Data.getLength();
    }

    public String getActivityKey(int index) {
        return Data.getFunctionName(index);
    }

    //@TODO В каждом кейсе вызов своего активити.
    public void startActivity(int indexOfValue) {
        switch (indexOfValue) {
            case 0: {
                context.startActivity(new Intent(context, JournalTab.class));
                break;
            }
        }
    }
}
