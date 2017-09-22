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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Object properties utility.<br>
 * Use set/get/is method to write/read data.<br>
 * 
 *
 * @author Kyle K. Lin
 */
public abstract class PropertyUtils {

    /**
     * Apply value to specific property of block converter.<br>
     * Method: public void set{PropName}(T value)<br>
     * 
     * @param obj The block converter.
     * @param propName The property name.
     * @param value The property value.
     * @return Result.
     * @throws IllegalArgumentException Exception.
     * @throws IllegalAccessException Exception.
     * @throws InvocationTargetException  Exception.
     */
    public static boolean write(Object obj, String propName, Object value) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String propMethod = "set" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
        Method[] ms = obj.getClass().getMethods();
        for(Method m : ms) {
            if(propMethod.equals(m.getName())) {
                m.setAccessible(true);
                m.invoke(obj, value);
                return true;
            }
        }
        
        return false;
    }

    /**
     * Retrieve value to specific property of block converter.<br>
     * Method: public T get{PropName}() or<br>
     * Method: public boolean is{PropName}() or<br>
     *
     * @param obj The block converter.
     * @param propName The property name.
     * @return The value.
     * @throws IllegalArgumentException Exception.
     * @throws IllegalAccessException Exception.
     * @throws InvocationTargetException Exception.
     */
    public static Object read(Object obj, String propName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String propMethod1 = "get" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
        String propMethod2 = "is" + propName.substring(0, 1).toUpperCase() + propName.substring(1);
        Method[] ms = obj.getClass().getMethods();
        for(Method m : ms) {
            if(propMethod1.equals(m.getName()) || propMethod2.equals(m.getName())) {
                return m.invoke(obj);
            }
        }

        return null;
    }
}
