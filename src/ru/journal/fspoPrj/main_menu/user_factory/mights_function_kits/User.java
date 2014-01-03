package ru.journal.fspoPrj.main_menu.user_factory.mights_function_kits;

import ru.journal.fspoPrj.main_menu.user_factory.Functions;
import ru.journal.fspoPrj.main_menu.user_factory.ToolsGetter;

public class User implements ToolsGetter{

    private final int[] tools;

    public User(int... tools) {
        this.tools = tools;
    }

    @Override
    public Class<?> getToolClass(int index) {
        return Functions.values()[tools[index % tools.length]].getToolClass();
    }

    @Override
    public String getToolName(int index) {
        return Functions.values()[tools[index % tools.length]].getToolName();
    }

    @Override
    public int getToolsCount() {
        return tools.length;
    }
}
