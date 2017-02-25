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
    			StringUtils.toBytes("abc", 4, (byte) 0x00)
    	);
    	
    	Assert.assertArrayEquals(
    			new byte[] { (byte)0xe5, (byte)0x8f, (byte)0xb0, (byte)0xe7, (byte)0x81, (byte)0xa3, 0x20, 0x20, 0x20, 0x20 }, 
    			StringUtils.toBytes("台灣", 10, (byte) 0x20)
    	);
    	
    	Assert.assertArrayEquals(
    			new byte[] { (byte)0xe5, (byte)0x8f, (byte)0xb0, (byte)0xe7 }, 
    			StringUtils.toBytes("台灣", 4, (byte) 0x20)
    	);
        
    	Assert.assertArrayEquals(
    			new byte[] { (byte)0xa5, (byte)0x78, (byte)0xc6, 0x57, 0x20, 0x20, 0x20, 0x20, 0x20, 0x20 }, 
        		StringUtils.toBytes("台灣", 10, (byte) 0x20, Charset.forName("Big5"))
    	);
    }
}
