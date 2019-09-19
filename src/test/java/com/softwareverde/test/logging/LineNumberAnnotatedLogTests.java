package com.softwareverde.test.logging;

import com.softwareverde.logging.LineNumberAnnotatedLog;
import com.softwareverde.logging.Log;
import com.softwareverde.logging.LogLevel;
import com.softwareverde.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class LineNumberAnnotatedLogTests {
    public static final LogLevel ORIGINAL_LOG_LEVEL = Logger.DEFAULT_LOG_LEVEL;
    public static final Log ORIGINAL_LOG = Logger.LOG;

    public static class AnnotatedDebugLog extends LineNumberAnnotatedLog {
        protected static class ListWriter implements Writer {
            List<String> _messages;

            @Override
            public void write(final String string) {
                _messages.add(string);
            }

            @Override
            public void write(final Throwable exception) {
                final StringWriter stringWriter = new StringWriter();
                final PrintWriter printWriter = new PrintWriter(stringWriter);
                exception.printStackTrace(printWriter);

                _messages.add(stringWriter.toString());
            }

            public void setList(final List<String> messages) {
                _messages = messages;
            }
        }

        protected final ArrayList<String> _messages = new ArrayList<String>();

        public AnnotatedDebugLog() {
            super(new ListWriter(), new ListWriter());
            ((ListWriter) _outWriter).setList(_messages);
            ((ListWriter) _errWriter).setList(_messages);
        }

        public List<String> getMessages() {
            return _messages;
        }
    }

    @Before
    public void setUp() {
        Logger.DEFAULT_LOG_LEVEL = ORIGINAL_LOG_LEVEL;
        Logger.LOG = ORIGINAL_LOG;
        Logger.clearLogLevels();
    }

    @After
    public void tearDown() {
        Logger.DEFAULT_LOG_LEVEL = ORIGINAL_LOG_LEVEL;
        Logger.LOG = ORIGINAL_LOG;
        Logger.clearLogLevels();
    }

    @Test
    public void should_log_calling_class() {
        // Setup
        final AnnotatedDebugLog annotatedDebugLog = new AnnotatedDebugLog();
        Logger.DEFAULT_LOG_LEVEL = LogLevel.ON;
        Logger.LOG = annotatedDebugLog;

        // Action
        final int lineNumber = ((new Exception()).getStackTrace()[0].getLineNumber() + 1);
        Logger.info("Message.");

        // Assert
        final List<String> messages = annotatedDebugLog.getMessages();
        Assert.assertEquals(1, messages.size());
        Assert.assertTrue(messages.get(0).endsWith(" [LineNumberAnnotatedLogTests.java:" + lineNumber + "] Message." + System.lineSeparator()));
    }
}
