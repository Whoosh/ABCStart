package ru.journal.fspoPrj.server_java.might_info.mights_function_kits;

import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.might_info.Functions;

public class ToolKitsManager {

    private final int[] toolsIndexes;

    public ToolKitsManager(int... toolsIndexes) {
        this.toolsIndexes = toolsIndexes;
    }

    public String getToolName(int index) {
        return getFunction(index).getToolName();
    }

    public int getToolsCount() {
        return toolsIndexes.length;
    }

    public Tool getTool(int index) {
        return new Tool(getFunction(index));
    }

    public Tool getTool(String toolName) {
        for (Functions function : Functions.values()) {
            if (function.getToolName().equals(toolName)) {
                return new Tool(function);
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
        String[] result = new String[toolsIndexes.length];
        for (int i = 0; i < toolsIndexes.length; i++) {
            result[i] = getToolName(i);
        }
        return result;
    }

    private Functions getFunction(int index) {
        return Functions.values()[toolsIndexes[index % toolsIndexes.length]];
    }

    private class NoFunctionException extends Exception {
    }
}
