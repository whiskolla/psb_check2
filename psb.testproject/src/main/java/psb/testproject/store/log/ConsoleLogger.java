package psb.testproject.store.log;

public class ConsoleLogger implements ILogger {
    public String ANSI_RESET = "\u001B[0m";
    public String ANSI_GREY_BACKGROUND = "\u001B[47m";
    public String ANSI_RED_BACKGROUND = "\u001B[41m";
    public String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public String ANSI_YELLOW_BACKGROUND = "\u001B[43m";

    @Override
    public void logInfo(String message) {
        System.out.println(ANSI_GREEN_BACKGROUND + "Info: " + message + ANSI_RESET);
    }

    @Override
    public void log(String message) {
        System.out.println(ANSI_GREY_BACKGROUND + message + ANSI_RESET);
    }

    @Override
    public void logWarning(String message) {
        System.out.println(ANSI_YELLOW_BACKGROUND + "Warning: " + message + ANSI_RESET);
    }

    @Override
    public void logError(String message) {
        System.out.println(ANSI_RED_BACKGROUND + "Error: " + message + ANSI_RESET);
    }
}
