package ru.journal.fspoPrj.server_java.might_info.mights_function_kits;

import ru.journal.fspoPrj.server_java.might_info.Functions;
import ru.journal.fspoPrj.server_java.might_info.Tools.ToolKitsManager;

public class Teacher extends ToolKitsManager {

    public Teacher() {
        super(
                Functions.LOOK_JOURNALS,
                Functions.PROFILE,
                Functions.ERROR_INFORMER); // TODO
    }
}
