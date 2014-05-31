package ru.journal.fspoPrj.journal.edit_journal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import ru.journal.fspoPrj.journal.action_bar.TeacherJournalInfoBar;
import ru.journal.fspoPrj.journal.config.Config;
import ru.journal.fspoPrj.journal.data_get_managers.communicators.EditJournalsCommunicator;
import ru.journal.fspoPrj.journal.data_get_managers.teacher_lessons.TeacherGroup;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.LightExercisesInfo;
import ru.journal.fspoPrj.journal.data_get_managers.visits_light.Visit;
import ru.journal.fspoPrj.journal.edit_journal.elements.date_slider.TeacherDateSlider;
import ru.journal.fspoPrj.journal.edit_journal.elements.lable.LessonLabel;
import ru.journal.fspoPrj.journal.edit_journal.elements.main_table.TeacherTableWithMarks;
import ru.journal.fspoPrj.journal.edit_journal.elements.right_side_journal_menu.RightSideJournalMenu;
import ru.journal.fspoPrj.journal.looking_journal.elements.data_slider.DateElement;
import ru.journal.fspoPrj.journal.looking_journal.elements.student_list.StudentList;
import ru.journal.fspoPrj.journal.EvolutionCell;
import ru.journal.fspoPrj.public_code.custom_desing_elements.lines.HorizontalLine;

public class TeacherJournalActivity extends Activity implements
        View.OnTouchListener, View.OnClickListener,
        TeacherJournalInfoBar.OnGroupSelected,
        RightSideJournalMenu.JournalEditsCallBack,
        View.OnLongClickListener {

    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String RANK = "Оценка";
    public static final String POWER_UP_TITLE = "Активность студента";
    public static final String STUDENT_POWER_HIGH_TITLE = "Высокая";
    public static final String STUDENT_POWER_LOW_TITLE = "Низкая";
    public static final String HIGH_NEEDED_STUDENT_TITLE = "Высокая";
    public static final String MIDDLE_NEEDED_STUDENT_TITLE = "Средняя";
    public static final String LOW_NEEDED_STUDENT_TITLE = "Низкая";
    public static final String REMOVE_CELL_STATE_TITLE = "Убрать";
    public static final String NEEDED_STATUS_TITLE = "Необходимость присутствия";
    public static final String SLOWER_STUDENT_STATUS_SLOW_TITLE = "Опоздал";
    public static final String SLOWER_STUDENT_STATUS_FAST_TITLE = "Ушёл раньше";
    public static final String SLOWER_STUDENT_STATUS_PUSHED_OFF_TITLE = "Выгнан";
    public static final String STUDENT_SLOWER_TITLE = "Дополнительно";
    public static final String WEIGHT_POINT_STATUS_TITLE = "Вес оценки";
    public static final String POINT_WEIGHT_HIGH_STATUS_TITLE = "Высокий";
    public static final String POINT_WEIGHT_MIDDLE_STATUS_TITLE = "Средний";
    public static final String POINT_WEIGHT_LOW_STATUS_TITLE = "Низкий";
    public static final String EXCLUSIVE_POINT_STATUS_TITLE = "Особая важность оценки";
    public static final String EXCLUSIVE_POINT_STATUS_SET_TITLE = "Установить";
    public static final String PAIR_SELECTED_TITLE = "Выбор пары";
    public static final String SELECT_DATE_FOR_DELETE_MESSAGE = "Выберете дату для удаления";
    public static final String SELECT_DATE_FOR_EDIT_MESSAGE = "Выберете дату для редактирования";
    public static final String TYPE_LESSON_MESSAGE = "Тип занятия";
    public static final String EDIT_MENU_TITLE = "Доступные возможности";
    public static final String TYPE_CHANGE_DEFAULT_LESSON_TITLE = "Лекция";
    public static final String TYPE_CHANGE_LAB_WORK_TITLE = "Лабораторная";
    public static final String TYPE_CHANGE_PRACTICAL_LESSON_TITLE = "Практическая";
    public static final String TYPE_CHANGE_CONTROL_WORK_TITLE = "Контрольная работа";
    public static final String TYPE_CHANGE_ATTESTATION_TITLE = "Аттестация";
    public static final String TYPE_CHANGE_SAVE_POINT_TITLE = "Зачёт";
    public static final String TYPE_CHANGE_EXAM_WORK_TITLE = "Экзамен";

    public static final int RANK_CHANGE_REMOVE_ID = 1;
    public static final int RANK_CHANGE_TWO_ID = 2;
    public static final int RANK_CHANGE_THREE_ID = 3;
    public static final int RANK_CHANGE_FORE_ID = 4;
    public static final int RANK_CHANGE_FIFE_ID = 5;
    public static final int POWER_UP_IS_HIGH_ID = 6;
    public static final int POWER_UP_IS_LOW_ID = 7;
    public static final int POWER_UP_REMOVE_ID = 8;
    public static final int NEEDED_STATUS_HIGH_ID = 9;
    public static final int NEEDED_STATUS_MIDDLE_ID = 10;
    public static final int NEEDED_STATUS_LOW_ID = 11;
    public static final int NEEDED_STATUS_REMOVE_ID = 12;
    public static final int SLOWER_STUDENT_STATUS_SLOW_ID = 13;
    public static final int SLOWER_STUDENT_STATUS_FAST_ID = 14;
    public static final int SLOWER_STUDENT_STATUS_PUSHED_OFF_ID = 15;
    public static final int SLOWER_STUDENT_STATUS_REMOVE_ID = 16;
    public static final int POINT_WEIGHT_STATUS_HIGH_ID = 17;
    public static final int POINT_WEIGHT_STATUS_MIDDLE_ID = 18;
    public static final int POINT_WEIGHT_STATUS_LOW_ID = 19;
    public static final int POINT_WEIGHT_STATUS_REMOVE_ID = 20;
    public static final int EXCLUSIVE_POINT_STATUS_SET_ID = 21;
    public static final int EXCLUSIVE_POINT_STATUS_REMOVE_ID = 22;
    public static final int PAIRS_SELECTED_BUTTON_ID = 23;
    public static final int TYPE_CHANGE_DEFAULT_LESSON_ID = 24;
    public static final int TYPE_CHANGE_LAB_WORK_ID = 25;
    public static final int TYPE_CHANGE_PRACTICAL_WORK_ID = 26;
    public static final int TYPE_CHANGE_CONTROL_WORK_ID = 27;
    public static final int TYPE_CHANGE_ATTESTATION_ID = 28;
    public static final int TYPE_CHANGE_SAVE_POINT_ID = 29;
    public static final int TYPE_CHANGE_EXAM_WORK_ID = 30;

    public static final int MIN_AVAILABLE_PAIR = 1;
    public static final int MAX_AVAILABLE_PAIR = 6;

    private static EditJournalsCommunicator jC;

    private static int rankBuffer;
    private static int powerBuffer;
    private static int neededBuffer;
    private static int slowerBuffer;
    private static int importantWeightBuffer;
    private static int exclusivePointStatusBuffer;
    private static int lessonTypeChangeBuffer;
    private static int lastJournalAction;
    private static int lastExerciseSelected;

    private LinearLayout mainLay;
    private HorizontalLine groupSelectorSeparateLine;
    private StudentList studentList;
    private TeacherDateSlider dateSlider;
    private TeacherJournalInfoBar teacherJournalInfoBar;
    private TeacherTableWithMarks tableWithMarks;
    private LessonLabel lessonLabel;
    private EvolutionCell lastClickedCell;
    private RightSideJournalMenu functionalRightBar;

    public void initElements() {
        LinearLayout datePlusMatrix = new LinearLayout(this);
        LinearLayout groupSelectorPlusStudents = new LinearLayout(this);
        HorizontalScrollView datePlusCellMatrixScroller = new HorizontalScrollView(this);

        groupSelectorSeparateLine = new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth());
        teacherJournalInfoBar = new TeacherJournalInfoBar(this);
        lessonLabel = new LessonLabel(this, EMPTY);
        mainLay = new LinearLayout(this);
        dateSlider = new TeacherDateSlider(this);
        studentList = new StudentList(this);
        tableWithMarks = new TeacherTableWithMarks(this);
        functionalRightBar = new RightSideJournalMenu(this);

        lessonLabel.setTextSize(Config.getEditJournalLessonLabelTextSize());
        mainLay.setBackgroundColor(Color.WHITE);
        groupSelectorSeparateLine.setVisibility(jC == null || jC.isTeacherVisitsEmpty() ? View.INVISIBLE : View.VISIBLE);

        datePlusMatrix.addView(dateSlider);
        datePlusMatrix.addView(new HorizontalLine(this, Color.BLACK, Config.getJournalEndLineWidth()));
        datePlusMatrix.addView(tableWithMarks);
        datePlusMatrix.setOrientation(LinearLayout.VERTICAL);
        datePlusCellMatrixScroller.addView(datePlusMatrix);

        groupSelectorPlusStudents.setOrientation(LinearLayout.VERTICAL);
        groupSelectorPlusStudents.addView(lessonLabel);
        groupSelectorPlusStudents.addView(groupSelectorSeparateLine);
        groupSelectorPlusStudents.addView(studentList);

        mainLay.addView(groupSelectorPlusStudents);
        mainLay.addView(datePlusCellMatrixScroller);

        tableWithMarks.setRightFunctions(functionalRightBar);

        studentList.setOnTouchListener(this);
        lessonLabel.setOnClickListener(this);
        tableWithMarks.setOnTouchListener(this);
        dateSlider.setClickListener(this);
        functionalRightBar.setCallBack(this);
        tableWithMarks.setOnCellsShortClickListener(this);
        tableWithMarks.setOnCellsLongClickListener(this);
        dateSlider.setClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initElements();
        if (jC == null) {
            jC = new EditJournalsCommunicator(this);
        }
        startActionMode(teacherJournalInfoBar);
        setContentView(mainLay);
        teacherJournalInfoBar.setJC(jC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EditJournalsCommunicator.RESULT_FAIL) {
            teacherJournalInfoBar.enableRefreshMode();
            teacherJournalInfoBar.setState(jC);
            return;
        } else {
            teacherJournalInfoBar.disableRefreshMode();
        }
        if (data != null) {
            jC.cacheData(data, resultCode);
            handlingResult(resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        jC = null;
        lastClickedCell = null;
        super.onBackPressed();
    }

    @Override
    public void onGroupSelected(TeacherGroup group) {
        if (group.equals(jC.getSelectedGroup())) return;
        lessonLabel.setSelectedLesson(group, jC.getTeacherJournal());
        jC.setTeacherGroup(group);
        jC.sendVisitsQueryByGroup(group);
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
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        lessonLabel.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lessonLabel.restoreState(savedInstanceState);
        if (!jC.isTeacherLessonsEmpty()) {
            teacherJournalInfoBar.setState(jC);
        }
        if (jC.canBeGetFromCache()) {
            tableWithMarks.setState(jC);
            studentList.setState(jC);
            dateSlider.setState(jC);
        }
    }

    @Override
    public void journalMenuClicked(int keyCode) {
        lastJournalAction = keyCode;
        registerForContextMenu(functionalRightBar);
        openContextMenu(functionalRightBar);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof DateElement) {
            handleDateElementClick((DateElement) view);
        } else if (view instanceof EvolutionCell) {
            handleCellShortClick((EvolutionCell) view);
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (view instanceof EvolutionCell) {
            handleCellLongClick((EvolutionCell) view);
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v instanceof EvolutionCell) {
            menu.setHeaderTitle(jC.getStudentInfo(lastClickedCell.getVisit().getStudentID()) +
                    SPACE +
                    jC.getDMDate(lastClickedCell.getVisit().getExercisesID()));
            setCellClickedMenu(menu);
        } else if (v instanceof RightSideJournalMenu) {
            setEditJournalMenu(menu);
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case RANK_CHANGE_REMOVE_ID: {
                rankBuffer = Visit.PointState.FAIL.ordinal();
                jC.removePointStatusQuery(lastClickedCell);
            }
            break;
            case RANK_CHANGE_TWO_ID:
            case RANK_CHANGE_THREE_ID:
            case RANK_CHANGE_FORE_ID:
            case RANK_CHANGE_FIFE_ID: {
                rankBuffer = Integer.valueOf(item.getTitle().toString());
                jC.changeStudentRankQuery(lastClickedCell, item.getTitle().toString());
            }
            break;
            case POWER_UP_IS_HIGH_ID: {
                powerBuffer = Visit.PerformanceState.LOW.ordinal();
                jC.changeStudentPowerQuery(lastClickedCell, powerBuffer);
            }
            break;
            case POWER_UP_IS_LOW_ID: {
                powerBuffer = Visit.PerformanceState.HIGH.ordinal();
                jC.changeStudentPowerQuery(lastClickedCell, powerBuffer);
            }
            break;
            case POWER_UP_REMOVE_ID: {
                powerBuffer = Visit.PerformanceState.NOT_SET.ordinal();
                jC.changeStudentPowerQuery(lastClickedCell, powerBuffer);
            }
            break;
            case NEEDED_STATUS_HIGH_ID: {
                neededBuffer = Visit.VisitNeedState.HIGH.ordinal();
                jC.changeStudentNeededOnPairQuery(lastClickedCell, neededBuffer);
            }
            break;
            case NEEDED_STATUS_MIDDLE_ID: {
                neededBuffer = Visit.VisitNeedState.MIDDLE.ordinal();
                jC.changeStudentNeededOnPairQuery(lastClickedCell, neededBuffer);
            }
            break;
            case NEEDED_STATUS_LOW_ID: {
                neededBuffer = Visit.VisitNeedState.LOW.ordinal();
                jC.changeStudentNeededOnPairQuery(lastClickedCell, neededBuffer);
            }
            break;
            case NEEDED_STATUS_REMOVE_ID: {
                neededBuffer = Visit.VisitNeedState.NOT_SET.ordinal();
                jC.changeStudentNeededOnPairQuery(lastClickedCell, neededBuffer);
            }
            break;
            case POINT_WEIGHT_STATUS_HIGH_ID: {
                importantWeightBuffer = Visit.WeightState.OVER9000KG.ordinal();
                jC.changeStudentPointImportantStatusQuery(lastClickedCell, importantWeightBuffer);
            }
            break;
            case POINT_WEIGHT_STATUS_MIDDLE_ID: {
                importantWeightBuffer = Visit.WeightState.MIDDLE.ordinal();
                jC.changeStudentPointImportantStatusQuery(lastClickedCell, importantWeightBuffer);
            }
            break;
            case POINT_WEIGHT_STATUS_LOW_ID: {
                importantWeightBuffer = Visit.WeightState.LIGHT.ordinal();
                jC.changeStudentPointImportantStatusQuery(lastClickedCell, importantWeightBuffer);
            }
            break;
            case POINT_WEIGHT_STATUS_REMOVE_ID: {
                importantWeightBuffer = Visit.WeightState.NOT_SET.ordinal();
                jC.changeStudentPointImportantStatusQuery(lastClickedCell, importantWeightBuffer);
            }
            break;
            case SLOWER_STUDENT_STATUS_FAST_ID: {
                slowerBuffer = Visit.DelayState.FAST.ordinal();
                jC.changeStudentSlowerStatusQuery(lastClickedCell, slowerBuffer);
            }
            break;
            case SLOWER_STUDENT_STATUS_SLOW_ID: {
                slowerBuffer = Visit.DelayState.SLOW.ordinal();
                jC.changeStudentSlowerStatusQuery(lastClickedCell, slowerBuffer);
            }
            break;
            case SLOWER_STUDENT_STATUS_PUSHED_OFF_ID: {
                slowerBuffer = Visit.DelayState.BATMAN.ordinal();
                jC.changeStudentSlowerStatusQuery(lastClickedCell, slowerBuffer);
            }
            break;
            case SLOWER_STUDENT_STATUS_REMOVE_ID: {
                slowerBuffer = Visit.DelayState.NOT_SET.ordinal();
                jC.changeStudentSlowerStatusQuery(lastClickedCell, slowerBuffer);
            }
            break;
            case EXCLUSIVE_POINT_STATUS_REMOVE_ID: {
                exclusivePointStatusBuffer = Visit.MarkNeedState.NOT_SET.ordinal();
                jC.changeStudentExclusivePointStatus(lastClickedCell, exclusivePointStatusBuffer);
            }
            break;
            case EXCLUSIVE_POINT_STATUS_SET_ID: {
                exclusivePointStatusBuffer = Visit.MarkNeedState.MARKED.ordinal();
                jC.changeStudentExclusivePointStatus(lastClickedCell, exclusivePointStatusBuffer);
            }
            break;
            case PAIRS_SELECTED_BUTTON_ID: {
                jC.makeCreateExerciseQuery(Integer.valueOf(item.getTitle().toString()));
            }
            break;
            case TYPE_CHANGE_DEFAULT_LESSON_ID: {
                lessonTypeChangeBuffer = LightExercisesInfo.TypeState.NORMAL_LESSON.ordinal();
                jC.makeEditTypeQuery(lessonTypeChangeBuffer, dateSlider.getSelectedStringExerciseID());
            }
            break;
            case TYPE_CHANGE_ATTESTATION_ID: {
                lessonTypeChangeBuffer = LightExercisesInfo.TypeState.ATTESTATION.ordinal();
                jC.makeEditTypeQuery(lessonTypeChangeBuffer, dateSlider.getSelectedStringExerciseID());
            }
            break;
            case TYPE_CHANGE_CONTROL_WORK_ID: {
                lessonTypeChangeBuffer = LightExercisesInfo.TypeState.CLASS_WORK.ordinal();
                jC.makeEditTypeQuery(lessonTypeChangeBuffer, dateSlider.getSelectedStringExerciseID());
            }
            break;
            case TYPE_CHANGE_EXAM_WORK_ID: {
                lessonTypeChangeBuffer = LightExercisesInfo.TypeState.EXAM.ordinal();
                jC.makeEditTypeQuery(lessonTypeChangeBuffer, dateSlider.getSelectedStringExerciseID());
            }
            break;
            case TYPE_CHANGE_LAB_WORK_ID: {
                lessonTypeChangeBuffer = LightExercisesInfo.TypeState.LAB_WORK.ordinal();
                jC.makeEditTypeQuery(lessonTypeChangeBuffer, dateSlider.getSelectedStringExerciseID());
            }
            break;
            case TYPE_CHANGE_PRACTICAL_WORK_ID: {
                lessonTypeChangeBuffer = LightExercisesInfo.TypeState.PRACTICE.ordinal();
                jC.makeEditTypeQuery(lessonTypeChangeBuffer, dateSlider.getSelectedStringExerciseID());
            }
            break;
            case TYPE_CHANGE_SAVE_POINT_ID: {
                lessonTypeChangeBuffer = LightExercisesInfo.TypeState.TEST.ordinal();
                jC.makeEditTypeQuery(lessonTypeChangeBuffer, dateSlider.getSelectedStringExerciseID());
            }
            break;
        }
        return super.onContextItemSelected(item);
    }

    private void handlingResult(int resultCode) {
        switch (resultCode) {
            case EditJournalsCommunicator.TEACHER_LESSONS_QUERY: {
                teacherJournalInfoBar.setState(jC);
            }
            break;
            case EditJournalsCommunicator.TEACHER_JOURNAL_VISITS_QUERY: {
                setAllDataFromTeachersQueryToScreen();
            }
            break;
            case EditJournalsCommunicator.STUDENT_COMING_STATUS_PARAM: {
                lastClickedCell.changeComingStatus();
            }
            break;
            case EditJournalsCommunicator.STUDENT_RANK_CHANGE: {
                lastClickedCell.changePoint(rankBuffer);
            }
            break;
            case EditJournalsCommunicator.STUDENT_POWER_UP_STATE_QUERY: {
                lastClickedCell.changeStudentPerformance(powerBuffer);
            }
            break;
            case EditJournalsCommunicator.STUDENT_NEEDED_ON_PAIR_QUERY: {
                lastClickedCell.changeNeededStatus(neededBuffer);
            }
            break;
            case EditJournalsCommunicator.STUDENT_POINT_IMPORTANT_STATUS_QUERY: {
                lastClickedCell.changePointWeight(importantWeightBuffer);
            }
            break;
            case EditJournalsCommunicator.STUDENT_SLOWER_STATUS_QUERY: {
                lastClickedCell.changeStudentSlowStatus(slowerBuffer);
            }
            break;
            case EditJournalsCommunicator.STUDENT_EXCLUSIVE_STATUS_POINT_QUERY: {
                lastClickedCell.changePointMark(exclusivePointStatusBuffer);
            }
            break;
            case EditJournalsCommunicator.STUDENT_REMOVE_POINT_STATUS_QUERY: {
                importantWeightBuffer = Visit.WeightState.NOT_SET.ordinal();
                exclusivePointStatusBuffer = Visit.MarkNeedState.NOT_SET.ordinal();
                lastClickedCell.changePoint(rankBuffer);
                lastClickedCell.changePointWeight(importantWeightBuffer);
                lastClickedCell.changePointMark(exclusivePointStatusBuffer);
            }
            break;
            case EditJournalsCommunicator.TEACHER_CREATE_NEW_EXERCISE_QUERY: {
                setAllDataFromTeachersQueryToScreen();
            }
            break;
            case EditJournalsCommunicator.TEACHER_DELETE_EXERCISE_QUERY: {
                handleTeacherDeleteExercise();
            }
            break;
            case EditJournalsCommunicator.TEACHER_EDIT_TYPE_EXERCISE_QUERY: {
                handleTeacherEditExerciseType();
            }
            break;
        }
    }

    private void handleTeacherEditExerciseType() {
        jC.changeExerciseType(lessonTypeChangeBuffer, dateSlider.getSelectedExerciseID());
        dateSlider.unmarkedAny();
        dateSlider.setStateBackColor();
        setAllDataFromTeachersQueryToScreen();
    }

    private void handleTeacherDeleteExercise() {
        jC.makeRemoveLastSelectedForDeleteExercises();
        dateSlider.unmarkedAny();
        dateSlider.removeBackColor();
        setAllDataFromTeachersQueryToScreen();
    }

    private void setAllDataFromTeachersQueryToScreen() {
        studentList.setStudents(jC.getStudents());
        dateSlider.setData(jC.getLightExercisesInfo());
        tableWithMarks.createTable(jC.getLightVisits(), jC.getStudents());
        groupSelectorSeparateLine.setVisibility(View.VISIBLE);
    }

    private void handleCellLongClick(EvolutionCell cell) {
        if (!cell.isEmpty()) {
            this.lastClickedCell = cell;
            registerForContextMenu(cell);
            openContextMenu(cell);
        }
    }

    private void setEditJournalMenu(ContextMenu menu) {
        switch (lastJournalAction) {
            case RightSideJournalMenu.EDIT: {
                handleEditClick(menu);
            }
            break;
            case RightSideJournalMenu.PLUS: {
                handlePlusClick(menu);
            }
            break;
            case RightSideJournalMenu.REMOVE: {
                handleRemoveClick();
            }
            break;
        }
    }

    private void handleEditClick(ContextMenu menu) {
        if (!dateSlider.hasMarked()) {
            Toast.makeText(this, SELECT_DATE_FOR_EDIT_MESSAGE, Toast.LENGTH_SHORT).show();
        } else {
            setEditMenu(menu);
        }
    }

    private void setEditMenu(ContextMenu menu) {
        menu.setHeaderTitle(EDIT_MENU_TITLE);
        SubMenu typeChange = menu.addSubMenu(TYPE_LESSON_MESSAGE);
        typeChange.add(Menu.NONE, TYPE_CHANGE_DEFAULT_LESSON_ID, Menu.NONE, TYPE_CHANGE_DEFAULT_LESSON_TITLE);
        typeChange.add(Menu.NONE, TYPE_CHANGE_LAB_WORK_ID, Menu.NONE, TYPE_CHANGE_LAB_WORK_TITLE);
        typeChange.add(Menu.NONE, TYPE_CHANGE_PRACTICAL_WORK_ID, Menu.NONE, TYPE_CHANGE_PRACTICAL_LESSON_TITLE);
        typeChange.add(Menu.NONE, TYPE_CHANGE_CONTROL_WORK_ID, Menu.NONE, TYPE_CHANGE_CONTROL_WORK_TITLE);
        typeChange.add(Menu.NONE, TYPE_CHANGE_ATTESTATION_ID, Menu.NONE, TYPE_CHANGE_ATTESTATION_TITLE);
        typeChange.add(Menu.NONE, TYPE_CHANGE_SAVE_POINT_ID, Menu.NONE, TYPE_CHANGE_SAVE_POINT_TITLE);
        typeChange.add(Menu.NONE, TYPE_CHANGE_EXAM_WORK_ID, Menu.NONE, TYPE_CHANGE_EXAM_WORK_TITLE);
    }

    private void handleRemoveClick() {
        if (!dateSlider.hasMarked()) {
            Toast.makeText(this, SELECT_DATE_FOR_DELETE_MESSAGE, Toast.LENGTH_SHORT).show();
        } else {
            jC.makeDeleteExercise(dateSlider.getSelectedStringExerciseID());
        }
    }

    private void handlePlusClick(ContextMenu menu) {
        menu.addSubMenu(PAIR_SELECTED_TITLE);
        for (int i = MIN_AVAILABLE_PAIR; i <= MAX_AVAILABLE_PAIR; i++) {
            menu.add(Menu.NONE, PAIRS_SELECTED_BUTTON_ID, Menu.NONE, String.valueOf(i));
        }
    }

    private void setCellClickedMenu(ContextMenu menu) {
        addRankOptions(menu);
        if (lastClickedCell.hasPoint())
            addImportantPointWeight(menu);
        addPowerUp(menu);
        addStudentNeeded(menu);
        if (lastClickedCell.hasPoint())
            addExclusiveImportantStatusPoint(menu);
        addStudentSlowerStatus(menu);
    }

    private void addExclusiveImportantStatusPoint(ContextMenu menu) {
        SubMenu weightStatus = menu.addSubMenu(EXCLUSIVE_POINT_STATUS_TITLE);
        weightStatus.add(Menu.NONE, EXCLUSIVE_POINT_STATUS_SET_ID, Menu.NONE, EXCLUSIVE_POINT_STATUS_SET_TITLE);
        weightStatus.add(Menu.NONE, EXCLUSIVE_POINT_STATUS_REMOVE_ID, Menu.NONE, REMOVE_CELL_STATE_TITLE);
    }

    private void addImportantPointWeight(ContextMenu menu) {
        SubMenu weightStatus = menu.addSubMenu(WEIGHT_POINT_STATUS_TITLE);
        weightStatus.add(Menu.NONE, POINT_WEIGHT_STATUS_HIGH_ID, Menu.NONE, POINT_WEIGHT_HIGH_STATUS_TITLE);
        weightStatus.add(Menu.NONE, POINT_WEIGHT_STATUS_MIDDLE_ID, Menu.NONE, POINT_WEIGHT_MIDDLE_STATUS_TITLE);
        weightStatus.add(Menu.NONE, POINT_WEIGHT_STATUS_LOW_ID, Menu.NONE, POINT_WEIGHT_LOW_STATUS_TITLE);
        weightStatus.add(Menu.NONE, POINT_WEIGHT_STATUS_REMOVE_ID, Menu.NONE, REMOVE_CELL_STATE_TITLE);
    }

    private void addStudentSlowerStatus(ContextMenu menu) {
        SubMenu slowerStatus = menu.addSubMenu(STUDENT_SLOWER_TITLE);
        slowerStatus.add(Menu.NONE, SLOWER_STUDENT_STATUS_SLOW_ID, Menu.NONE, SLOWER_STUDENT_STATUS_SLOW_TITLE);
        slowerStatus.add(Menu.NONE, SLOWER_STUDENT_STATUS_FAST_ID, Menu.NONE, SLOWER_STUDENT_STATUS_FAST_TITLE);
        slowerStatus.add(Menu.NONE, SLOWER_STUDENT_STATUS_PUSHED_OFF_ID, Menu.NONE, SLOWER_STUDENT_STATUS_PUSHED_OFF_TITLE);
        slowerStatus.add(Menu.NONE, SLOWER_STUDENT_STATUS_REMOVE_ID, Menu.NONE, REMOVE_CELL_STATE_TITLE);
    }

    private void addStudentNeeded(ContextMenu menu) {
        SubMenu neededStudent = menu.addSubMenu(NEEDED_STATUS_TITLE);
        neededStudent.add(Menu.NONE, NEEDED_STATUS_HIGH_ID, Menu.NONE, HIGH_NEEDED_STUDENT_TITLE);
        neededStudent.add(Menu.NONE, NEEDED_STATUS_MIDDLE_ID, Menu.NONE, MIDDLE_NEEDED_STUDENT_TITLE);
        neededStudent.add(Menu.NONE, NEEDED_STATUS_LOW_ID, Menu.NONE, LOW_NEEDED_STUDENT_TITLE);
        neededStudent.add(Menu.NONE, NEEDED_STATUS_REMOVE_ID, Menu.NONE, REMOVE_CELL_STATE_TITLE);
    }

    private void addPowerUp(ContextMenu menu) {
        SubMenu powerUP = menu.addSubMenu(POWER_UP_TITLE);
        powerUP.add(Menu.NONE, POWER_UP_IS_HIGH_ID, Menu.NONE, STUDENT_POWER_HIGH_TITLE);
        powerUP.add(Menu.NONE, POWER_UP_IS_LOW_ID, Menu.NONE, STUDENT_POWER_LOW_TITLE);
        powerUP.add(Menu.NONE, POWER_UP_REMOVE_ID, Menu.NONE, REMOVE_CELL_STATE_TITLE);
    }

    private void addRankOptions(Menu menu) {
        SubMenu rank = menu.addSubMenu(RANK);
        for (int i = 2; i <= 5; i++) {
            rank.add(Menu.NONE, i, Menu.NONE, String.valueOf(i));
        }
        rank.add(Menu.NONE, RANK_CHANGE_REMOVE_ID, Menu.NONE, REMOVE_CELL_STATE_TITLE);
    }

    private void handleCellShortClick(EvolutionCell cell) {
        if (!cell.isEmpty()) {
            this.lastClickedCell = cell;
            jC.makeStudentComingCellStatusChangeQuery(cell);
        }
    }

    private void handleDateElementClick(DateElement element) {
        lastExerciseSelected = element.getTableIndex();
        setColorOnColumn(Color.GREEN, element.getCellColor());
    }

    private void setColorOnColumn(int color, int backColor) {
        dateSlider.markEditableExercise(lastExerciseSelected, color, backColor);
    }
}
