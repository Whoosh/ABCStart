#include <iostream>
#include <vector>
#include <cstring>
#include <SDL.h>
#include <SDL_net.h>
#include <stdio.h>
#include "Logger.h"
#include "APIStrings.h"

#ifndef SERVER_H
#define	SERVER_H

#define STANDARD_QUERY_SIZE 512
#define ONE 1

class Server {
private:
    std::basic_string<char> answer; // буфер для ответов
    std::basic_string<char> token; // токен для запросов 
    unsigned short int hostPort;
    const char *host;
    TCPsocket socket;
    IPaddress hostIP;
    int counters;
    int errorState;

public:
    Server(const char *name, const char *password);
    Server(const char *name, const char *password, const char *proxyHost, unsigned short int port); // для коннекта через прокси
    ~Server();
    bool checkConnection(); // true есть..
    int getErrorState(); // Состояние ошибок, для отладки Logger.h
    void printToken();
    void printAnswer(); 
    void logOut(); // удалить токен с сервера
    const char *getProfileJSONString(); // данные будем обрабатывать выше 
    void setToken(const char *name, const char *password);
private:
    void clearAnswerHead(); // отбрасываем служебную инфу, путём первого вхождения "{" 
    void sendQuery(const char *linkQuery);
    void setTokenFromAnswer();
};

#endif	/* SERVER_H */

