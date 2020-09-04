package com.softwareverde.constable.bytearray;

import com.softwareverde.constable.Const;
import com.softwareverde.util.ByteUtil;
import com.softwareverde.util.HexUtil;

public class ImmutableByteArray extends ByteArrayCore implements Const {
    public static ImmutableByteArray fromHexString(final String hexString) {
        final byte[] bytes = HexUtil.hexStringToByteArray(hexString);
        if (bytes == null) { return null; }

        return new ImmutableByteArray(bytes, false);
    }

    public static ImmutableByteArray copyOf(final byte[] bytes) {
        if (bytes == null) { return null; }

        return new ImmutableByteArray(bytes);
    }

    protected ImmutableByteArray(final byte[] bytes, final Boolean copyBytes) {
        _bytes = (copyBytes ? ByteUtil.copyBytes(bytes) : bytes);
    }

    public ImmutableByteArray() {
        _bytes = new byte[0];
    }

    public ImmutableByteArray(final byte[] bytes) {
        _bytes = ByteUtil.copyBytes(bytes);
    }

    public ImmutableByteArray(final ByteArray byteArray) {
        if (byteArray instanceof ImmutableByteArray) {
            _bytes = ((ImmutableByteArray) byteArray)._bytes;
            return;
        }

        _bytes = ByteUtil.copyBytes(byteArray.getBytes());
    }
}
