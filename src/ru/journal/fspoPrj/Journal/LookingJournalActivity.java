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
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.JournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.groups_list.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.journal.elements.data_slider.DateSlider;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorButton;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorDialog;
import ru.journal.fspoPrj.journal.elements.head_selector.date_selector.DateSelectorButton;
import ru.journal.fspoPrj.journal.elements.head_selector.date_selector.DateSelectorDialog;
import ru.journal.fspoPrj.journal.elements.main_table.TableWithMarks;
import ru.journal.fspoPrj.journal.elements.student_list.StudentList;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class LookingJournalActivity extends Activity implements View.OnTouchListener, View.OnClickListener
        , GroupSelectorDialog.GroupSelectedCallBack, LessonsSelector.LessonSelectedCallBack {

    private static final String EMPTY_TAG = "";

    private static JournalsCommunicator journalsCommunicator; // TODO

    private static String currentSelectedGroup = "";
    private static String currentSelectedLesson = "";
    private static String currentSelectedGroupID = "";
    private static String currentSelectedLessonID = "";

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
    private DateSelectorButton dateSelectorButton;
    private DateSelectorDialog dateSelectorDialog;
    private HorizontalLine groupSelectorSeparateLine;

    public void initElements() {
        mainLay = new LinearLayout(this);
        dateSlider = new DateSlider(this);
        studentList = new StudentList(this);
        lessonsSelector = new LessonsSelector(this);
        datePlusMatrix = new LinearLayout(this);
        tableWithMarks = new TableWithMarks(this);
        groupSelectorButton = new GroupSelectorButton(this);
        groupSelectorPlusStudents = new LinearLayout(this);
        datePlusCellMatrixScroller = new HorizontalScrollView(this);
        dateSelectorButton = new DateSelectorButton(this);

        groupSelectorSeparateLine = new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth());

        groupSelectorDialog = new GroupSelectorDialog();
        dateSelectorDialog = new DateSelectorDialog();

        datePlusMatrix.addView(dateSlider);
        datePlusMatrix.addView(new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth()));
        datePlusMatrix.addView(tableWithMarks);
        datePlusMatrix.setOrientation(LinearLayout.VERTICAL);
        datePlusCellMatrixScroller.addView(datePlusMatrix);

        groupSelectorPlusStudents.setOrientation(LinearLayout.VERTICAL);
        groupSelectorPlusStudents.addView(groupSelectorButton);
        groupSelectorPlusStudents.addView(groupSelectorSeparateLine);
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
        groupSelectorSeparateLine.setVisibility(View.INVISIBLE);

        lessonsSelector.setDateSelectorButton(dateSelectorButton);

        dateSelectorButton.setOnClickListener(this);
        studentList.setOnTouchListener(this);
        groupSelectorButton.setOnClickListener(this);
        tableWithMarks.setOnTouchListener(this);

        startActionMode(lessonsSelector);
        setContentView(mainLay);
    }

    public GroupSelectorDialog.GroupSelectedCallBack getGroupSelectorCallBack() {
        return this;
    }

    public LessonsSelector.LessonSelectedCallBack getLessonSelectedCallBack() {
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
            groupSelectorButton.disableRefreshState();
            journalsCommunicator.cacheData(data, resultCode);
            handlingResult(resultCode);
        } else {
            groupSelectorButton.refreshStateON();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handlingResult(int resultCode) {
        switch (resultCode) {
            case JournalsCommunicator.GROUPS_LIST_QUERY: {
                groupSelectorDialog.setGroups(journalsCommunicator.getSortedGroups());
            }
            break;
            case JournalsCommunicator.LIGHT_VISITS_QUERY: {
                handleVisitsQuery();
            }
            break;
        }
    }

    private void handleVisitsQuery() {
        LightVisits lightVisits = journalsCommunicator.getLightVisits();
        dateSlider.setData(lightVisits.getExercisesInfo());
        tableWithMarks.createTable(lightVisits, journalsCommunicator.getStudents(currentSelectedGroup));
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        groupSelectorDialog.saveState(outState);
        groupSelectorButton.saveState(outState);
        dateSelectorButton.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (!currentSelectedGroup.isEmpty()) {
            lessonsSelector.restoreState(journalsCommunicator.getLessons(currentSelectedGroup));
            studentList.restoreState(journalsCommunicator.getStudents(currentSelectedGroup));
        }

        LightVisits lightVisits = journalsCommunicator.getLightVisits();
        if (lightVisits != null) {
            tableWithMarks.restoreState(lightVisits, journalsCommunicator.getStudents(currentSelectedGroup));
            dateSlider.restoreState(lightVisits.getExercisesInfo());
        }

        groupSelectorDialog.restoreState(savedInstanceState);
        groupSelectorButton.restoreState(savedInstanceState);
        dateSelectorButton.restoreState(savedInstanceState);
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
        } else if (view.equals(dateSelectorButton)) {
            handleDateButtonClick();
        }
    }

    @Override
    public void groupHasSelected(String group) {
        if (currentSelectedGroup.equals(group)) {
            return;
        }
        currentSelectedGroup = group;
        GroupLesson[] groupLessons = journalsCommunicator.getLessons(group);
        currentSelectedGroupID = groupLessons[0].getStringGroupID();

        groupSelectorSeparateLine.setVisibility(View.VISIBLE);
        groupSelectorButton.setSelectedGroup(group);
        studentList.setStudents(journalsCommunicator.getStudents(group));
        lessonsSelector.setLessons(groupLessons);

        sendVisitsQueryByGroupSelect(groupLessons);
    }

    private void sendVisitsQueryByGroupSelect(GroupLesson[] groupLessons) {
        if (currentSelectedLesson.isEmpty()) {
            journalsCommunicator.sendGroupVisitsLightQuery(this, groupLessons[0].getStringLessonID(), currentSelectedGroupID);
        } else {
            for (GroupLesson lesson : groupLessons) {
                if (lesson.getShortName().equals(currentSelectedLesson)) {
                    journalsCommunicator.sendGroupVisitsLightQuery(this, lesson.getStringLessonID(), currentSelectedGroupID);
                    return;
                }
            }
        }
        currentSelectedLessonID = groupLessons[0].getStringLessonID();
        journalsCommunicator.sendGroupVisitsLightQuery(this, currentSelectedLessonID, currentSelectedGroupID);
    }

    @Override
    public void lessonSelected(String lesson) {
        if (currentSelectedLesson.equals(lesson)) {
            return;
        }
        sendVisitsQueryByLessonSelect(lesson);
    }

    private void sendVisitsQueryByLessonSelect(String lesson) {
        GroupLesson[] currentGroupLessons = journalsCommunicator.getLessons(currentSelectedGroup);
        for (GroupLesson gLesson : currentGroupLessons) {
            if (gLesson.getShortName().equals(lesson)) {
                currentSelectedLesson = gLesson.getShortName();
                currentSelectedLessonID = gLesson.getStringLessonID();
                break;
            }
        }
        journalsCommunicator.sendGroupVisitsLightQuery(this, currentSelectedLessonID, currentSelectedGroupID);
    }

    @Override
    public void dateSelected(String date) {
        dateSelectorButton.setSelectedDate(date);
        // TODO query
        System.out.println(date);
    }

    private void handleDateButtonClick() {
        dateSelectorDialog.show(getFragmentManager(), EMPTY_TAG);
    }

    private void handleGroupButtonClick() {
        if (journalsCommunicator.getSortedGroups().length > 0) {
            groupSelectorDialog.show(getFragmentManager(), EMPTY_TAG);
        } else {
            journalsCommunicator.resendLastQuery(this);
            groupSelectorButton.disableRefreshState();
        }
    }

}

