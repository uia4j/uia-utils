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
public class HexStringUtilsTest {

    public HexStringUtilsTest() {
    }

    @Test
    public void testToBytes() throws Exception {
        Assert.assertArrayEquals(
                new byte[] { 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef },
                HexStringUtils.toBytes("23-45-67-89-ab-cd-ef", "-"));

        Assert.assertArrayEquals(
                new byte[] { 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef },
                HexStringUtils.toBytes("23456789abcdef"));

        Assert.assertArrayEquals(
                new byte[] { 0x02, 0x34, 0x56, 0x78, (byte) 0x9a, (byte) 0xbc, (byte) 0xde, (byte) 0xfa },
                HexStringUtils.toBytes("23456789abcdefA"));

        Assert.assertArrayEquals(
                new byte[] { 0x02, 0x34, 0x56, 0x78, (byte) 0x9a, (byte) 0xbc, (byte) 0xde, (byte) 0xfa },
                HexStringUtils.toBytes("23456789abcdefA", true));

        Assert.assertArrayEquals(
                new byte[] { 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef, (byte) 0xa0 },
                HexStringUtils.toBytes("23456789abcdefA", false));
    }
}
