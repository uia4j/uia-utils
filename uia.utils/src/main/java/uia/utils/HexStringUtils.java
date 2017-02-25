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

/**
 * Hex string utility.
 * 
 * @author Kyle K. Lin
 */
public class HexStringUtils {

	/**
	 * Convert hex string to byte array.
	 * @param hex Hex string. e.g. 38ff0e2a86
	 * @return Byte array.
	 */
    public static byte[] toBytes(String hex) {
        String data = hex.length() % 2 == 1 ? "0" + hex : hex;
        byte[] result = new byte[data.length() / 2];
        for (int i = 0; i <result.length; i++) {
            result[i] = (byte) Integer.parseInt(data.substring(2 * i, 2 * i + 2), 16);
        }
        return result;
    }
    
    /**
	 * Convert hex string to byte array.
	 * @param hex Hex string. e.g. ab12cd34
     * @param fixAtHead Add '0' at head if this is true or add '0' to tail when length of hex string is odd.
     * @return Byte array.
     */
    public static byte[] toBytes(String hex, boolean fixAtHead) {
        String data = hex.length() % 2 == 1 ? 
        		fixAtHead ? "0" + hex : hex + "0" :  
                hex;
        byte[] result = new byte[data.length() / 2];
        for (int i = 0; i <result.length; i++) {
            result[i] = (byte) Integer.parseInt(data.substring(2 * i, 2 * i + 2), 16);
        }
        return result;
    }
    
    /**
     * Convert hex string to byte array.
	 * @param hex Hex string. e.g. ab-12-cd-34
     * @param split Split string.
     * @return Byte array.
     */
	public static byte[] toBytes(String hex, String split) {
		String[] value = hex.split(split);
		byte[] result = new byte[value.length];
		for (int i = 0; i < value.length; i++) {
			result[i] = (byte) Integer.parseInt(value[i], 16);
		}
		return result;
	}
}