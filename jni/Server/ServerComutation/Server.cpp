#include "Server.h"

Server::Server(const char *name, const char *password) {
    hostPort = 80;
    host = "fspo.segrys.ru";
    if (SDLNet_Init() == -1) {
        logger("%s", "Fatal error SDLnet_Init faill");
        exit(0);
    }
    setToken(name, password);
}

Server::Server(const char *name, const char *password, const char *proxyHost, unsigned short int port) {
    hostPort = port;
    host = proxyHost;
    if (SDLNet_Init() == -1) {
        logger("%s", "Fatal error SDLnet_Init faill");
        exit(0);
    }
    setToken(name, password);
}

Server::~Server() {
    logOut();
    SDLNet_Quit();
}

void Server::setToken(const char* name, const char* password) {
    token.clear();
    char *linkQuery = (char*) new char[AUTH_FORM_SIZE + AUTH_PASS_PARAM_SIZE + strlen(name) + strlen(password) + ONE];
    memset(linkQuery, 0, strlen(linkQuery));
    strcat(linkQuery, AUTH_FORM_STRING);
    strcat(linkQuery, name);
    strcat(linkQuery, AUTH_PASS_PARAM_STRING);
    strcat(linkQuery, password);
    sendQuery(linkQuery); // послали запрос 
    setTokenFromAnswer(); // записали токен в токен -_-
    delete[] linkQuery;
}

void Server::sendQuery(const char* linkQuery) {
    answer.clear();
    if (SDLNet_ResolveHost(&hostIP, host, hostPort)) {
        errorState = SERVER_RESOLVE_ERROR;
        printErrorState(errorState, __LINE__);
        return;
    }
    socket = SDLNet_TCP_Open(&hostIP);
    logger("%d", socket);
    if (socket == NULL) {
        errorState = SOCKET_OPEN_ERROR;
        printErrorState(errorState, __LINE__);
        logger("%s", SDLNet_GetError());
        exit(0);
        return;
    }
    char *query = (char *) new char[HEAD_QUERY_SIZE + strlen(linkQuery) + TAIL_QUERY_SIZE];
    char *answerBuffer = (char *) new char[STANDARD_QUERY_SIZE];
    memset(query, 0, strlen(query));

    strcat(query, HEAD_QUERY_STRING);
    strcat(query, linkQuery);
    strcat(query, TAIL_QUERY_STRING);

    if (!(SDLNet_TCP_Send(socket, query, strlen(query)) != strlen(query))) // Ушли ли все данные? да-спрашиваем нет-в логгер.
        while (true) { // пока данные поступают.
            counters = SDLNet_TCP_Recv(socket, answerBuffer, STANDARD_QUERY_SIZE / 6); // 6 - unicodeLen 
            if (counters < 0) {
                errorState = SOCKET_CONNECT_ERROR;
                printErrorState(errorState, __LINE__);
                break;
            }
            if (counters == 0) {
                errorState = RECV_CLOSE_SUCCESSFUL;
                printErrorState(errorState, __LINE__);
                break;
            }
            for (unsigned int i = 0; i < counters; i++) answer.push_back(answerBuffer[i]);
        } else {
        errorState = QUERY_SEND_ERROR;
        printErrorState(errorState, __LINE__);

    }
    delete[] query;
    delete[] answerBuffer;
    SDLNet_TCP_Close(socket);
    socket = NULL;
}

void Server::printAnswer() {
    logger("\nAnswer = ");
    logger("%s", answer.c_str());
    //    for (int i = 0; i < answer.size(); i++) logger("%c", answer[i]);
    logger("\n");
}

void Server::printToken() {
    logger("\nToken = ");
    logger("%s", token.c_str());
    // for (int i = 0; i < token.size(); i++) logger("%c", token[i]);
    logger("\n");
}

void Server::setTokenFromAnswer() {
    if (errorState != RECV_CLOSE_SUCCESSFUL) return; // по какой либо причине запрос небыл получен, обрабатывать нечего.
    // +4 размер ssid, +3 размер разделителя ":" << нет смысла где-то описывать
    if (answer.rfind("null") == -ONE) { // верёт -1 если нет пустого токена т.е -1 > есть что брать 
        for (unsigned int i = answer.rfind("ssid") + 4 + 3; answer[i] != '\"'; i++)
            token.push_back(answer[i]);
        errorState = TOKEN_GET_SUCCESSFUL;
        printErrorState(errorState, __LINE__);
    } else {
        errorState = PASSWORD_LOGIN_ERROR;
        printErrorState(errorState, __LINE__);
    }
}

int Server::getErrorState() {
    return errorState;
}

bool Server::checkConnection() {
    sendQuery("/ ");
    return (errorState == RECV_CLOSE_SUCCESSFUL);
}

void Server::logOut() {
    char *logOutLink = (char*) new char[LOG_OUT_FORM_SIZE + token.size()];
    memset(logOutLink, 0, strlen(logOutLink));
    strcat(logOutLink, LOG_OUT_FORM_STRING);
    strcat(logOutLink, token.c_str());
    sendQuery(logOutLink);
    token.clear();
    answer.clear();
    delete[] logOutLink;
}

const char *Server::getProfileJSONString() {
    if (token.empty()) {
        errorState = EMPTY_TOKEN_ERROR;
        printErrorState(errorState, __LINE__);
        return token.c_str(); // вернём указатель в пустоту. от токена а не от ответа.(ответ может содержать какие-то данные)
    } // в продакшн версии с проверкой в java на удачный запрос токена, можно удалить.
    char *profileLink = (char*) new char[PROFILE_FORM_SIZE + token.size()];
    memset(profileLink, 0, strlen(profileLink));
    strcat(profileLink, PROFILE_FORM_STRING);
    strcat(profileLink, token.c_str());
    sendQuery(profileLink);
    clearAnswerHead();
    delete[] profileLink;
    return answer.c_str();
}

void Server::clearAnswerHead() {
    answer.erase(0, answer.find_first_of("{"));
}