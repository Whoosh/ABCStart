package com.example.First_prj.Journal;

import android.app.Activity;
import android.os.Bundle;
import com.example.First_prj.ForAllCode.BubleGradientLine;

public class EditJournalActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BubleGradientLine(this,5));
    }
}
