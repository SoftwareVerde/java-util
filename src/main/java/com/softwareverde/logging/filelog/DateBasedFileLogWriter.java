package com.softwareverde.logging.filelog;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateBasedFileLogWriter extends FileLogWriter {
    protected LocalDate currentFileDate;

    public DateBasedFileLogWriter(final String logDirectory, final String logFilePrefix, final Long maxByteCount, final boolean shouldBufferOutput) throws IOException {
        super(logDirectory, logFilePrefix, maxByteCount, shouldBufferOutput);
    }

    @Override
    protected File _getNewRotationFile(final String extension) {
        final String dateString;
        {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            dateString = currentFileDate.format(formatter);
        }

        int sequenceNumber = 1;
        while (true) {
            final String postfix = "-" + sequenceNumber;
            final String filename = (_logDirectory + File.separator + (_logFilePrefix != null ? _logFilePrefix + "-" : "") + dateString + postfix + extension);
            final File file = new File(filename);
            final File finalizedFile = new File(filename + ".gz");
            if (!file.exists() && !finalizedFile.exists()) {
                return file;
            }
            sequenceNumber++;
        }
    }

    @Override
    protected File _rotateLog(final Boolean createNewLog) throws IOException {
        final File rotationFile = super._rotateLog(createNewLog);
        currentFileDate = LocalDate.now();
        return rotationFile;
    }

    @Override
    protected void _conditionallyRotateLog() {
        try {
            final boolean isNewDate = !LocalDate.now().equals(currentFileDate);
            final boolean isOverSized = _currentByteCount >= _maxByteCount;
            if (isNewDate || isOverSized) {
                _currentOutputStream.flush();
                _currentOutputStream.close();

                final File oldFile = _rotateLog(true);
                FileLogWriter.finalizeLog(oldFile, true);
            }
        }
        catch (final IOException exception) {
            exception.printStackTrace(System.err);
        }
    }
}