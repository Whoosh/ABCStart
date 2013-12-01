package com.example.First_prj.Journal;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.example.First_prj.ForAllCode.SerifTextView;
import com.example.First_prj.Journal.MainTable.MainConstructor;

public class LookingJournalActivity extends Activity {
    private MainConstructor mainConstructor;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainConstructor = new MainConstructor(this);

        setContentView(mainConstructor);

    }


}


