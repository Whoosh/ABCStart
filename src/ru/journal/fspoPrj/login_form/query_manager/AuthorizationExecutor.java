package ru.journal.fspoPrj.login_form.query_manager;

import ru.journal.fspoPrj.server_java.Server;
import ru.journal.fspoPrj.server_java.server_info.APIQuery;
import ru.journal.fspoPrj.server_java.server_managers.MainExecutor;
import ru.journal.fspoPrj.server_java.storage.BufferedLinker;
import ru.journal.fspoPrj.server_java.storage.CachedStorage;

import java.util.concurrent.*;

public class AuthorizationExecutor extends MainExecutor {

    public AuthorizationExecutor(String queryLink) {
        super.queryLink = queryLink;
    }

    @Override
    protected void handleQuery() throws InterruptedException, ExecutionException, TimeoutException, WrongPasswordException {
        super.handleQuery();
        CachedStorage.cacheAuthorizationInfo(queryLink);
        if (!CachedStorage.tokenIsValid()) {
            throw new WrongPasswordException();
        }
        BufferedLinker linker = Server.setFutureQuery(APIQuery.GET_MIGHT.getLink(CachedStorage.getToken(), CachedStorage.getMyID()));
        super.handlingAllFutures();
        CachedStorage.cacheMightInfo(linker.getQueryLink());
    }

}