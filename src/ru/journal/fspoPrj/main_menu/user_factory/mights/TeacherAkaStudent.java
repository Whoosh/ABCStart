package ru.journal.fspoPrj.main_menu.user_factory.mights;

import ru.journal.fspoPrj.main_menu.user_factory.Functions;
import ru.journal.fspoPrj.main_menu.user_factory.InfoGetter;

public class TeacherAkaStudent implements InfoGetter {

    private int[] indexing = {
            Functions.JOURNALS.ordinal(),
            Functions.PROFILE.ordinal(),
            Functions.ERROR_INFORMER.ordinal()};

    @Override
    public Class<?> getToolClass(int index) {
        return Functions.values()[indexing[index % indexing.length]].getToolClass();
    }

    @Override
    public String getToolName(int index) {
        return Functions.values()[indexing[index % indexing.length]].getToolName();
    }

    @Override
    public int getToolsCount() {
        return indexing.length;
    }
}
