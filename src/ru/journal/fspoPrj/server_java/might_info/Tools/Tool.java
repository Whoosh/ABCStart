package ru.journal.fspoPrj.server_java.might_info.Tools;

import ru.journal.fspoPrj.server_java.might_info.Functions;

import java.io.Serializable;

public class Tool implements Serializable {

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

    public String getName() {
        return toolText;
    }

    public int getIconID() {
        return iconID;
    }
}
