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
import java.util.Arrays;

/**
 * String utility.
 *
 * @author Kyle K. Lin
 */
public final class StringUtils {

    private StringUtils() {
    }
    
    /**
     * Convert 'Y' or "TRUE" to boolean.
     * @param yn 'Y' or "TRUE"
     * @return True if yn is 'Y' or "TRUE", others false.
     */
    public static boolean bool(String yn) {
        return "Y".equalsIgnoreCase(yn) || "TRUE".equalsIgnoreCase(yn);
    }

    /**
     * Convert string to byte array.
     * @param value Content with UTF8 charset.
     * @param length Length of byte array.
     * @param empty Empty byte.
     * @return Result.
     */
    public static byte[] toBytes(String value, int length, byte empty) {
        return toBytes(value, length, empty, Charset.forName("UTF8"));
    }

    /**
     * Convert string to byte array.
     * @param value Content.
     * @param length Length of byte array.
     * @param empty Empty byte.
     * @param charset Content charset.
     * @return Result.
     */
    public static byte[] toBytes(String value, int length, byte empty, Charset charset) {
        byte[] temp = value.getBytes(charset);
        if (temp.length == length) {
            return temp;
        }

        byte[] result = new byte[length];
        Arrays.fill(result, empty);
        for (int i = 0, cnt = Math.min(length, temp.length); i < cnt; i++) {
            result[i] = temp[i];
        }

        return result;
    }

}
