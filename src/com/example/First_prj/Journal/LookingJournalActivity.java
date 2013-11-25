package com.example.First_prj.Journal;

import android.app.Activity;
import android.os.Bundle;
import com.example.First_prj.ForAllCode.BoldGradientLine;

public class LookingJournalActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BoldGradientLine(this,5));
    }
}


