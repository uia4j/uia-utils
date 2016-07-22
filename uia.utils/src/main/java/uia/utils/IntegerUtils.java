/*******************************************************************************
 * * Copyright (c) 2015, UIA
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

import java.util.ArrayList;

/**
 * Integer utility.
 * 
 * @author Kyle K. Lin
 */
public class IntegerUtils {

	/**
	 * Convert integer to string.<br>
	 * Example:<br>
	 * value: 12, length: 10, empty: 0x20<br>
	 * result "12        ", 8 blank characters appended to "12".<br>
	 * 
	 * @param value Value.
	 * @param length Length of result.
	 * @param empty Appended character.
	 * @return Result.
	 */
    public static String toString(int value, int length, byte empty) {
        String result = Integer.toString(value);
        String fix = new String(new byte[] { empty });
        if (result.length() > length) {
            return result.substring(0, length);
        }

        for (int i = result.length(); i < length; i++) {
            result = fix + result;
        }
        return result;
    }

    /**
     * Convert integer to BCD byte array.<br>
	 * Example:<br>
	 * value:1600, result: {0x16, 0x00}.<br>
     * 
     * @param value Value.
     * @return Result.
     */
    public static byte[] bcdValue(int value) {
        ArrayList<Byte> data = new ArrayList<Byte>();
        do {
            int rem = value % 100;
            value = (value - rem) / 100;
            byte b = (byte) ((rem / 10) << 4);
            b += rem % 10;
            data.add(0, b);
        } while (value != 0);

        byte[] result = new byte[data.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = data.get(i).byteValue();
        }

        return result;
    }

    /**
     * Convert integer to 4 bytes array.<br>
     * Example:<br>
     * value:258, result: {0x00, 0x00, 0x01, 0x02}.<br>
     * @param value Value.
     * @return Result
     */
    public static byte[] byteValue(int value) {
        byte[] result = new byte[4];
        result[0] = (byte) (value >> 24);
        result[1] = (byte) (value >> 16);
        result[2] = (byte) (value >> 8);
        result[3] = (byte) value;
        return result;
    }
}
