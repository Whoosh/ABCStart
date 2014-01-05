package ru.journal.fspoPrj.user_profile.elements;

import ru.journal.fspoPrj.server_java.profile_info.JsonKeys;

public enum TextInfo {

    PREFIX_FIRST_NAME("\tИмя : ", JsonKeys.FIRST_NAME.ordinal()),
    PREFIX_MIDDLE_NAME("\tОтчество : ", JsonKeys.MIDDLE_NAME.ordinal()),
    PREFIX_LAST_NAME("\tФамилия : ", JsonKeys.LAST_NAME.ordinal()),
    PREFIX_PHONE("\t\tТелефон : ", JsonKeys.PHONE.ordinal()),
    PREFIX_MAIL("\t\tПочта : ", JsonKeys.EMAIL.ordinal());

    private final String visualKey;
    private final int chainKey;

    private TextInfo(String visualKey, int chain) {
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
