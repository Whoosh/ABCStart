package ru.journal.fspoPrj.journal;

import android.app.*;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.callbacks.Lessons;
import ru.journal.fspoPrj.journal.data_get_managers.JournalsCommunicator;
import ru.journal.fspoPrj.journal.elements.data_slider.DateSlider;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorButton;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorDialog;
import ru.journal.fspoPrj.journal.elements.main_table.MScrollView;
import ru.journal.fspoPrj.journal.elements.main_table.TableWithMarks;
import ru.journal.fspoPrj.journal.elements.student_list.StudentList;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.VerticalLine;

import java.util.ArrayList;

public class LookingJournalActivity extends Activity implements View.OnTouchListener, View.OnClickListener {

    public static final String EMPTY_TAG = "";
    private static JournalsCommunicator journalsCommunicator; // TODO

    private GroupSelectorButton selectorButton;
    private TableWithMarks tableWithMarks;
    private Lessons lessonsSelector;
    private DateSlider dateSlider;
    private StudentList studentList;
    private LinearLayout mainLay;
    private LinearLayout datePlusMatrix;
    private LinearLayout groupSelectorPlusStudents;
    private HorizontalScrollView datePlusCellMatrixScroller;

    public void initElements() {
        mainLay = new LinearLayout(this);
        lessonsSelector = new Lessons(this);
        selectorButton = new GroupSelectorButton(this);
        dateSlider = new DateSlider(this);
        groupSelectorPlusStudents = new LinearLayout(this);
        tableWithMarks = new TableWithMarks(this);
        studentList = new StudentList(this);
        datePlusMatrix = new LinearLayout(this);
        datePlusCellMatrixScroller = new HorizontalScrollView(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initElements();
        if (journalsCommunicator == null) {
            journalsCommunicator = new JournalsCommunicator(this);
        }

        ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dates.add("" + i);
        }

        ArrayList<String> students = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            students.add("Студент такой то" + i);
        }

        studentList.setStudents(students);
        dateSlider.setData(dates);

        studentList.setOnTouchListener(this);
        selectorButton.setOnClickListener(this);

        tableWithMarks.createTable(5, 5);
        tableWithMarks.setOnTouchListener(this);

        datePlusMatrix.addView(dateSlider);
        datePlusMatrix.addView(tableWithMarks);
        datePlusMatrix.setOrientation(LinearLayout.VERTICAL);
        datePlusCellMatrixScroller.addView(datePlusMatrix);

        groupSelectorPlusStudents.setOrientation(LinearLayout.VERTICAL);
        groupSelectorPlusStudents.addView(selectorButton);
        groupSelectorPlusStudents.addView(studentList);

        mainLay.addView(groupSelectorPlusStudents);
        mainLay.addView(datePlusCellMatrixScroller);
        mainLay.addView(new VerticalLine(this, Color.CYAN, 5)); //TODO

        startActionMode(lessonsSelector);
        setContentView(mainLay);
    }

    @Override
    public void onBackPressed() {
        //lessonsSelector.close();
        journalsCommunicator = null; // TODO if close from lessons
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            journalsCommunicator.cacheData(data, resultCode);
            switch (resultCode) {
                case JournalsCommunicator.GROUPS_LIST_QUERY: {
                    // example
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        lessonsSelector.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lessonsSelector.restoreState(savedInstanceState);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            if (view.equals(studentList)) {
                tableWithMarks.scrollScrollerTo(0, studentList.getScrollY());
            }
            return false;
        }
        tableWithMarks.scrollScrollerTo(0, studentList.getScrollY());
        studentList.scrollTo(0, tableWithMarks.getScrollerY());
        return true;
    }


    @Override
    public void onClick(View view) {
        new GroupSelectorDialog(journalsCommunicator.getGroupArray()).show(getFragmentManager(), EMPTY_TAG);
    }


}

