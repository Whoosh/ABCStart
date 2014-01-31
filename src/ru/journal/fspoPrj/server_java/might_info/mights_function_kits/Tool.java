package ru.journal.fspoPrj.server_java.might_info.mights_function_kits;

import ru.journal.fspoPrj.server_java.might_info.Functions;

public class Tool {

    private final Class<?> toolClass;
    private final String toolText;
    private final int iconID;

    public Tool(Functions function) {
        this.toolClass = function.getToolClass();
        this.toolText = function.getToolName();
        this.iconID = function.getToolIconID();
    }

    public Class<?> getToolClass() {
        return toolClass;
    }

    public String getToolText() {
        return toolText;
    }

    public int getIconID() {
        return iconID;
    }
}
