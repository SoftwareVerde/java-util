package com.softwareverde.constable.bytearray;

import com.softwareverde.util.ByteUtil;

import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {
    protected int _position = 0;
    protected final ByteArray _byteArray;

    public ByteArrayInputStream(final ByteArray byteArray) {
        _byteArray = byteArray;
    }

    @Override
    public int read() {
        if (_position >= _byteArray.getByteCount()) { return -1; }

        final byte value = _byteArray.getByte(_position);
        _position += 1;

        return ByteUtil.byteToInteger(value);
    }

    @Override
    public long skip(final long n) {
        _position += n;
        return n;
    }

    @Override
    public int available() throws IOException {
        return (_byteArray.getByteCount() - _position);
    }
}
