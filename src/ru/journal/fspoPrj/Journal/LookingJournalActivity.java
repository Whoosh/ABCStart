package ru.journal.fspoPrj.journal;

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
import ru.journal.fspoPrj.journal.data_get_managers.LookingJournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.journal.elements.data_slider.DateSlider;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorButton;
import ru.journal.fspoPrj.journal.elements.group_selector.GroupSelectorDialog;
import ru.journal.fspoPrj.journal.elements.head_selector.date_selector.SemesterButton;
import ru.journal.fspoPrj.journal.elements.head_selector.date_selector.SemesterSelector;
import ru.journal.fspoPrj.journal.elements.head_selector.date_selector.SemesterSelectorDialog;
import ru.journal.fspoPrj.journal.elements.main_table.TableWithMarks;
import ru.journal.fspoPrj.journal.elements.student_list.StudentList;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class LookingJournalActivity extends android.app.Activity implements View.OnTouchListener, View.OnClickListener,
        GroupSelectorDialog.GroupSelectedCallBack, LessonsSelector.LessonSelectedCallBack, SemesterSelector.semesterCallBack,
        SemesterSelectorDialog.ClosedCallBack {

    private static final String EMPTY = "";
    private static LookingJournalsCommunicator jC; // TODO
    private static Group selectedGroup;
    private static GroupLesson selectedLesson;

    private static int selectedSemester;

    private LessonsSelector lessonsSelector;
    private DateSlider dateSlider;
    private StudentList studentList;
    private LinearLayout mainLay;
    private TableWithMarks tableWithMarks;
    private GroupSelectorButton groupSelector;
    private GroupSelectorDialog groupSelectorDialog;
    private SemesterButton semesterSelectedButton;
    private SemesterSelectorDialog semesterSelectorDialog;
    private HorizontalLine groupSelectorSeparateLine;

    public void initElements() {
        LinearLayout datePlusMatrix = new LinearLayout(this);
        LinearLayout groupSelectorPlusStudents = new LinearLayout(this);
        HorizontalScrollView datePlusCellMatrixScroller = new HorizontalScrollView(this);

        mainLay = new LinearLayout(this);
        dateSlider = new DateSlider(this);
        studentList = new StudentList(this);
        lessonsSelector = new LessonsSelector(this, this);
        tableWithMarks = new TableWithMarks(this);
        groupSelector = new GroupSelectorButton(this);
        semesterSelectedButton = new SemesterButton(this);

        groupSelectorSeparateLine = new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth());
        groupSelectorDialog = new GroupSelectorDialog();
        semesterSelectorDialog = new SemesterSelectorDialog();

        semesterSelectorDialog.setCallBack(this);

        datePlusMatrix.addView(dateSlider);
        datePlusMatrix.addView(new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth()));
        datePlusMatrix.addView(tableWithMarks);
        datePlusMatrix.setOrientation(LinearLayout.VERTICAL);
        datePlusCellMatrixScroller.addView(datePlusMatrix);

        groupSelectorPlusStudents.setOrientation(LinearLayout.VERTICAL);
        groupSelectorPlusStudents.addView(groupSelector);
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

        if (jC == null) {
            jC = new LookingJournalsCommunicator(this);
            selectedGroup = new Group();
        }

        initElements();
        groupSelectorSeparateLine.setVisibility(View.INVISIBLE);

        lessonsSelector.setSemesterButton(semesterSelectedButton);

        semesterSelectedButton.setOnClickListener(this);
        studentList.setOnTouchListener(this);
        groupSelector.setOnClickListener(this);
        tableWithMarks.setOnTouchListener(this);

        if (!selectedGroup.isEmpty()) {
            groupSelectorSeparateLine.setVisibility(View.VISIBLE);
        }
        startActionMode(lessonsSelector);
        setContentView(mainLay);
    }

    @Override
    public void onBackPressed() {
        jC = null;
        selectedLesson = null;
        selectedGroup = null;
        selectedSemester = 0;
        groupSelector.removeGroupTitle();
        super.onBackPressed();
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
        if (view.equals(groupSelector)) {
            handleGroupClick();
        } else if (view.equals(semesterSelectedButton)) {
            handleSemesterClick();
        }
    }

    @Override
    public void lessonSelected(GroupLesson lesson) {
        if (!selectedLesson.equals(lesson)) {
            sendVisitsQueryByLessonSelect(lesson);
            lessonsSelector.setLessonTitle(selectedLesson);
        }
    }

    @Override
    public void groupHasSelected(int groupNumber) {
        Group group = jC.getGroup(groupNumber);
        if (!selectedGroup.equals(group)) {
            selectedGroup = group;
            semesterSelectorDialog.setAllPossiblySemesters(jC.getAllSemesters(group));
            groupSelector.setSelectedGroup(group);
            if (jC.getLessons(group, selectedSemester).length == 0) {
                selectedSemester = jC.getFirstPossiblySemester(selectedGroup);
                semesterSelectedButton.setSelectedSemester(selectedSemester);
            }
            sendVisitsQueryByGroupSelect(jC.getLessons(group, selectedSemester));
        }
    }

    @Override
    public void semesterSelected(int semester) {
        if (semester != selectedSemester) {
            selectedSemester = semester;
            semesterSelectedButton.setSelectedSemester(semester);
            lessonsSelector.setLessons(jC.getLessons(selectedGroup, selectedSemester));
            sendVisitsQueryByGroupSelect(jC.getLessons(selectedGroup, semester));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == LookingJournalsCommunicator.RESULT_FAIL) {
            groupSelector.refreshStateON();
            return;
        }
        if (data != null) {
            groupSelector.disableRefreshState();
            jC.cacheData(data, resultCode);
            handlingResult(resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        groupSelectorDialog.saveState(outState);
        groupSelector.saveState(outState);
        semesterSelectedButton.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        restoreLessonStudents();
        restoreTable(jC.getLightVisits());
        groupSelectorDialog.restoreState(savedInstanceState);
        groupSelector.restoreState(savedInstanceState);
        semesterSelectedButton.restoreState(savedInstanceState);
    }

    @Override
    public void semesterDialogClosed() {
        semesterSelectedButton.setEnabled(true);
    }

    @Override
    public void semesterDialogOpened() {
        semesterSelectedButton.setEnabled(false);
    }

    private void handlingResult(int resultCode) {
        switch (resultCode) {
            case LookingJournalsCommunicator.GROUPS_LIST_QUERY: {
                groupSelectorDialog.setGroups(jC.getSortedGroups());
            }
            break;
            case LookingJournalsCommunicator.LIGHT_VISITS_QUERY: {
                handleVisitsQuery();
            }
            break;
        }
    }

    private void handleVisitsQuery() {
        LightVisits lightVisits = jC.getLightVisits();
        dateSlider.setData(lightVisits.getExercisesInfo());
        tableWithMarks.createTable(lightVisits, jC.getStudents(selectedGroup));
        studentList.setStudents(jC.getStudents(selectedGroup));
        if (groupSelectorSeparateLine.getVisibility() == View.INVISIBLE) {
            groupSelectorSeparateLine.setVisibility(View.VISIBLE);
        }
    }

    private void handleSemesterClick() {
        semesterDialogOpened();
        semesterSelectorDialog.show(getFragmentManager(), EMPTY);
    }

    private void handleGroupClick() {
        if (groupSelector.isRefreshState()) {
            jC.resendLastQuery(this);
            groupSelector.disableRefreshState();
        } else {
            groupSelectorDialog.show(getFragmentManager(), EMPTY);
        }
    }

    private void restoreLessonStudents() {
        lessonsSelector.setLessonTitle(selectedLesson);
        if (!selectedGroup.isEmpty() && selectedLesson != null) {
            lessonsSelector.restoreState(jC.getLessons(selectedGroup, selectedSemester));
            studentList.restoreState(jC.getStudents(selectedGroup));
        } else {
            groupSelectorSeparateLine.setVisibility(View.INVISIBLE);
        }
    }

    private void restoreTable(LightVisits lightVisits) {
        if (lightVisits != null && selectedLesson != null) {
            try {
                tableWithMarks.restoreState(lightVisits, jC.getStudents(selectedGroup));
                dateSlider.restoreState(lightVisits.getExercisesInfo());
            } catch (NullPointerException ex) {
                Logger.printError(ex, getClass());
            }
        }
    }

    private void sendVisitsQueryByGroupSelect(GroupLesson[] groupLessons) {
        lessonsSelector.setLessons(groupLessons);
        for (GroupLesson lesson : groupLessons) {
            if (lesson.equals(selectedLesson)) {
                jC.sendGroupVisitsLightQuery(this, lesson);
                return;
            }
        }
        if (groupLessons.length != 0) {
            selectedLesson = groupLessons[0];
            jC.sendGroupVisitsLightQuery(this, selectedLesson);
        }
        lessonsSelector.setLessonTitle(selectedLesson);
    }

    private void sendVisitsQueryByLessonSelect(GroupLesson lesson) {
        for (GroupLesson gLesson : jC.getLessons(selectedGroup, selectedSemester)) {
            if (gLesson.equals(lesson)) {
                selectedLesson = gLesson;
                break;
            }
        }
        jC.sendGroupVisitsLightQuery(this, selectedLesson);
    }


}

