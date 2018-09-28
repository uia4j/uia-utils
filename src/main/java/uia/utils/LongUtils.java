package uia.utils;

import java.util.ArrayList;


public class LongUtils {

    /**
     * Convert long to string.<br>
     * Example:<br>
     * value: 12, length: 10, empty: 0x20<br>
     * result "12        ", 8 blank characters appended to "12".<br>
     *
     * @param value Value.
     * @param length Length of result.
     * @param empty Appended character.
     * @return Result.
     */
    public static String toString(long value, int length, byte empty) {
        String result = Long.toString(value);
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
     * Convert long to BCD byte array.<br>
     * Example:<br>
     * value:1600, result: {0x16, 0x00}.<br>
     *
     * @param value Value.
     * @return Result.
     */
    public static byte[] bcdValue(long value) {
        ArrayList<Byte> data = new ArrayList<Byte>();
        long _value = value;
        do {
            long rem = _value % 100;
            _value = (_value - rem) / 100;
            byte b = (byte) ((rem / 10) << 4);
            b += rem % 10;
            data.add(0, b);
        }
        while (_value != 0);

        byte[] result = new byte[data.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = data.get(i).byteValue();
        }

        return result;
    }

    /**
     * Convert long to 4 bytes array.<br>
     * Example:<br>
     * value:258, result: {0x00, 0x00, 0x01, 0x02}.<br>
     * @param value Value.
     * @return Result
     */
    public static byte[] byteValue(long value) {
        return toBytes(value);
    }

    /**
     * Convert long to 4 bytes array. Same as byteValue().<br>
     * Example:<br>
     * value:258, result: {0x00, 0x00, 0x01, 0x02}.<br>
     * @param value Value.
     * @return Result
     */
    public static byte[] toBytes(long value) {
        byte[] result = new byte[8];
        result[0] = (byte) (value >> 56);
        result[1] = (byte) (value >> 48);
        result[2] = (byte) (value >> 40);
        result[3] = (byte) (value >> 32);
        result[4] = (byte) (value >> 24);
        result[5] = (byte) (value >> 16);
        result[6] = (byte) (value >> 8);
        result[7] = (byte) value;
        return result;
    }
}
