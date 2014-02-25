package ru.journal.fspoPrj.journal;

import android.os.Bundle;
import android.view.View;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.journal.elements.custom_cell.EvolutionCell;

public class TeacherJournalActivity extends LookingJournalActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setJournalCommunicator(EditJournalsCommunicator.class, this);
        super.onCreate(savedInstanceState);
        tableWithMarks.setOnCellClickListener(this);
    }

    @Override
    public void onClick(View view) {
        System.out.println("IMG CLICKED");
        super.onClick(view);
    }
}

