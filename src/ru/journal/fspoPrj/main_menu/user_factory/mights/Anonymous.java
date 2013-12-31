package ru.journal.fspoPrj.main_menu.user_factory.mights;

import ru.journal.fspoPrj.main_menu.user_factory.Functions;
import ru.journal.fspoPrj.main_menu.user_factory.InfoGetter;

public class Anonymous implements InfoGetter {

    private int[] indexing = {Functions.ERROR_INFORMER.ordinal()};

    @Override
    public Class<?> getToolClass(int index) {
        try {
            return Functions.values()[indexing[index]].getToolClass();
        } catch (Exception e) {
            return Functions.values()[indexing[index % indexing.length]].getToolClass();
        }
    }

    @Override
    public String getToolName(int index) {
        try {
            return Functions.values()[indexing[index]].getToolName();
        } catch (Exception e) {
            return Functions.values()[indexing[index % indexing.length]].getToolName();
        }
    }

    @Override
    public int getToolsCount() {
        return indexing.length;
    }
}
