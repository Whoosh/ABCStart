package ru.journal.fspoPrj.public_code.keys_manager;

import org.json.JSONException;
import org.json.JSONObject;
import ru.journal.fspoPrj.public_code.Logger;

public interface IKeyApi {

    static final int DEFAULT_INT = 0;
    static final String DEFAULT_STRING = "";
    static Parser parser = new Parser();

    String getKey();

    int getIntValue(JSONObject element);

    String getStringValue(JSONObject element);

    public static class Parser {

        public int parseInt(String key, JSONObject data) {
            try {
                return Integer.parseInt(data.getString(key));
            } catch (NumberFormatException ex) {
                return DEFAULT_INT;
            } catch (JSONException e) {
                Logger.printIKeyApiError(e);
                return DEFAULT_INT;
            }
        }

        public String parseString(String key, JSONObject data) {
            try {
                String result = data.getString(key);
                if (result.equals("null")) {
                    return DEFAULT_STRING;
                } else {
                    return result;
                }
            } catch (JSONException e) {
                Logger.printIKeyApiError(e);
                return DEFAULT_STRING;
            }
        }
    }
}
