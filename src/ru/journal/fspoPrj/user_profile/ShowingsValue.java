package ru.journal.fspoPrj.user_profile;

import ru.journal.fspoPrj.server_java.profile_info.JsonValues;

public enum ShowingsValue {

    PREFIX_FIRST_NAME("\tИмя : ", JsonValues.FIRST_NAME.ordinal()),
    PREFIX_MIDDLE_NAME("\tОтчество : ", JsonValues.MIDDLE_NAME.ordinal()),
    PREFIX_LAST_NAME("\tФамилия : ", JsonValues.LAST_NAME.ordinal()),
    PREFIX_PHONE("\t\tТелефон : ", JsonValues.PHONE.ordinal()),
    PREFIX_MAIL("\t\tПочта : ", JsonValues.EMAIL.ordinal());

    private final String visualKey;
    private int chainKey;

    private ShowingsValue(String visualKey, int chain) {
        this.visualKey = visualKey;
        this.chainKey = chain;
    }

    public String getVisualKey() {
        return visualKey;
    }

    public int getChainKey() {
        return chainKey;
    }
}
