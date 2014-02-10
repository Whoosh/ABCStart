package ru.journal.fspoPrj.login_form.data_get_managers;

import android.app.Activity;
import android.content.Intent;
import ru.journal.fspoPrj.public_code.Logger;
import ru.journal.fspoPrj.server_java.might_info.CurrentRolesInfo;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_info.CommunicationInfo;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AuthorizationExecutor extends MainExecutor {

    public static final String COMMUNICATION_KEY = "communication_k";
    public static final String TOOLS_KIT_KEY = "tools_k";

    private String authKey = "";
    private String rolesKey = "";
    private CommunicationInfo authorizationInfo;
    private int caller;

    public AuthorizationExecutor(String authKey, int caller) {
        this.authKey = authKey;
        this.caller = caller;
        super.makeQuery(authKey);
    }

    @Override
    protected void queryResults(HashMap<String, String> results)
            throws InterruptedException, ExecutionException, WrongPasswordException, TimeoutException {
        Intent resultStorage = new Intent();
        if (results.containsKey(authKey)) {
            authorizationInfo = new CommunicationInfo(results.remove(authKey));
            if (!authorizationInfo.tokenIsValid()) {
                throw new WrongPasswordException();
            }
            makeRolesQuery();
            super.doExecute();
        } else if (results.containsKey(rolesKey)) {
            resultStorage.putExtra(TOOLS_KIT_KEY, CurrentRolesInfo.makeToolKits(results.remove(rolesKey)));
        } else {
            Logger.printError(new UnknownServiceException(), getClass());
        }
        resultStorage.putExtra(COMMUNICATION_KEY, authorizationInfo);
        progressActivity.setResult(caller, resultStorage);
        progressActivity.finish();
    }

    private void makeRolesQuery() {
        rolesKey = APIQuery.GET_MIGHT.getLink(authorizationInfo.getToken(), authorizationInfo.getMyID());
        super.makeQuery(rolesKey);
    }

    public static class WrongPasswordException extends Throwable {
    }
}