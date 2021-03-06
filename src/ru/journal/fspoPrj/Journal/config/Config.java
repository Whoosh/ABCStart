package ru.journal.fspoPrj.journal.config;

import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;


public abstract class Config extends GlobalConfig {

    private static int selectorGroupWidth;
    private static int selectorGroupHeight;

    private static int onGroupDialogSeparateLineHeight;
    private static int onGroupDialogTransparentSeparateLineHeight;
    private static int onGroupDialogElementTransparentSeparateLineSize;
    private static int onDialogGroupElementWidth;
    private static int onDialogGroupElementHeight;

    private static int dateDialogElementWidth;
    private static int dateDialogElementHeight;

    private static int evolutionCellSize;
    private static int journalEndLineWidth;

    private static float selectorGroupTextSize;
    private static float groupElementTextSize;
    private static float studentElementTextSize;
    private static float dateSliderTextSize;
    private static float evolutionTextSize;
    private static float dateDialogElementTextSize;
    private static float teacherJournalDialogButtonsTextSize;
    private static int functionDialogTransparentSeparateLineHeight;
    private static int cellFunctionDialogHeight;
    private static int cellFunctionDialogWidth;
    private static int groupSelectorDialogWidth;
    private static int groupSelectorDialogHeight;
    private static float groupSelectorDialogTitleTextSize;
    private static float editJournalLessonLabelTextSize;


    public static void setDefaultElementSize() {
        groupElementTextSize = getTextSize(R.dimen.looking_journal__group_element_text_size);
        selectorGroupTextSize = getTextSize(R.dimen.looking_journal__selector_group_text_size);
        studentElementTextSize = getTextSize(R.dimen.looking_journal__student_element_text_size);
        evolutionTextSize = getTextSize(R.dimen.looking_journal__evolution_element_text_size);
        dateSliderTextSize = getTextSize(R.dimen.looking_journal__date_element_text_size);
        dateDialogElementTextSize = getTextSize(R.dimen.looking_journal__on_date_dialog_element_text_size);
        teacherJournalDialogButtonsTextSize = getTextSize(R.dimen.teacher_journal__dialog_buttons_text_size);
        groupSelectorDialogTitleTextSize = getTextSize(R.dimen.journal__group_title_dialog_text_size);
        editJournalLessonLabelTextSize = getTextSize(R.dimen.teacher_journal__lesson_label__text_size);

        evolutionCellSize = getRealSize(R.integer.looking_journal__evolution_size);
        selectorGroupHeight = getRealSize(R.integer.looking_journal__selector_group_height);
        selectorGroupWidth = getRealSize(R.integer.looking_journal__selector_group_width);

        onGroupDialogElementTransparentSeparateLineSize = getRealSize(R.integer.looking_journal__on_group_dialog_element_separate_line_size);
        onGroupDialogTransparentSeparateLineHeight = getRealSize(R.integer.looking_journal__on_group_dialog_separate_transparent_line_height);
        onGroupDialogSeparateLineHeight = getRealSize(R.integer.looking_journal__on_group_dialog_separate_line_height);

        onDialogGroupElementHeight = getRealSize(R.integer.looking_journal__on_group_dialog_element_group_height);
        onDialogGroupElementWidth = getRealSize(R.integer.looking_journal__on_group_dialog_element_group_width);

        journalEndLineWidth = getRealSize(R.integer.looking_journal__journal_end_line_width);

        dateDialogElementHeight = getRealSize(R.integer.looking_journal__on_date_dialog_element_height);
        dateDialogElementWidth = getRealSize(R.integer.looking_journal__on_date_dialog_element_width);

        functionDialogTransparentSeparateLineHeight = getRealSize(R.integer.teacher_journal_cell_dialog_transparent_separator_line_height);
        cellFunctionDialogHeight = getRealSize(R.integer.teacher_journal_cell_dialog_height);
        cellFunctionDialogWidth = getRealSize(R.integer.teacher_journal_cell_dialog_weight);

        groupSelectorDialogHeight = getRealSize(R.integer.journal__group_dialog_height);
        groupSelectorDialogWidth = getRealSize(R.integer.journal__group_dialog_width);
    }

    public static void setMatrixThemeColors() {

    }

    public static void setNormalThereColors() {

    }


    public static float getSelectorGroupTextSize() {
        return selectorGroupTextSize;
    }

    public static int getSelectorGroupOrLabelWidth() {
        return selectorGroupWidth;
    }

    public static int getSelectorGroupOrLabelHeight() {
        return selectorGroupHeight;
    }

    public static int getOnGroupDialogSeparateLineHeight() {
        return onGroupDialogSeparateLineHeight;
    }

    public static int getOnGroupDialogTransparentSeparateLineHeight() {
        return onGroupDialogTransparentSeparateLineHeight;
    }

    public static int getOnGroupDialogElementTransparentSeparateLineSize() {
        return onGroupDialogElementTransparentSeparateLineSize;
    }

    public static float getGroupElementTextSize() {
        return groupElementTextSize;
    }

    public static int getOnDialogGroupElementWidth() {
        return onDialogGroupElementWidth;
    }

    public static int getOnDialogGroupElementHeight() {
        return onDialogGroupElementHeight;
    }

    public static int getEvolutionCellSize() {
        return evolutionCellSize;
    }

    public static float getStudentElementTextSize() {
        return studentElementTextSize;
    }

    public static int getStudentElementHeight() {
        return getEvolutionCellSize();
    }

    public static int getStudentElementWidth() {
        return getSelectorGroupOrLabelWidth();
    }

    public static int getSliderDateElementWidth() {
        return getEvolutionCellSize();
    }

    public static int getSliderDateElementHeight() {
        return getEvolutionCellSize();
    }

    public static float getDateSliderTextSize() {
        return dateSliderTextSize;
    }

    public static int getEvolutionTextSize() {
        return (int) evolutionTextSize;
    }

    public static int getJournalEndLineWidth() {
        return journalEndLineWidth;
    }

    public static float getDateDialogElementTextSize() {
        return dateDialogElementTextSize;
    }

    public static int getDateDialogElementWidth() {
        return dateDialogElementWidth;
    }

    public static int getDateDialogElementHeight() {
        return dateDialogElementHeight;
    }

    public static float getTeacherJournalDialogButtonsTextSize() {
        return teacherJournalDialogButtonsTextSize;
    }

    public static int getFunctionDialogTransparentSeparateLineHeight() {
        return functionDialogTransparentSeparateLineHeight;
    }

    public static int getCellFunctionDialogHeight() {
        return cellFunctionDialogHeight;
    }

    public static int getCellFunctionDialogWidth() {
        return cellFunctionDialogWidth;
    }

    public static int getGroupSelectorDialogWidth() {
        return groupSelectorDialogWidth;
    }

    public static int getGroupSelectorDialogHeight() {
        return groupSelectorDialogHeight;
    }

    public static float getGroupSelectorDialogTitleTextSize() {
        return groupSelectorDialogTitleTextSize;
    }

    public static float getEditJournalLessonLabelTextSize() {
        return editJournalLessonLabelTextSize;
    }
}
