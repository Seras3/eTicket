package service;

import context.Context;
import context.Identity;
import dao.LogDao;
import model.Log;

public class LoggingService {
    private LogDao logDao;
    private Identity identity;

    public LoggingService() {
        logDao = new LogDao();
        identity = Context.getIdentity();
    }

    public void addLog(String route) {
        Log log = new Log(identity.getId(), route);
        logDao.add(log);
    }
}
