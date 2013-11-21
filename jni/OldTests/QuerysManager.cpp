#include "QuerysManager.h"

QuerysManager::QuerysManager() {

}

bool QuerysManager::sendQuery(const char *name, const char *password) {

    this->isSocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    this->indexer = 0;
    char *test = "GET /api/authentication/?login=&pass= HTTP/1.1\r\n"
            "Host: fspo.segrys.ru\r\n"
            "User-Agent: \r\n\r\n";
    char *step1 = "GET /api/authentication/?login=";
    char *step2 = "&pass=";
    char *info = " HTTP/1.1\r\n"
            "Host: fspo.segrys.ru\r\n"
            "User-Agent: \r\n\r\n";
    head = (char *) malloc(sizeof (char)*(strlen(test) + strlen(name) + strlen(password)));
    int offset = 0;
    for (int i = 0; i < strlen(step1); i++)
        head[i] = step1[i];

    offset = strlen(step1);

    for (int i = offset; i < offset + strlen(name); i++)
        head[i] = name[i - offset];

    offset = offset + strlen(name);

    for (int i = offset; i < offset + strlen(step2); i++)
        head[i] = step2[i - offset];

    offset = offset + strlen(step2);

    for (int i = offset; i < offset + strlen(password); i++)
        head[i] = password[i - offset];

    offset = offset + strlen(password);

    for (int i = offset; i < offset + strlen(info); i++)
        head[i] = info[i - offset];

    logger("%s", name);
    logger("%s", password);
    logger("%s", head);
    
    
    this->host = "fspo.segrys.ru";


    this->address = (char *) malloc(sizeof (char)*IPSIZE + sizeof (char));
    memset(address, 0, IPSIZE + ONE);

    this->query = (char *) malloc(strlen(this->host) + strlen(this->head));

    this->remote = (struct sockaddr_in *) malloc(sizeof (struct sockaddr_in *));

    if ((hostInfo = gethostbyname(host)) == NULL) {
        logger("Host is NULL");
        return false;
    }
    if (inet_ntop(AF_INET, (void *) hostInfo->h_addr_list[0], address, IPSIZE) == NULL) {
        logger("IP IS NULL");
        return false;
    }

    remote->sin_family = AF_INET;
    inet_pton_buffer = inet_pton(AF_INET, address, (void *) (&(remote->sin_addr.s_addr)));
    remote->sin_port = htons(PORT);

    connect(isSocket, (struct sockaddr *) remote, sizeof (struct sockaddr));
    sprintf(query, head, host);

    while (indexer < strlen(query)) {
        inet_pton_buffer = send(isSocket, query + indexer, strlen(query) - indexer, 0);
        indexer += inet_pton_buffer;
    }
    indexer = 0;

    answer = (char *) malloc(sizeof (char)*BUFSIZ);
    char *segment = (char *) malloc(sizeof (char)*BUFSIZ);
    recv(isSocket, answer, BUFSIZ, 0);

    // while (recv(isSocket, segment, BUFSIZ, 0) > 0) {
    //     strcat(answer, segment);
    //      answer = (char*) realloc(answer, sizeof (char)*(strlen(answer) + BUFSIZ));
    // }
    for (int i = 0; i < strlen(answer); i++) {
        if (((int) answer[i]) < 20 || ((int) answer[i]) > 126)
            answer[i] = ' ';
    }

    free(segment);
    return true;
}

void QuerysManager::addSegmentToAnswer(char *segment) {

}

char *QuerysManager::getAnswer() {
    return this->answer;
}

QuerysManager::~QuerysManager() {
    free(this->query);
    free(this->address);
    free(this->remote);
    free(this->answer);
    free(this->head);
}

