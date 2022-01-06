package osp.leobert.android.gdcollector;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;


/**
 * Created by leobert on 2017/9/18.
 */
public class Logger {
    private static final String PREFIX_OF_LOGGER = "DaoCollector";
    private final Messager msg;

    public Logger(Messager messager) {
        msg = messager;
    }

    /*
     * Print info log.
     */
    public void info(CharSequence info) {
        if (isNotEmpty(info)) {
            msg.printMessage(Diagnostic.Kind.NOTE, PREFIX_OF_LOGGER + "[INFO] " + info + "\r\n");
        }
    }

    public void error(CharSequence error) {
        if (isNotEmpty(error)) {
            msg.printMessage(Diagnostic.Kind.WARNING, PREFIX_OF_LOGGER + "[ERROR] An exception is encountered, [" + error + "]" + "\r\n");
        }
    }

    public void error(Throwable error) {
        if (null != error) {
            msg.printMessage(Diagnostic.Kind.WARNING, PREFIX_OF_LOGGER + "[ERROR] An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    public void warning(CharSequence warning) {
        if (isNotEmpty(warning)) {
            msg.printMessage(Diagnostic.Kind.WARNING, PREFIX_OF_LOGGER + warning + "\r\n");
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private static boolean isNotEmpty(final CharSequence cs) {
        boolean isEmpty = cs == null || cs.length() == 0;
        return !isEmpty;
    }
}
