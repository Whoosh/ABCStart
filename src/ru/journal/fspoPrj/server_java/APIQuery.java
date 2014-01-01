package ru.journal.fspoPrj.server_java;

import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

public enum APIQuery {

    AUTHORIZATION("GET /api/authentication/?login=", "&pass="),
    LOG_OUT("GET /api/logout/?ssid="),
    GET_PROFILE("GET /api/getProfile/?ssid=", "&user_id="),
    GET_MIGHT("GET /api/getRoles/?ssid=", "&user_id="),
    EMPTY_QUERY("GET /api");

    private final String[] key;

    private APIQuery(String... key) {
        this.key = key;
    }

    // при несовподении параметров, получим эксепшен, TODO
    public String getLink(String... values) {
        if (values.length == 0) return key[0];
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            builder.append(key[i]);
            builder.append(values[i]);
        }
        return builder.toString();
    }
}
