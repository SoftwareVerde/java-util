package com.softwareverde.logging.filelog;

import java.io.IOException;

public class DateBasedAnnotatedFileLog extends AnnotatedFileLog {
    public static DateBasedAnnotatedFileLog newInstance(final String logDirectory) throws IOException {
        return DateBasedAnnotatedFileLog.newInstance(logDirectory, "", null, true);
    }
    public static DateBasedAnnotatedFileLog newInstance(final String logDirectory, final String logFilePrefix) throws IOException {
        return DateBasedAnnotatedFileLog.newInstance(logDirectory, logFilePrefix, null, true);
    }

    public static DateBasedAnnotatedFileLog newInstance(final String logDirectory, final String logFilePrefix, final Long maxByteCount) throws IOException {
        return DateBasedAnnotatedFileLog.newInstance(logDirectory, logFilePrefix, maxByteCount, true);
    }

    public static DateBasedAnnotatedFileLog newInstance(final String logDirectory, final String logFilePrefix, final Long maxByteCount, final boolean shouldBufferOutput) throws IOException {
        final Writer writer = new DateBasedFileLogWriter(logDirectory, logFilePrefix, maxByteCount, shouldBufferOutput);
        return new DateBasedAnnotatedFileLog(writer);
    }

    protected DateBasedAnnotatedFileLog(final Writer writer) {
        super(writer);
    }
}