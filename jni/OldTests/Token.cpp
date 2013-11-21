#include "Token.h"
#include "QuerysManager.h"
#define ONE 1

#define logger(...) __android_log_print(ANDROID_LOG_DEBUG,"JNILogger",__VA_ARGS__)

char *getStringValueFromParam(char *valueMap, const char *param) {
    unsigned int indexer = 0;
    valueMap = strstr(valueMap, param);

    if (valueMap[strlen(param) + DJSONSIZE - ONE] != '\"')
        return NULL;

    for (; indexer < strlen(param) + DJSONSIZE; valueMap++, indexer++);
    while (valueMap[++indexer] != '\"');

    char *result = (char *) malloc(sizeof (char) * indexer);

    for (unsigned int j = 0; j < indexer; j++)
        result[j] = valueMap[j];

    if (strlen(result) > indexer) {// ПРИВЕТ КОСТЫЛЬ ОЛОЛО // задать вопрос!.
        char *bugBuffer = (char*) malloc(sizeof (char)*strlen(result)); // чёртов нул, копия с багом, рядом с проектом.
        for (int k = 0; k < strlen(result) - ONE; k++)
            bugBuffer[k] = result[k];
        return bugBuffer;
    }
    return result;
}

Token::Token() {

}

Token::Token(const char *name, const char *password) {
    setToken(name, password);
}

bool Token::setToken(const char *name, const char *password) {
    QuerysManager test;
    test.sendQuery(name, password);
    this->token = getStringValueFromParam(test.getAnswer(),"ssid");
    if (this->token == NULL)
        return false;
    logger("%s", token);
    return true;
}

Token::~Token() {
    free(this->token);
}

void Token::printToken() {
    //logger("%s", this->token);
}