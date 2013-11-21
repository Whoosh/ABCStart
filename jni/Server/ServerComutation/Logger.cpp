#include "Logger.h"

void printErrorState(int errorCode,unsigned int codeLine) {
    switch (errorCode) {
        case RECV_CLOSE_SUCCESSFUL:
        {
            logger("%s%d\n", "\nRecv close successful \t code Line = ",codeLine);
            break;
        }
        case SOCKET_CONNECT_ERROR:
        {
            logger("%s%d\n", "\nSocket connection error \t code Line = ",codeLine);
            break;
        }
        case PASSWORD_LOGIN_ERROR:
        {
            logger("%s%d\n", "\nPassword or login has incorrect \t code Line = ",codeLine);
            break;
        }
        case TOKEN_GET_SUCCESSFUL:
        {
            logger("%s%d\n", "\nToken get successful \t code Line = ",codeLine);
            break;
        }
        case SERVER_RESOLVE_ERROR:
        {
            logger("%s%d\n", "\nResolve host is not available\t code Line = ",codeLine);
            break;
        }
        case SOCKET_OPEN_ERROR:
        {
            logger("%s%d\n", "\nOpen socket error maybe port is wrong\t code Line = ",codeLine);
            break;
        }
        case QUERY_SEND_ERROR:
        {
            logger("%s%d\n", "\nQuery is not send full to server \t code Line = ",codeLine);
            break;
        }
        case EMPTY_TOKEN_ERROR:
        {
            logger("%s%d\n", "\nLooking empty token \t code Line = ",codeLine);
            break;
        }

        default:
        {
            logger("%s%d\n", "\nNo state for this error code \t code Line = ",codeLine);
        }
    }
}
