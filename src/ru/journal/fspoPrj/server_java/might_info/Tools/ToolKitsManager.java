package ru.journal.fspoPrj.server_java.might_info.Tools;

import android.os.Parcel;
import android.os.Parcelable;
import org.jetbrains.annotations.Nullable;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.might_info.CurrentRolesInfo;
import ru.journal.fspoPrj.server_java.might_info.Functions;

import java.io.Serializable;

public class ToolKitsManager implements Serializable {

    private Tool[] tools;
    private int indexer;

    public ToolKitsManager(Functions... functions) {
        this.tools = new Tool[functions.length];
        for (Functions function : functions) {
            tools[indexer++] = new Tool(function);
        }
    }

    public ToolKitsManager(Parcel parcel) {
        tools = (Tool[]) parcel.readSerializable();
        indexer = parcel.readInt();
    }

    public Tool[] getTools() {
        return tools;
    }

    public int size() {
        return tools.length;
    }

    public Tool getTool(String toolName) {
        for (Tool tool : tools) {
            if (tool.getName().equals(toolName)) {
                return tool;
            }
        }
        try {
            throw new NoFunctionException();
        } catch (NoFunctionException e) {
            Logger.printError(e, getClass());
            return new Tool(Functions.ERROR_INFORMER);
        }
    }

    public String[] getToolsName() {
        String[] result = new String[tools.length];
        for (int i = 0; i < tools.length; i++) {
            result[i] = tools[i].getName();
        }
        return result;
    }

    private static class NoFunctionException extends Exception {
    }
}
