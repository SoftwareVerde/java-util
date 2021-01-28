package com.softwareverde.logging;

import com.softwareverde.logging.log.AnnotatedLog;
import com.softwareverde.logging.log.BufferedSystemWriter;
import com.softwareverde.logging.log.SystemLog;
import com.softwareverde.util.DateUtil;

import java.util.TimeZone;

public class LineNumberAnnotatedLog extends AnnotatedLog {
    protected static final StackTraceManager _stackTraceManager = new StackTraceManager();

    protected static final Object INSTANCE_MUTEX = new Object();
    protected static volatile LineNumberAnnotatedLog INSTANCE = null;
    public static LineNumberAnnotatedLog getInstance() {
        if (INSTANCE == null) {
            synchronized (INSTANCE_MUTEX) {
                if (INSTANCE == null) {
                    INSTANCE = new LineNumberAnnotatedLog(
                        SystemLog.wrapSystemStream(System.out),
                        SystemLog.wrapSystemStream(System.err)
                    );
                }
            }
        }

        return INSTANCE;
    }

    protected static volatile LineNumberAnnotatedLog BUFFERED_INSTANCE = null;
    public static LineNumberAnnotatedLog getBufferedInstance() {
        if (BUFFERED_INSTANCE == null) {
            synchronized (INSTANCE_MUTEX) {
                if (BUFFERED_INSTANCE == null) {
                    BUFFERED_INSTANCE = new LineNumberAnnotatedLog(
                        new BufferedSystemWriter(BufferedSystemWriter.Type.SYSTEM_OUT),
                        new BufferedSystemWriter(BufferedSystemWriter.Type.SYSTEM_ERR)
                    );
                }
            }
        }

        return BUFFERED_INSTANCE;
    }

    @Override
    protected String _getLogLevelAnnotation(final LogLevel logLevel) {
        return (logLevel != null ? logLevel.toString() : EMPTY_STRING);
    }

    @Override
    protected String _getTimestampAnnotation() {
        return DateUtil.timestampToDatetimeString(System.currentTimeMillis(), TimeZone.getDefault());
    }

    @Override
    protected String _getClassAnnotation(final Class<?> callingClass) {
        final Exception exception = new Exception();

        final int backtraceIndex = _stackTraceManager.getCallingDepth();

        final StackTraceElement[] stackTraceElements = exception.getStackTrace();
        if (backtraceIndex >= stackTraceElements.length) {
            return super._getClassAnnotation(callingClass);
        }

        final StackTraceElement stackTraceElement = stackTraceElements[backtraceIndex];
        return stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber();
    }

    protected LineNumberAnnotatedLog(final Writer outWriter, final Writer errWriter) {
        super(outWriter, errWriter);
    }

}
