package ru.journal.fspoPrj.server_java.server_info;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;

import java.io.Serializable;

public class CommunicationInfo implements Serializable {

    private static final String EMPTY = "";
    private static final String TOKEN_KEY = "ssid";
    private static final String USER_ID_KEY = "user_id";
    private static final String YEAR_ID_KEY = "year_id";

    private String token;
    private String yearID;
    private String myID;

    private static final byte THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN = 5;

    public CommunicationInfo(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            this.token = jsonObject.getString(TOKEN_KEY);
            this.myID = jsonObject.getString(USER_ID_KEY);
            this.yearID = jsonObject.getString(YEAR_ID_KEY);
        } catch (JSONException e) {
            Logger.printError(e, getClass());
        }
    }

    public String getToken() {
        return token;
    }

    public String getYearID() {
        return yearID;
    }

    public String getMyID() {
        return myID;
    }

    public boolean tokenIsValid() {
        return (token.length() > THIS_IS_NOT_NULL_AND_NOT_TOKEN_LEN);
    }

    public void dropInfo() {
        token = EMPTY;
        yearID = EMPTY;
        myID = EMPTY;
    }
}
