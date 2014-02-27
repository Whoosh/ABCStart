package ru.journal.fspoPrj.journal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.journal.elements.cell_status_setters.CellLongClickDialog;
import ru.journal.fspoPrj.journal.elements.custom_cell.EvolutionCell;
import ru.journal.fspoPrj.journal.elements.data_slider.DateElement;
import ru.journal.fspoPrj.journal.elements.right_side_journal_menu.RightSideJournalMenu;

public class TeacherJournalActivity extends LookingJournalActivity implements
        RightSideJournalMenu.JournalEditsCallBack,
        View.OnLongClickListener {

    private RightSideJournalMenu functionalRightBar;
    private CellLongClickDialog cellLongClickDialog;

    @Override
    public void initElements() {
        super.initElements();

        functionalRightBar = new RightSideJournalMenu(this);
        functionalRightBar.setCallBack(this);

        tableWithMarks.setOnCellsShortClickListener(this);
        tableWithMarks.setOnCellsLongClickListener(this);
        tableWithMarks.setRightFunctions(functionalRightBar);

        dateSlider.setClickListener(this);

        cellLongClickDialog = new CellLongClickDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setJournalCommunicator(EditJournalsCommunicator.class, this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void journalMenuClicked(int keyCode) {
        switch (keyCode) {
            case RightSideJournalMenu.PLUS: {
                System.out.println("IM click plus");
                break;
            }
            case RightSideJournalMenu.EDIT: {
                System.out.println("Im click Edit");
                break;
            }
            case RightSideJournalMenu.REMOVE: {
                System.out.println("Im click Remove");
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view instanceof DateElement) {
            handleDateElementClick((DateElement) view);
        } else if (view instanceof EvolutionCell) {
            handleCellShortClick((EvolutionCell) view);
        }
        super.onClick(view);
    }

    @Override
    public boolean onLongClick(View view) {
        if (view instanceof EvolutionCell) {
            handleCellLongClick((EvolutionCell) view);
        }
        return true;
    }

    private void handleCellLongClick(EvolutionCell cell) {
        // TODO запрос
        cellLongClickDialog.setPreparingCell(cell);
        cellLongClickDialog.show(getFragmentManager(), EMPTY);
    }

    private void handleCellShortClick(EvolutionCell cell) {
        // TODO запрос и по ответу, уже мейкаем.
        makeShortClickResultOnCell(cell);
    }

    private void makeShortClickResultOnCell(EvolutionCell cell) {
        cell.changeComingStatus();
    }

    private void handleDateElementClick(DateElement element) {
        setColorOnColumn(element,Color.GREEN);
        System.out.println(element.getDate());
    }

    private void setColorOnColumn(DateElement element,int color) {
        element.setBackgroundColor(color);
        tableWithMarks.setColumnColor(element.getTableIndex(),color);
    }
}

