package ru.journal.fspoPrj.journal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.data_get_managers.EditJournalsCommunicator;

public class TeacherJournalsActivity extends Activity implements View.OnClickListener {

    private static EditJournalsCommunicator eJC;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (eJC == null) {
            eJC = new EditJournalsCommunicator(this);
        }
        checkBox = new CheckBox(this);
        checkBox.setOnClickListener(this);

        LinearLayout layout = new LinearLayout(this);
        layout.addView(checkBox);
        setContentView(layout);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            eJC.cacheData(data, resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        eJC.sendTeacherLessonsQuery(this);
    }
}
