package ru.journal.fspoPrj.journal;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import ru.journal.fspoPrj.journal.action_bar.LessonsSelector;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.JournalCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.groups.Group;
import ru.journal.fspoPrj.journal.data_get_managers.groups.GroupLesson;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightVisits;
import ru.journal.fspoPrj.journal.looking_journal.elements.data_slider.DateSlider;
import ru.journal.fspoPrj.journal.looking_journal.elements.group_selector.GroupButton;
import ru.journal.fspoPrj.journal.looking_journal.elements.group_selector.GroupDialog;
import ru.journal.fspoPrj.journal.looking_journal.elements.head_selector.date_selector.SemesterButton;
import ru.journal.fspoPrj.journal.looking_journal.elements.head_selector.date_selector.SemesterSelector;
import ru.journal.fspoPrj.journal.looking_journal.elements.head_selector.date_selector.SemesterDialog;
import ru.journal.fspoPrj.journal.looking_journal.elements.main_table.TableWithMarks;
import ru.journal.fspoPrj.journal.looking_journal.elements.student_list.StudentList;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public abstract class JournalActivity extends Activity implements
        View.OnTouchListener, View.OnClickListener,
        GroupDialog.GroupSelectedCallBack,
        LessonsSelector.LessonSelectedCallBack,
        SemesterSelector.semesterCallBack,
        SemesterDialog.ClosedCallBack {

    public static final String EMPTY = "";

    protected static final String SEMESTER_TITLE = "Семестр - ";

    protected static JournalCommunicator jC;
    protected static Group selectedGroup;
    protected static GroupLesson selectedLesson;
    protected static int selectedSemester;

    protected LessonsSelector lessonsSelector;
    protected LinearLayout mainLay;
    protected HorizontalLine groupSelectorSeparateLine;
    protected DateSlider dateSlider;
    protected StudentList studentList;
    protected TableWithMarks tableWithMarks;

    protected GroupButton groupButton;
    protected GroupDialog groupDialog;
    protected SemesterButton semesterButton;
    protected SemesterDialog semesterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (selectedGroup == null) {
            selectedGroup = new Group();
        }
    }

    @Override
    public void onBackPressed() {
        jC = null;
        selectedLesson = null;
        selectedGroup = null;
        selectedSemester = 0;
        groupButton.removeGroupTitle();
        super.onBackPressed();
    }

    public void initElements() {
        lessonsSelector = new LessonsSelector(this);
        mainLay = new LinearLayout(this);
        mainLay.setBackgroundColor(Color.WHITE);

        groupSelectorSeparateLine = new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth());
        groupSelectorSeparateLine.setVisibility(selectedGroup.isEmpty() ? View.INVISIBLE : View.VISIBLE);

        groupButton = new GroupButton(this);
        groupDialog = new GroupDialog();

        semesterButton = new SemesterButton(this);
        semesterDialog = new SemesterDialog();
        if (jC != null && jC.getSortedGroups() != null && jC.getVisits() == null) {
            groupDialog.setGroups(jC.getSortedGroups());
            handleGroupClick();
        }
    }

    public void setJournalCommunicator(Class<? extends JournalCommunicator> communicator, JournalActivity journalActivity) {
        if (jC == null) {
            try {
                jC = communicator.getConstructor(JournalActivity.class).newInstance(journalActivity);
            } catch (Exception e) {
                Logger.printError(e, getClass());
            }
        }
        jC.setCallerLink(journalActivity);
    }

    protected void handlingResult(int resultCode) {
        switch (resultCode) {
            case JournalCommunicator.GROUPS_LIST_QUERY: {
                groupDialog.setGroups(jC.getSortedGroups());
                handleGroupClick();
            }
            break;
            case JournalCommunicator.LIGHT_VISITS_QUERY: {
                handleVisitsQuery();
            }
            break;
        }
    }

    protected void handleVisitsQuery() {
        LightVisits lightVisits = jC.getVisits();
        dateSlider.setData(lightVisits.getExercisesInfo());
        tableWithMarks.createTable(lightVisits, jC.getStudents(selectedGroup));
        studentList.setStudents(jC.getStudents(selectedGroup));
        if (groupSelectorSeparateLine.getVisibility() == View.INVISIBLE) {
            groupSelectorSeparateLine.setVisibility(View.VISIBLE);
        }
    }

    protected void handleSemesterClick() {
        semesterButton.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.clear();
                for (Integer semester : jC.getAllSemesters(selectedGroup)) {
                    contextMenu.add(SEMESTER_TITLE + semester).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            semesterSelected(Integer.valueOf(menuItem.getTitle().toString().replace(SEMESTER_TITLE, EMPTY)));
                            return true;
                        }
                    });
                }
            }
        });
        openContextMenu(semesterButton);
    }


    protected void handleGroupClick() {
        if (groupButton.isRefreshState()) {
            jC.resendLastQuery();
            groupButton.disableRefreshState();
        } else {
            groupDialog.show(getFragmentManager(), EMPTY);
        }
    }

    protected void restoreLessonStudents() {
        lessonsSelector.setLessonTitle(selectedLesson);
        if (!selectedGroup.isEmpty() && selectedLesson != null) {
            lessonsSelector.restoreState(jC.getLessons(selectedGroup, selectedSemester));
            studentList.restoreState(jC.getStudents(selectedGroup));
        } else {
            groupSelectorSeparateLine.setVisibility(View.INVISIBLE);
        }
    }

    protected void restoreTable(LightVisits lightVisits) {
        if (lightVisits != null && selectedLesson != null) {
            try {
                tableWithMarks.restoreState(lightVisits, jC.getStudents(selectedGroup));
                dateSlider.restoreState(lightVisits.getExercisesInfo());
            } catch (NullPointerException ex) {
                Logger.printError(ex, getClass());
            }
        }
    }

    protected void sendVisitsQueryByGroupSelect(GroupLesson[] groupLessons) {
        lessonsSelector.setLessons(groupLessons);
        for (GroupLesson lesson : groupLessons) {
            if (lesson.equals(selectedLesson)) {
                jC.sendGroupVisitsQuery(lesson);
                return;
            }
        }
        if (groupLessons.length != 0) {
            selectedLesson = groupLessons[0];
            jC.sendGroupVisitsQuery(selectedLesson);
        }
        lessonsSelector.setLessonTitle(selectedLesson);
    }

    protected void sendVisitsQueryByLessonSelect(GroupLesson lesson) {
        for (GroupLesson gLesson : jC.getLessons(selectedGroup, selectedSemester)) {
            if (gLesson.equals(lesson)) {
                selectedLesson = gLesson;
                break;
            }
        }
        jC.sendGroupVisitsQuery(selectedLesson);
    }
}
