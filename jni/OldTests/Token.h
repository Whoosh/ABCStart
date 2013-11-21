#ifndef TOKEN_H
#define	TOKEN_H

#define DJSONSIZE 3 // разделитель в ответе json ":" 
#define SSID "ssid"

class Token {
public:
    Token();
    Token(const char *name, const char *password);
    bool setToken(const char *name, const char *password);
    void printToken();
    ~Token();
    

private:
    char *token;    
    const char *host;
    const char *head;
};

#endif	