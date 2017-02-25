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

import java.util.Arrays;
import java.util.List;

/**
 * Byte utility.
 * 
 * @author Kyle K. Lin
 */
public class ByteUtils {

    /**
     * Reverse byte index.
     * @param data Data.
     * @return Result.
     */
    public static byte[] reverse(byte[] data) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[data.length - i - 1];
        }
        return result;
    }

    /**
     * Convert byte to BCD integer.
     * @param value Data
     * @return Result.
     */
    public static int bcdValue(byte value) {
        return 10 * (0x0f & (value >> 4)) + (0x0f & value);
    }

    /**
     * Convert bytes to BCD integer.
     * @param value Data
     * @return Result.
     */
    public static int bcdValue(byte[] value) {
        int result = 0;
        for (int i = 0; i < value.length; i++) {
            result *= 100;
            result += bcdValue(value[i]);
        }
        return result;
    }

    /**
     * Convert bytes to short.
     * @param value Value.
     * @return Result.
     */
    public static short shortValue(byte[] value) {
        int len = (0x00ff & value[0]);
        if (value.length > 1) {
            len = len << 8;
            len += (0x00ff & value[1]);
        }
        return (short) len;
    }

    /**
     * Convert bytes to unsigned integer.
     * @param value Value.
     * @param bitLength Bit length.
     * @return Result
     */
    public static int uintValue(byte[] value, int bitLength) {
        return intValue(value, bitLength, true);
    }

    /**
     * Convert bytes to integer.
     * @param value Data
     * @param bitLength Bit length.
     * @return Result
     */
    public static int intValue(byte[] value, int bitLength) {
        return intValue(value, bitLength, false);
    }

    /**
     * Convert bytes to integer.
     * @param value Data
     * @param bitLength Bit length.
     * @param unsigned Unsigned or not.
     * @return Result.
     */
    public static int intValue(byte[] value, int bitLength, boolean unsigned) {
        int bc = (int) Math.ceil((double) bitLength / 8);
        long temp = unsigned ? (long) (0x00ff & value[0]) : (long) value[0];
        for (int i = 1; i < bc; i++) {
            temp <<= 8;
            temp |= (0x00ff & value[i]);
        }

        int endBit = (bitLength % 8);
        if (endBit != 0) {
            temp >>= (8 - endBit);
        }

        if (unsigned && temp > Integer.MAX_VALUE) {
            throw new java.lang.IllegalArgumentException("value(" + temp + ") is greater of " + Integer.MAX_VALUE);
        }

        return (int) temp;
    }

    /**
     * Convert bytes to long.
     * @param value Data
     * @param bitLength Bit length.
     * @return Result.
     */
    public static long longValue(byte[] value, int bitLength) {
        int bc = (int) Math.ceil((double) bitLength / 8);
        long temp = value[0];
        for (int i = 1; i < bc; i++) {
            temp <<= 8;
            temp |= (0x00ff & value[i]);
        }

        int endBit = (bitLength % 8);
        if (endBit != 0) {
            temp >>= (8 - endBit);
        }

        return temp;
    }

    /**
     * Copy bits from original bytes source.
     * @param data original bytes source.
     * @param byteStart the start index of bytes.
     * @param bitLength the bit count to be retrieved.
     * @return Result.
     */
    public static byte[] copyBits(byte[] data, int byteStart, int bitLength) {
        return copyBits(data, byteStart, 0, bitLength);
    }

    /**
     * Copy bits from original bytes source.
     * @param data original bytes source.
     * @param byteStart the start index of bytes.
     * @param bitStart the start index of bits.
     * @param bitLength the bit count to be retrieved.
     * @return Result.
     */
    public static byte[] copyBits(byte[] data, int byteStart, int bitStart, int bitLength) {
        int byteCount = (int) Math.ceil((double) bitLength / 8);
        byte[] result = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {
            if (byteStart + i >= data.length) {
                break;
            }

            if (bitStart == 0) {
                result[i] = data[byteStart + i];
            } else {
                result[i] = (byte) (ByteUtils.valueRight(data[byteStart + i], bitStart) << bitStart);
                if (byteStart + i + 1 < data.length) {
                    int right = ByteUtils.valueLeft(data[byteStart + i + 1], bitStart);
                    right = (byte) ((0x00ff & right) >>> (8 - bitStart));
                    result[i] |= right;
                }
            }
        }

        int bitEnd = bitLength % 8;
        if (bitEnd != 0) {
            result[byteCount - 1] = valueLeft(result[byteCount - 1], bitEnd);
        }

        return result;
    }

    /**
     * Offset bytes<br>
     * Example:<br>
     * data: {0x73} (01110011), offset: 3<br>
     * result: 00001110 01100000<br>
     * 
     * @param data Value.
     * @param bitOffset Offset bits.
     * @return Result.
     */
    public static byte[] offsetBits(byte[] data, int bitOffset) {
        return offsetBits(data, bitOffset, 8 * data.length);
    }

    /**
     * Offset bytes<br>
     * Example:<br>
     * data: {0x73} (01110011), offset: 3, length: 7<br>
     * result: 00001110 01000000<br>
     * 
     * @param data Value.
     * @param bitOffset Offset bits.
     * @param bitLength bit count of result.
     * @return Result.
     */
    public static byte[] offsetBits(byte[] data, int bitOffset, int bitLength) {
        int byteCount = (int) Math.ceil((double) (bitOffset + bitLength) / 8);
        byte[] bytes = new byte[byteCount];

        for (int i = 0; i < byteCount; i++) {
            if (i >= data.length) {
                break;
            }

            if (bitOffset == 0) {
                bytes[i] = data[i];
            } else {
                bytes[i] |= (byte) ((0x00ff & data[i]) >>> bitOffset);
                if (i + 1 < byteCount) {
                    bytes[i + 1] = (byte) (data[i] << (8 - bitOffset));
                }
            }
        }

        int bitEnd = (int) Math.ceil((double) (bitOffset + bitLength) % 8);
        if (bitEnd != 0) {
            bytes[byteCount - 1] = valueLeft(bytes[byteCount - 1], bitEnd);
        }

        return bytes;
    }

    /**
     * Convert to string with UTF8 charset.
     * @param data Value.
     * @param from From index.
     * @param length Byte count.
     * @return Result.
     */
    public static String toString(byte[] data, int from, int length) {
        return new String(Arrays.copyOfRange(data, from, from + length));
    }

    /**
     * Convert to hex string.
     * @param data Value.
     * @return Result.
     */
    public static String toHexString(byte data) {
        return String.format("%02x", data & 0xff);
    }

    /**
     * Convert to hex string.<br>
     * Example:<br>
     * data: {0xf3, 0x82, 0x12}<br>
	 * result: f3-82-12
     * 
     * @param data byte array.
     * @return hex string.
     */
    public static String toHexString(byte[] data) {
        return toHexString(data, "-");
    }

    /**
     * Convert to hex string.<br>
     * Example:<br>
     * data: {0xf3, 0x82, 0x12}, max: 2<br>
	 * result: f3-82<br>
     * 
     * @param data byte array.
     * @param max Max bytes converted to string.
     * @return hex string.
     */
    public static String toHexString(byte[] data, int max) {
        return toHexString(data, "-", max);
    }

    /**
     * Convert to hex string.<br>
     * Example:<br>
     * data: {0xf3, 0x82, 0x12}, split: ","<br>
	 * result: f3,82,12<br>
     * 
     * @param data byte array.
     * @param split split string.
     * @return Result.
     */
    public static String toHexString(byte[] data, String split) {
        if (data == null || data.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(toHexString(data[0]));
        for (int i = 1; i < data.length; i++) {
            result.append(split).append(toHexString(data[i]));
        }
        return result.toString();
    }

    /**
     * Convert to hex string.<br>
     * Example:<br>
     * data: {0xf3, 0x82, 0x12}, split: ",", max: 2<br>
	 * result: f3,82<br>
     * 
     * @param data byte array.
     * @param split split string.
     * @param max Max bytes converted to string.
     * @return hex string.
     */
    public static String toHexString(byte[] data, String split, int max) {
        if (data == null || data.length == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(toHexString(data[0]));
        for (int i = 1; i < Math.min(max, data.length); i++) {
            result.append(split).append(toHexString(data[i]));
        }
        if(data.length > max) {
            result.append(" ... total:" + data.length);
        }
        return result.toString();
    }

    /**
     * Convert byte to bit string.<br>
     * Example:<br>
     * data: 0x83<br>
     * result: "10000011"<br>
     * 
     * @param data one byte.
     * @return Result.
     */
    public static String toBitString(byte data) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if ((data & (0x80 >>> i)) == 0) {
                result.append("0");
            } else {
                result.append("1");
            }
        }
        return result.toString();
    }

    /**
     * Convert byte array to bit string.<br>
     * Example:<br>
     * data: {0x82, 0xf0}<br>
     * result: "1000001011110000"<br>
     * 
     * @param data byte array.
     * @return Result.
     */
    public static String toBitString(byte[] data) {
        StringBuilder result = new StringBuilder();
        for (byte value : data) {
            result.append(toBitString(value)).append(" ");
        }
        return result.toString().trim();
    }

    /**
     * Get value.<br>
     * Example:<br>
     * data: 0xf0, pos: 3<br>
     * result: 0x10<br>
     * 
     * @param data Value
     * @param pos bit index.
     * @return Result.
     */
    public static byte valueRight(byte data, int pos) {
        return (byte) (data & (0xff >>> pos));
    }

    /**
     * Get value.<br>
     * Example:<br>
     * data: 0xf0, pos: 3<br>
     * result: 0xe0<br>
     * 
     * @param data Value
     * @param pos bit index.
     * @return Result.
     */
    public static byte valueLeft(byte data, int pos) {
        return (byte) (data & ~(0xff >>> pos));
    }

    /**
     * Convert byte list to array.
     * @param data Value
     * @return Result.
     */
    public static byte[] toArray(List<Byte> data) {
        byte[] result = new byte[data.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = data.get(i);
        }
        return result;
    }

    /**
     * Add bytes.
     * @param result Value.
     * @param data Bytes to be added.
     */
    public static void add(List<Byte> result, byte[] data) {
        for (int i = 0; i < data.length; i++) {
            result.add(data[i]);
        }
    }

    /**
     * Add bytes.
     * @param result Value.
     * @param b Byte to be added.
     * @param count Count.
     */
    public static void add(List<Byte> result, byte b, int count) {
        for (int i = 0; i < count; i++) {
            result.add(b);
        }
    }

    /**
     * Copy bytes.
     * @param data Value
     * @param from From index.
     * @return Result.
     */
    public static byte[] copy(byte[] data, int from) {
        return copy(data, from, data.length - from, (byte) 0x00);
    }

    /**
     * Copy bytes.
     * @param data Value
     * @param from From index.
     * @param length Byte count.
     * @return Result.
     */
    public static byte[] copy(byte[] data, int from, int length) {
        return copy(data, from, length, (byte) 0x00);
    }

    /**
     * Copy bytes.
     * @param data Value
     * @param from From index.
     * @param length Byte count.
     * @param empty Empty byte.
     * @return Result.
     */
    public static byte[] copy(byte[] data, int from, int length, byte empty) {
        byte[] result = new byte[length];
        Arrays.fill(result, empty);
        int len = Math.min(length, data.length - from);
        for (int i = 0; i < len; i++) {
            result[i] = data[from + i];
        }
        return result;
    }

    /**
     * Compare two byte array.
     * @param source Source.
     * @param target Target.
     * @return Result.
     */
    public static boolean compare(byte[] source, byte[] target) {
        if (source == null || target == null || source.length != target.length) {
            return false;
        }

        for (int i = 0; i < source.length; i++) {
            if (source[i] != target[i]) {
                return false;
            }
        }
        return true;
    }
}
