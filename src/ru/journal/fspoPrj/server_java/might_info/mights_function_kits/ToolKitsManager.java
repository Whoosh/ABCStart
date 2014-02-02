package ru.journal.fspoPrj.server_java.might_info.mights_function_kits;

import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.might_info.Functions;

import java.io.Serializable;

public class ToolKitsManager implements Serializable {

    private Tool[] tools;

    public ToolKitsManager(int... toolsIndexes) {
        this.tools = new Tool[toolsIndexes.length];
        for (int i = 0; i < tools.length; i++) {
            tools[i] = new Tool(Functions.values()[toolsIndexes[i]]);
        }
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

    private class NoFunctionException extends Exception {
    }
}
