package ru.journal.fspoPrj.server_java.server_info;

public enum ServerErrors {

    SERVER_TTL_QUERY_ERROR("Сервер не отвечает, проверьте подлючение и попробуйте ещё раз"),
    LOGIN_OR_PASSWORD_ERROR("Неверный логин или пароль"),
    SERVER_IS_DOWN("Отсутсвует подключение");

    private final String message;

    private ServerErrors(String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }
}
