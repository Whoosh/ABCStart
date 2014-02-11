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
import ru.journal.fspoPrj.journal.callbacks.LessonsSelector;
import ru.journal.fspoPrj.journal.data_get_managers.JournalsCommunicator;
import ru.journal.fspoPrj.journal.elements.data_slider.DateSlider;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorButton;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorDialog;
import ru.journal.fspoPrj.journal.elements.main_table.TableWithMarks;
import ru.journal.fspoPrj.journal.elements.student_list.StudentList;

import java.util.Arrays;

public class LookingJournalActivity extends Activity implements View.OnTouchListener, View.OnClickListener
        , GroupSelectorDialog.GroupSelectedCallBack {

    private static final String EMPTY_TAG = "";

    private static JournalsCommunicator journalsCommunicator; // TODO

    private LessonsSelector lessonsSelector;
    private DateSlider dateSlider;
    private StudentList studentList;
    private LinearLayout mainLay;
    private LinearLayout datePlusMatrix;
    private LinearLayout groupSelectorPlusStudents;
    private TableWithMarks tableWithMarks;
    private GroupSelectorButton groupSelectorButton;
    private HorizontalScrollView datePlusCellMatrixScroller;
    private GroupSelectorDialog groupSelectorDialog;

    public void initElements() {
        mainLay = new LinearLayout(this);
        dateSlider = new DateSlider(this);
        studentList = new StudentList(this);
        lessonsSelector = new LessonsSelector(this, journalsCommunicator);
        datePlusMatrix = new LinearLayout(this);
        tableWithMarks = new TableWithMarks(this);
        groupSelectorButton = new GroupSelectorButton(this);
        groupSelectorPlusStudents = new LinearLayout(this);
        datePlusCellMatrixScroller = new HorizontalScrollView(this);
        groupSelectorDialog = new GroupSelectorDialog();

        datePlusMatrix.addView(dateSlider);
        datePlusMatrix.addView(tableWithMarks);
        datePlusMatrix.setOrientation(LinearLayout.VERTICAL);
        datePlusCellMatrixScroller.addView(datePlusMatrix);

        groupSelectorPlusStudents.setOrientation(LinearLayout.VERTICAL);
        groupSelectorPlusStudents.addView(groupSelectorButton);
        groupSelectorPlusStudents.addView(studentList);

        mainLay.addView(groupSelectorPlusStudents);
        mainLay.addView(datePlusCellMatrixScroller);
        mainLay.setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (journalsCommunicator == null) {
            journalsCommunicator = new JournalsCommunicator(this);
        }
        initElements();

        studentList.setOnTouchListener(this);
        groupSelectorButton.setOnClickListener(this);
        tableWithMarks.setOnTouchListener(this);

        startActionMode(lessonsSelector);
        setContentView(mainLay);
    }

    public GroupSelectorDialog.GroupSelectedCallBack getGroupSelectorCallBack(){
        return this;
    }

    @Override
    public void onBackPressed() {
        journalsCommunicator = null;
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            journalsCommunicator.cacheData(data, resultCode);
            initDataToElements();
            switch (resultCode) {
                case JournalsCommunicator.GROUPS_LIST_QUERY: {
                    // TODO
                }
                break;
                case JournalsCommunicator.RESEND_GROUP_LIST_FROM_GROUP_BUTTON: {
                    onClick(groupSelectorButton);
                }
                break;
                case JournalsCommunicator.RESEND_GROUP_LIST_FORM_REFRESH_BUTTON: {
                    groupSelectorButton.setDefaultText();
                }
                break;
            }
        } else {
            groupSelectorButton.setRefreshText();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initDataToElements() {
        groupSelectorDialog.setGroups(journalsCommunicator.getSortedGroups());
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        lessonsSelector.saveState(outState);
        groupSelectorDialog.saveState(outState);
        groupSelectorButton.saveState(outState);
        studentList.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lessonsSelector.restoreState(savedInstanceState);
        groupSelectorDialog.restoreState(savedInstanceState);
        groupSelectorButton.restoreState(savedInstanceState);
        studentList.restoreState(savedInstanceState);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != MotionEvent.ACTION_UP && view.equals(studentList)) {
            tableWithMarks.scrollScrollerTo(0, studentList.getScrollY());
            return false;
        }
        tableWithMarks.scrollScrollerTo(0, studentList.getScrollY());
        studentList.scrollTo(0, tableWithMarks.getScrollerY());
        return true;
    }


    @Override
    public void onClick(View view) {
        if (view.equals(groupSelectorButton)) {
            handleGroupButtonClick();
        }
    }

    private void handleGroupButtonClick() {
        String[] groups = journalsCommunicator.getSortedGroups();
        if (groups.length > 0) {
            groupSelectorDialog.show(getFragmentManager(), EMPTY_TAG);
        } else if (groupSelectorButton.isRefreshState()) {
            journalsCommunicator.sendGroupsInfoQuery(this, JournalsCommunicator.RESEND_GROUP_LIST_FORM_REFRESH_BUTTON);
        } else {
            journalsCommunicator.sendGroupsInfoQuery(this, JournalsCommunicator.RESEND_GROUP_LIST_FROM_GROUP_BUTTON);
        }
    }

    @Override
    public void groupHasSelected(String group) {
        groupSelectorButton.setSelectedGroup(group);
        studentList.setStudents(journalsCommunicator.getStudentsName(group));
        lessonsSelector.setLessons(journalsCommunicator.getLessonsName(group));
        // TODO query
    }
}

