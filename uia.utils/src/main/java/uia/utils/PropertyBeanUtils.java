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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Object properties utility.
 *
 * @author Kyle K. Lin
 */
public abstract class PropertyBeanUtils {

    /**
     * Apply value to specific property of block converter.
     *
     * @param obj The block converter.
     * @param propName The property name.
     * @param value The property value.
     * @return Result.
     * @throws IntrospectionException bean info exception.
     * @throws IllegalAccessException bean info exception.
     * @throws IllegalArgumentException bean info exception.
     * @throws InvocationTargetException bean info exception.
     */
    public static boolean write(Object obj, String propName, Object value) throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(propName)) {
                pd.getWriteMethod().invoke(obj, value);
                return true;
            }
        }

        return false; 
    }

    /**
     * Retrieve value to specific property of block converter.
     *
     * @param obj The block converter.
     * @param propName The property name.
     * @return The value.
     * @throws IntrospectionException bean info exception.
     * @throws IllegalAccessException bean info exception.
     * @throws IllegalArgumentException bean info exception.
     * @throws InvocationTargetException bean info exception.
     */
    public static Object read(Object obj, String propName)
            throws IntrospectionException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {

        BeanInfo info = Introspector.getBeanInfo(obj.getClass());
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(propName)) {
                return pd.getReadMethod().invoke(obj);
            }
        }

        return null;
    }
}
