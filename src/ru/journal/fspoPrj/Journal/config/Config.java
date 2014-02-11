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

    private static int evolutionCellSize;
    private static int journalEndLineWidth;

    private static float selectorGroupTextSize;
    private static float groupElementTextSize;
    private static float studentElementTextSize;
    private static float dateTextSize;
    private static float evolutionTextSize;


    public static void setDefaultElementSize() {
        groupElementTextSize = getTextSize(R.dimen.looking_journal__group_element_text_size);
        selectorGroupTextSize = getTextSize(R.dimen.looking_journal__selector_group_text_size);
        studentElementTextSize = getTextSize(R.dimen.looking_journal__student_element_text_size);
        evolutionTextSize = getTextSize(R.dimen.looking_journal__evolution_element_text_size);
        dateTextSize = getTextSize(R.dimen.looking_journal__date_element_text_size);

        evolutionCellSize = getRealSize(R.integer.looking_journal__evolution_size);
        selectorGroupHeight = getRealSize(R.integer.looking_journal__selector_group_height);
        selectorGroupWidth = getRealSize(R.integer.looking_journal__selector_group_width);

        onGroupDialogElementTransparentSeparateLineSize = getRealSize(R.integer.looking_journal__on_group_dialog_element_separate_line_size);
        onGroupDialogTransparentSeparateLineHeight = getRealSize(R.integer.looking_journal__on_group_dialog_separate_transparent_line_height);
        onGroupDialogSeparateLineHeight = getRealSize(R.integer.looking_journal__on_group_dialog_separate_line_height);

        onDialogGroupElementHeight = getRealSize(R.integer.looking_journal__on_group_dialog_element_group_height);
        onDialogGroupElementWidth = getRealSize(R.integer.looking_journal__on_group_dialog_element_group_width);

        journalEndLineWidth = getRealSize(R.integer.looking_journal__journal_end_line_width);
    }

    public static void setMatrixThemeColors() {

    }

    public static void setNormalThereColors() {

    }


    public static float getSelectorGroupTextSize() {
        return selectorGroupTextSize;
    }

    public static int getSelectorGroupWidth() {
        return selectorGroupWidth;
    }

    public static int getSelectorGroupHeight() {
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
        return getSelectorGroupWidth();
    }

    public static int getDateElementWidth() {
        return getEvolutionCellSize();
    }

    public static int getDateElementHeight() {
        return getEvolutionCellSize();
    }

    public static float getDateTextSize() {
        return dateTextSize;
    }

    public static int getEvolutionTextSize() {
        return (int) evolutionTextSize;
    }

    public static int getJournalEndLineWidth() {
        return journalEndLineWidth;
    }
}
