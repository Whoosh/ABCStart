package ru.journal.fspoPrj.server_java.server_info;

public enum ClientInfo {

    USER_CLIENT_INFO("Agent: ArmyOfDroid\n"),
    HTTP_VERSION(" HTTP/1.0"),
    HOST_INFO("Host: fspo.segrys.ru"),
    CLIENT_INFO(" HTTP/1.0\nHost: fspo.segrys.ru\nAgent: ArmyOfDroid\n");

    private final String data;

    private ClientInfo(String data) {
        this.data = data;
    }

    public String get() {
        return data;
    }
}
