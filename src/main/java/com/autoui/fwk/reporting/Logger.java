package com.autoui.fwk.reporting;

import com.autoui.fwk.utils.LoggingUtils;
import com.autoui.fwk.enums.LogType;
import io.qameta.allure.Attachment;

/**
 * Apache Log4J logging along with HTML logging for test and business logic
 *
 * @author rama.bisht
 */
public class Logger {

    protected org.apache.log4j.Logger _logger;

    protected Logger(Class<?> classToLog) {
        _logger = org.apache.log4j.Logger.getLogger(classToLog);
    }

    protected Logger(String classToLog) {
        _logger = org.apache.log4j.Logger.getLogger(classToLog);
    }

    public static Logger getLogger(Class<?> classToLog) {
        return new Logger(classToLog);
    }

    /**
     * Format the message for TEXT Report by adding the space according to the
     * nesting level
     *
     * @param message Message to log
     * @return Formatted String
     */
    protected String formatMessageForText(String message) {
        return LoggingUtils.getSpaceForNestingLevel(LoggingUtils.getNestingLevelOfLogs(), LogType.TEXT) + message;
    }

    /**
     * Format the message for HTML Report by adding the space according to the
     * nesting level
     *
     * @param message Message to log
     * @return Formatted String
     */
    protected String formatMessageForHTML(String message) {
        return LoggingUtils.getSpaceForNestingLevel(LoggingUtils.getNestingLevelOfLogs() + 1, LogType.HTML) + message;
    }

    /**
     * Format the message for TEXT Report by adding the space according to the
     * nesting level
     *
     * @param message Message to log
     * @return Formatted String
     */
    protected String formatMessageForTextHeading(String message) {
        return LoggingUtils.getSpaceForNestingLevel(LoggingUtils.getNestingLevelOfLogs() - 2, LogType.TEXT) + message;
    }

    /**
     * Format the message for HTML Report by adding the space according to the
     * nesting level
     *
     * @param message Message to log
     * @return Formatted String
     */
    protected String formatMessageForHTMLHeading(String message) {
        return LoggingUtils.getSpaceForNestingLevel(LoggingUtils.getNestingLevelOfLogs() - 2 + 1, LogType.HTML) + message;
    }

    /* -----------------------------TEXT AND HTML logs------------------ */

    /**
     * Print DEBUG statement to TEXT log and HTML report
     *
     * @param message Message to log
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String trace(String message) {
        _logger.trace(formatMessageForText(message));
        String level = _logger.getParent().getLevel().toString();
        if (level.equalsIgnoreCase("TRACE"))
            ReportingTools.logMessage(formatMessageForHTML(message));

        return message;
    }

    /**
     * Print DEBUG statement to TEXT log and HTML report
     *
     * @param message Message to log
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String debug(String message) {
        _logger.debug(formatMessageForText(message));
        String level = _logger.getParent().getLevel().toString();
        if (level.equalsIgnoreCase("DEBUG") || level.equalsIgnoreCase("TRACE"))
            ReportingTools.logMessage(formatMessageForHTML(message));

        return message;
    }

    /**
     * Print INFO statement to TEXT log and HTML report
     *
     * @param message Message to log
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String info(String message) {
        _logger.info(formatMessageForText(message));
        ReportingTools.logMessage(formatMessageForHTML(message));
        return message;
    }

    /**
     * Print WARN statement to TEXT log and HTML report
     *
     * @param message Message to log
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String warn(String message) {
        _logger.warn(formatMessageForText(message));
        ReportingTools.logWarnMessage(formatMessageForHTML(message));

        return message;
    }

    /**
     * Print WARN statement to TEXT and HTML report along with throwable
     *
     * @param message   Message to log
     * @param throwable Throwable
     */
    @Attachment(value = "{0}", type = "text/plain")
    public void warn(String message, Throwable throwable) {
        _logger.warn(formatMessageForText(message), throwable);
        ReportingTools.logWarnMessage(formatMessageForHTML(message) + "<br/>" + throwable.getMessage());
    }

    /**
     * Print ERROR statement to TEXT log and HTML report
     *
     * @param message Message to log
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String error(String message) {
        _logger.error(formatMessageForText(message));
        ReportingTools.logErrorMessage(formatMessageForHTML(message));
        return message;
    }

    /**
     * Print ERROR statement to TEXT log and HTML report along with throwable
     *
     * @param message   Message to log
     * @param throwable Throwable
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String error(String message, Throwable throwable) {
        _logger.error(formatMessageForText(message), throwable);
        ReportingTools.logErrorMessage(formatMessageForHTML(message) + "<br/>" + throwable.getMessage());
        return message;
    }

    /**
     * Print FATAL statement to TEXT log and HTML report
     *
     * @param message Message to log
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String fatal(String message) {
        _logger.fatal(formatMessageForText(message));
        ReportingTools.logErrorMessage(formatMessageForHTML(message));
        return message;
    }

    /**
     * Print FATAL statement to TEXT log and HTML report along with throwable
     *
     * @param message   Message to log
     * @param throwable Throwable
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String fatal(String message, Throwable throwable) {
        _logger.fatal(formatMessageForText(message), throwable);
        ReportingTools.logErrorMessage(formatMessageForHTML(message) + "<br/>" + throwable.getMessage());
        return message;
    }

    /**
     * Print INFO statement formatted as a heading to TEXT log and HTML report
     *
     * @param message Message to log
     */
    @Attachment(value = "{0}", type = "text/plain")
    public String infoHeading(String message) {
        _logger.info(this.formatMessageForTextHeading(message));
        ReportingTools.logHeadingMessage(this.formatMessageForHTMLHeading(message));
        return message;
    }
}
