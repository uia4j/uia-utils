package uia.utils;

import org.junit.Assert;
import org.junit.Test;

public class CRCTest {

    @Test
    public void testCRC16(){
        byte[] data = new byte[] {
                0x31,
                0x32,
                0x33,
                0x34,
                0x35,
                0x36,
                0x37,
                0x38,
                0x39
        };
        Assert.assertArrayEquals(new byte[] { (byte)0xbb, (byte)0x3d }, CRC.crc16(data));
    }
}
