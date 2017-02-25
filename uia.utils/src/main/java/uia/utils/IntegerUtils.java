/*******************************************************************************
 *  Copyright 2017 UIA
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
