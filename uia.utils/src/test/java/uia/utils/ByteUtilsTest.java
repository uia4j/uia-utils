/*******************************************************************************
 * Copyright 2017 UIA
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uia.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class ByteUtilsTest {

    @Test
    @Ignore
    public void testDoubleByte() {
        byte[] v1 = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putDouble(12.9d).array();
        byte[] v2 = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putDouble(13.9d).array();
        System.out.println(ByteUtils.toHexString(v1));
        System.out.println(ByteUtils.toHexString(v2));
        System.out.println("v1=" + ByteBuffer.wrap(v1).getDouble());
        System.out.println("v2=" + ByteBuffer.wrap(v2).order(ByteOrder.LITTLE_ENDIAN).getDouble());
    }

    @Test
    public void testReserved() {
        byte[] data = new byte[] { 0x01, 0x02 };
        Assert.assertArrayEquals(new byte[] { 0x02, 0x01 }, ByteUtils.reverse(data));
    }
    
    @Test
    public void testAdd() {
        byte[] a = new byte[] { 0x01, 0x02 };
        byte[] b = new byte[] { 0x01, 0x03 };
        Assert.assertArrayEquals(new byte[] { 0x01, 0x02, 0x01, 0x03 }, ByteUtils.add(a, b));
        Assert.assertArrayEquals(new byte[] { 0x01, 0x02, 0x30, 0x30 }, ByteUtils.add(a, (byte) 0x30, 2));
    }
    
    @Test
    public void testShortValue() {
        Assert.assertEquals(256, ByteUtils.shortValue(new byte[] { 0x01, 0x00 }), 0);
        Assert.assertEquals(-2, ByteUtils.shortValue(new byte[] { (byte) 0xff, (byte)0xfe }), 0);
    }

    @Test
    public void testIntValue() throws Exception {
        Assert.assertEquals(256, ByteUtils.intValue(new byte[] { 0x00, 0x00, 0x01, 0x00 }, 32), 0);

        // 11111110
        Assert.assertEquals(-2, ByteUtils.intValue(new byte[] { (byte) 0xfe }, 8));

        // 1111111-
        // 11111111
        Assert.assertEquals(-1, ByteUtils.intValue(new byte[] { (byte) 0xfe }, 7));

        byte[] temp = new byte[] { (byte) 0x80, (byte) 0x00, (byte) 0x30, (byte) 0x81 };
        Assert.assertEquals(-128, ByteUtils.intValue(temp, 8));
        Assert.assertEquals(-32768, ByteUtils.intValue(temp, 16));
        Assert.assertEquals(-8388560, ByteUtils.intValue(temp, 24));
        Assert.assertEquals(-2147471231, ByteUtils.intValue(temp, 32));
    }

    @Test
    public void testUintValue() throws Exception {
        // 11111110
        Assert.assertEquals(254, ByteUtils.uintValue(new byte[] { (byte) 0xfe }, 8));

        // 1111111-
        // 01111111
        Assert.assertEquals(127, ByteUtils.uintValue(new byte[] { (byte) 0xfe }, 7));

        byte[] temp = new byte[] { (byte) 0x80, (byte) 0x00, (byte) 0x30, (byte) 0x81 };
        Assert.assertEquals(128, ByteUtils.uintValue(temp, 8));
        Assert.assertEquals(32768, ByteUtils.uintValue(temp, 16));
        Assert.assertEquals(8388656, ByteUtils.uintValue(temp, 24));
    }

    @Test
    public void testBcdValue() throws Exception {
        Assert.assertEquals(9, ByteUtils.bcdValue((byte) 0x09));
        Assert.assertEquals(82, ByteUtils.bcdValue((byte) 0x82));
        Assert.assertEquals(3402, ByteUtils.bcdValue(new byte[] { (byte) 0x34, (byte) 0x02 }));
        Assert.assertEquals(21232, ByteUtils.bcdValue(new byte[] { (byte) 0x02, (byte) 0x12, (byte) 0x32 }));
    }
    
    @Test
    public void testLongValue() {
        Assert.assertEquals(256, ByteUtils.longValue(new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00 }, 64), 0);
        Assert.assertEquals(-2, ByteUtils.longValue(new byte[] { 
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte)0xfe }, 64), 0);
    }

    @Test
    public void testOffsetBits() {
        // 11110000 01001111 10000100 10000000
        byte[] data = new byte[] { (byte) 0xf0, (byte) 0x4f, (byte) 0x84, (byte) 0x80 };

        // 00011110 00001001 11110000 10010000 0000000
        Assert.assertArrayEquals(
                new byte[] { 0x1e, 0x09, (byte) 0xf0, (byte) 0x90, 0x00 },
                ByteUtils.offsetBits(data, 3));

        // 11110000 0100---- -------- -------- -------
        // 00011110 00001000
        Assert.assertArrayEquals(
                new byte[] { 0x1e, 0x08 },
                ByteUtils.offsetBits(data, 3, 12));

        // 11110000 01001111 -------- -------- --------
        // 00011110 00001001 11100000
        Assert.assertArrayEquals(
                new byte[] { 0x1e, 0x09, (byte) 0xe0 },
                ByteUtils.offsetBits(data, 3, 16));
    }

    @Test
    public void testToValueRightLeft() {
        // 11101001
        byte value = (byte) 0xe9;

        // --101001
        // --101001
        Assert.assertEquals(0x29, ByteUtils.valueRight(value, 2));

        // 11------
        Assert.assertEquals((byte) 0xc0, ByteUtils.valueLeft(value, 2));
    }
    
    @Test
    public void testCompare() {
        Assert.assertTrue(ByteUtils.compare(new byte[] { 0x01,  0x02, 0x03 }, new byte[] { 0x01,  0x02, 0x03 }));
        Assert.assertFalse(ByteUtils.compare(null, new byte[] { 0x01,  0x02, 0x03 }));
    }
    
    @Test
    public void testCopy() {
        Assert.assertArrayEquals(new byte[] { 0x03 }, ByteUtils.copy(new byte[] { 0x01,  0x02, 0x03 }, 2));
        Assert.assertArrayEquals(new byte[] { 0x02,  0x03 }, ByteUtils.copy(new byte[] { 0x01,  0x02, 0x03 }, 1, 2));
        Assert.assertArrayEquals(new byte[] { 0x02,  0x03, 0x00 }, ByteUtils.copy(new byte[] { 0x01,  0x02, 0x03 }, 1, 3));
        Assert.assertArrayEquals(new byte[] { 0x02,  0x03, 0x20 }, ByteUtils.copy(new byte[] { 0x01,  0x02, 0x03 }, 1, 3, (byte)0x20));
    }

    @Test
    public void testCopyBits() {
        // 11111000 01001111 10110100 01101111
        byte[] data = new byte[] { (byte) 0xf8, (byte) 0x4f, (byte) 0xb4, (byte) 0x6f };

        // 1111----
        Assert.assertArrayEquals(new byte[] { (byte) 0xf0 }, ByteUtils.copyBits(data, 0, 4));

        // 11111000 01001---
        Assert.assertArrayEquals(new byte[] { (byte) 0xf8, (byte) 0x48 }, ByteUtils.copyBits(data, 0, 13));

        // ----1000 0100----
        // 10000100
        Assert.assertArrayEquals(new byte[] { (byte) 0x84 }, ByteUtils.copyBits(data, 0, 4, 8));

        // ----1000 010011--
        // 10000100 11------
        Assert.assertArrayEquals(new byte[] { (byte) 0x84, (byte) 0xc0 }, ByteUtils.copyBits(data, 0, 4, 10));

        // ----1000 01001111 101-----
        // 10000100 1111101-
        Assert.assertArrayEquals(new byte[] { (byte) 0x84, (byte) 0xfa }, ByteUtils.copyBits(data, 0, 4, 15));

        // -------- -------- -------- --101111
        // 101111-- --------
        Assert.assertArrayEquals(new byte[] { (byte) 0xbc, (byte) 0x00 }, ByteUtils.copyBits(data, 3, 2, 16));

    }

    @Test
    public void testToBitString() {
        Assert.assertEquals("11110000", ByteUtils.toBitString((byte) 0xf0));
        Assert.assertEquals("00001111", ByteUtils.toBitString((byte) 0x0f));
        Assert.assertEquals("10000001", ByteUtils.toBitString((byte) 0x81));
        Assert.assertEquals(
                "11111000 01001111 10110100 01101111",
                ByteUtils.toBitString(new byte[] { (byte) 0xf8, (byte) 0x4f, (byte) 0xb4, (byte) 0x6f }));
    }

    @Test
    public void testToHexString() {
        Assert.assertEquals("", ByteUtils.toHexString(null));
        Assert.assertEquals("", ByteUtils.toHexString(new byte[0]));
        Assert.assertEquals("f0", ByteUtils.toHexString((byte) 0xf0));
        Assert.assertEquals("0f", ByteUtils.toHexString((byte) 0x0f));
        Assert.assertEquals("81", ByteUtils.toHexString((byte) 0x81));
        Assert.assertEquals("81-18-00", ByteUtils.toHexString(new byte[] { (byte) 0x81, 0x18, 0x00 }));
        Assert.assertEquals("81,18,00", ByteUtils.toHexString(new byte[] { (byte) 0x81, 0x18, 0x00 }, ","));
        Assert.assertEquals("81-18-00 ... total:7", ByteUtils.toHexString(new byte[] { (byte) 0x81, 0x18, 0x00, 0x18, 0x00, 0x18, 0x00 }, 3));
    }

    @Test
    public void testToString() {
        byte[] utf8 = StringUtils.toBytes("這是中文", 12, (byte) 0x20);
        byte[] big5 = StringUtils.toBytes("這是中文", 8, (byte) 0x20, Charset.forName("Big5"));
        Assert.assertEquals("這是中文", ByteUtils.toString(utf8, 0, utf8.length));
        Assert.assertEquals("這是中文", ByteUtils.toString(big5, 0, big5.length, Charset.forName("Big5")));
    }
}
