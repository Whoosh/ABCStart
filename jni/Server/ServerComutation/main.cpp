#define SDL_main main
#include <iostream>
#include <stdio.h>
#include "Server.h"

void test() {
    Server server("log", "pass", "host", 80);
    std::cout << server.checkConnection() << std::endl;
    server.setToken("log", "pass");
    server.printToken();
    std::cout << server.getProfileJSONString() << std::endl;
    server.logOut();
}

int main(int argc, char** argv) {
    Server server("log", "pass", "host", 80);
    for (int i = 0; i < 1000; i++) {
        std::cout << server.checkConnection() << std::endl;
        server.setToken("log", "pass");
        server.printToken();
        std::cout << server.getProfileJSONString() << std::endl;
        server.logOut();

    }


    return 0;

}

