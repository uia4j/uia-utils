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

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class StringUtilsTest {

    public StringUtilsTest() {
    }

    @Test
    public void testBool() throws Exception {
        Assert.assertEquals(true, StringUtils.bool("Y"));
        Assert.assertEquals(true, StringUtils.bool("True"));
        Assert.assertEquals(false, StringUtils.bool("n"));
        Assert.assertEquals(false, StringUtils.bool("False"));
    }

    @Test
    public void testToBytes() {
        Assert.assertArrayEquals(
                new byte[] { 0x61, 0x62, 0x63, 0x00 },
                StringUtils.toBytes("abc", 4, (byte) 0x00));

        Assert.assertArrayEquals(
                new byte[] { (byte) 0xe5, (byte) 0x8f, (byte) 0xb0, (byte) 0xe7, (byte) 0x81, (byte) 0xa3, 0x20, 0x20, 0x20, 0x20 },
                StringUtils.toBytes("台灣", 10, (byte) 0x20));

        Assert.assertArrayEquals(
                new byte[] { (byte) 0xe5, (byte) 0x8f, (byte) 0xb0, (byte) 0xe7 },
                StringUtils.toBytes("台灣", 4, (byte) 0x20));

        Assert.assertArrayEquals(
                new byte[] { (byte) 0xa5, (byte) 0x78, (byte) 0xc6, 0x57, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 },
                StringUtils.toBytes("台灣", 10, (byte) 0x20, Charset.forName("Big5")));
    }
}
