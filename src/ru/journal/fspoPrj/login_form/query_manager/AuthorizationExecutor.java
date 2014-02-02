package ru.journal.fspoPrj.login_form.query_manager;

import ru.journal.fspoPrj.server_java.Authorization;
import ru.journal.fspoPrj.server_java.might_info.CurrentRolesInfo;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class AuthorizationExecutor extends MainExecutor {

    private static Authorization authorization;
    private static Authorization.OnAuthCallBack listener;

    private String authKey = "";
    private String rolesKey = "";

    public AuthorizationExecutor(String authKey, Authorization parentAuth) {
        AuthorizationExecutor.authorization = parentAuth;
        listener = authorization.getOnAuthCallBack();
        this.authKey = authKey;
        super.makeQuery(authKey);
    }

    public void resetAuthListener(Authorization.OnAuthCallBack listener) {
        AuthorizationExecutor.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        super.doInBackground(params);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        authorization.over();
        listener = null;
    }

    @Override
    protected void queryResults(ConcurrentHashMap<String, String> results)
            throws InterruptedException, ExecutionException, WrongPasswordException, TimeoutException {

        if (results.containsKey(authKey)) {
            AuthorizationExecutor.authorization.cacheCommunicationInfo(results.remove(authKey));
            if (!AuthorizationExecutor.authorization.tokenIsValid()) {
                throw new WrongPasswordException(AuthorizationExecutor.authorization);
            }
            makeRolesQuery();
            super.doExecute();
        } else if (results.containsKey(rolesKey)) {
            CurrentRolesInfo.setDataFromJson(results.remove(rolesKey));
            // TODO
            AuthorizationExecutor.listener.authSuccessful(CurrentRolesInfo.getToolsKit());
        }
    }

    private void makeRolesQuery() {
        rolesKey = APIQuery.GET_MIGHT.getLink(
                AuthorizationExecutor.authorization.getToken(),
                AuthorizationExecutor.authorization.getMyID());
        super.makeQuery(rolesKey);
    }

    public class WrongPasswordException extends Throwable {
        public WrongPasswordException(Authorization authorization) {
            if (authorization != null) {
                authorization.clearToken();
            }
        }
    }
}