package com.softwareverde.logging.filelog;

import jdk.vm.ci.meta.Local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateBasedFileLogWriter extends FileLogWriter {
    protected LocalDate currentFileDate;

    public DateBasedFileLogWriter(final String logDirectory, final String logFilePrefix, final Long maxByteCount, final boolean shouldBufferOutput) throws IOException {
        super(logDirectory, logFilePrefix, maxByteCount, shouldBufferOutput);
        currentFileDate = LocalDate.now();
    }

    @Override
    protected File _getNewRotationFile(final String extension) throws IOException {
        final String dateString;
        {
            BasicFileAttributes attr = Files.readAttributes(_currentFile.toPath(), BasicFileAttributes.class);
            final Date fileTime = new Date(attr.lastModifiedTime().toMillis());
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            final TimeZone timeZone = TimeZone.getDefault();
            simpleDateFormat.setTimeZone(timeZone);
            dateString = simpleDateFormat.format(fileTime);
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