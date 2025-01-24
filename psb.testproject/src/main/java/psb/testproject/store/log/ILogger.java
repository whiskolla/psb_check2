package psb.testproject.store.log;

public interface ILogger {
    void logInfo(String message);

    void log(String message);

    void logWarning(String message);

    void logError(String message);
}