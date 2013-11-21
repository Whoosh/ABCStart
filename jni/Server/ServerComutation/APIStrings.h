#ifndef APISTRINGS_H
#define	APISTRINGS_H

#define HEAD_QUERY_STRING "GET /api/"
#define HEAD_QUERY_SIZE 9

#define TAIL_QUERY_STRING " HTTP/1.0\r\nHost: fspo.segrys.ru\r\nUser-Agent: ArmyOfDroid's \r\n\r\n"
#define TAIL_QUERY_SIZE 67

#define AUTH_FORM_STRING "authentication/?login="
#define AUTH_FORM_SIZE 22

#define AUTH_PASS_PARAM_STRING "&pass="
#define AUTH_PASS_PARAM_SIZE 6

#define LOG_OUT_FORM_STRING "logout/?ssid="
#define LOG_OUT_FORM_SIZE 13

#define PROFILE_FORM_STRING "getprofile/?ssid="
#define PROFILE_FORM_SIZE 17


#endif

