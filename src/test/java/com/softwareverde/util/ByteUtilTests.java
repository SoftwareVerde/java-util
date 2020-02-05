package com.softwareverde.util;

import com.softwareverde.constable.bytearray.ByteArray;
import com.softwareverde.constable.bytearray.MutableByteArray;
import org.junit.Assert;
import org.junit.Test;

public class ByteUtilTests {
    @Test
    public void should_set_tail_bytes() {
        // Setup
        final MutableByteArray destination = MutableByteArray.fromHexString("FFFFFFFFFFFFFFFFFFFF");
        final ByteArray source = ByteArray.fromHexString("010203");

        // Action
        ByteUtil.setTailBytes(destination, source);

        // Assert
        Assert.assertEquals("FFFFFFFFFFFFFF010203", destination.toString());
    }

    @Test
    public void should_set_tail_bytes_2() {
        // Setup
        final byte[] destination = new byte[10];
        final byte[] source = HexUtil.hexStringToByteArray("010203");

        // Action
        ByteUtil.setTailBytes(destination, source);

        // Assert
        Assert.assertEquals("00000000000000010203", HexUtil.toHexString(destination));
    }

    @Test
    public void should_set_tail_bytes_3() {
        // Setup
        final MutableByteArray destination = new MutableByteArray(4);
        final ByteArray source = ByteArray.fromHexString("0102030405");

        // Action
        ByteUtil.setTailBytes(destination, source);

        // Assert
        Assert.assertEquals("02030405", destination.toString());
    }


    @Test
    public void should_set_tail_bytes_4() {
        // Setup
        final MutableByteArray destination = new MutableByteArray(4);
        final ByteArray source = ByteArray.fromHexString("01020304");

        // Action
        ByteUtil.setTailBytes(destination, source);

        // Assert
        Assert.assertEquals("01020304", destination.toString());
    }
}
