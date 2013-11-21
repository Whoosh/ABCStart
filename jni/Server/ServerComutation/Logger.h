#include <stdio.h>
#ifndef LOGGER_H
#define	LOGGER_H

//#define logger(...) __android_log_print(ANDROID_LOG_DEBUG,"JNILogger",__VA_ARGS__) // до момента сборки к дройду, не использовать
//#define logger(...) printf(__VA_ARGS__); // просмотр действий
#define logger(...)  // отключить 

#define RECV_CLOSE_SUCCESSFUL 0 // Приняли все данные, ошибки нет 
#define SOCKET_CONNECT_ERROR -1 // Ошибка при recv (Сокет был принудительно закрыт/потеря соеденения/нет соеденения)
#define PASSWORD_LOGIN_ERROR -2 // Неверный логин или пароль токен = null 
#define TOKEN_GET_SUCCESSFUL -3 // Токен получили  
#define SERVER_RESOLVE_ERROR -4 // Ошибка в SDLNet_ResolveHost, сервер не может быть опрошен
#define SOCKET_OPEN_ERROR -5 // Ошибка на этапе открытия сокета, к примеру указан не правильный порт
#define QUERY_SEND_ERROR - 6 // Запрос не был отправлен или отправлен не полностью
#define EMPTY_TOKEN_ERROR -7 // Был обнаружен пустой токен при запросе данных

void printErrorState(int errorCode, unsigned int codeLine);

#endif 

