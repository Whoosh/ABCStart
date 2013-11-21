#ifndef QUERYSMANAGER_H
#define	QUERYSMANAGER_H

#define logger(...) __android_log_print(ANDROID_LOG_DEBUG,"JNILogger",__VA_ARGS__)
#define IPSIZE 15
#define ONE 1
#define PORT 80

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <android/log.h>

class QuerysManager {
public:
    QuerysManager();
    ~QuerysManager();
    bool sendQuery(const char *name, const char *password);
    char *getAnswer();
private:
    void addSegmentToAnswer(char *segment);
    int isSocket;
    unsigned int indexer;
    unsigned int inet_pton_buffer;
    struct hostent *hostInfo;
    char *address;
    char *query;
    struct sockaddr_in *remote;
    char *answer;
    
    const char *host;
  char *head;
};

#endif	
