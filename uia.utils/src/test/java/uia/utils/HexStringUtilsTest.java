/*******************************************************************************
 * * Copyright (c) 2014, UIA
 * * All rights reserved.
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions are met:
 * *
 * *     * Redistributions of source code must retain the above copyright
 * *       notice, this list of conditions and the following disclaimer.
 * *     * Redistributions in binary form must reproduce the above copyright
 * *       notice, this list of conditions and the following disclaimer in the
 * *       documentation and/or other materials provided with the distribution.
 * *     * Neither the name of the {company name} nor the
 * *       names of its contributors may be used to endorse or promote products
 * *       derived from this software without specific prior written permission.
 * *
 * * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND ANY
 * * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
    			new byte[] { 0x23, 0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef }, 
    			HexStringUtils.toBytes("23-45-67-89-ab-cd-ef", "-"));

    	Assert.assertArrayEquals(
    			new byte[] { 0x23, 0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef }, 
    			HexStringUtils.toBytes("23456789abcdef"));

    	Assert.assertArrayEquals(
    			new byte[] { 0x02, 0x34, 0x56, 0x78, (byte)0x9a, (byte)0xbc, (byte)0xde, (byte)0xfa }, 
    			HexStringUtils.toBytes("23456789abcdefA"));

    	Assert.assertArrayEquals(
    			new byte[] { 0x02, 0x34, 0x56, 0x78, (byte)0x9a, (byte)0xbc, (byte)0xde, (byte)0xfa }, 
    			HexStringUtils.toBytes("23456789abcdefA", true));

    	Assert.assertArrayEquals(
    			new byte[] { 0x23, 0x45, 0x67, (byte)0x89, (byte)0xab, (byte)0xcd, (byte)0xef, (byte)0xa0 }, 
    			HexStringUtils.toBytes("23456789abcdefA", false));
    }
}
