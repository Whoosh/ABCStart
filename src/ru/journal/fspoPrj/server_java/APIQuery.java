package ru.journal.fspoPrj.server_java;

import ru.journal.fspoPrj.public_code.Logger;

public enum APIQuery {

    AUTHORIZATION("GET /api/authentication/?login=", "&pass="),
    LOG_OUT("GET /api/logout/?ssid="),
    GET_PROFILE("GET /api/getProfile/?ssid=", "&user_id="),
    GET_MIGHT("GET /api/getRoles/?ssid=", "&user_id="),
    EMPTY_QUERY("GET /api");

    private final String[] keys;

    private APIQuery(String... keys) {
        this.keys = keys;
    }

    public String getLink(String... values) {
        if (values.length == 0) return keys[0];
        StringBuilder builder = new StringBuilder();
        try {
            for (int i = 0; i < values.length; i++) {
                builder.append(keys[i]);
                builder.append(values[i]);
            }
            return builder.toString();
        } catch (ArrayIndexOutOfBoundsException ex) {
            Logger.printError(ex, getClass());
            return EMPTY_QUERY.toString();
        }
    }
}
