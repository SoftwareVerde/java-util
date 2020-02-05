package com.softwareverde.constable.bytearray;

public class ByteArrayCore {
    protected ByteArrayCore() { }

    public static boolean getBit(final byte[] bytes, final long index) {
        final int byteIndex = (int) (index >>> 3);
        final byte b = bytes[byteIndex];

        final int bitMask = ( 0x01 << ( 7 - (0x07 & index) ) );
        return ( (b & bitMask) != 0x00 );
    }
}
