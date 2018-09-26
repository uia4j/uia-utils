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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class LongUtilsTest {

    @Test
    public void testToString() {
        Assert.assertEquals("0023", LongUtils.toString(23, 4, (byte) '0'));
        Assert.assertEquals("1234", LongUtils.toString(12345678, 4, (byte) '0'));
        Assert.assertEquals(" -23", LongUtils.toString(-23, 4, (byte) ' '));
    }

    @Test
    public void testBcdValue() {
        Assert.assertArrayEquals(new byte[] { 0x03, 0x21, 0x09, (byte)0x87, 0x65, 0x43, 0x21 }, LongUtils.bcdValue(3210987654321L));
    }

    @Test
    public void testByteValue() {
        Assert.assertArrayEquals(
                new byte[] { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff },
                LongUtils.byteValue(-1));

        Assert.assertArrayEquals(
                new byte[] { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xfe },
                LongUtils.byteValue(-2));

        Assert.assertArrayEquals(
                new byte[] { (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0x7f },
                LongUtils.byteValue(-129));
    }
}
