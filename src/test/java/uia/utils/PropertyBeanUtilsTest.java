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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class PropertyBeanUtilsTest {

    @Test
    public void testRW() throws Exception {
        TestObj2 obj = new TestObj2();
        Assert.assertEquals("Unknown", obj.getName());
        Assert.assertEquals("Unknown", PropertyBeanUtils.read(obj, "name"));
        Assert.assertFalse(obj.isAlive());
        Assert.assertFalse((Boolean)PropertyBeanUtils.read(obj, "alive"));

        Assert.assertTrue(PropertyBeanUtils.write(obj, "name", "John"));
        
        Assert.assertEquals("John", obj.getName());
        Assert.assertEquals("John", PropertyBeanUtils.read(obj, "name"));
        Assert.assertTrue(obj.isAlive());
        Assert.assertTrue((Boolean)PropertyBeanUtils.read(obj, "alive"));
    }

    @Test
    public void testRWFailed() throws Exception {
        TestObj2 obj = new TestObj2();
        Assert.assertNull(PropertyBeanUtils.read(obj, "name1"));
        Assert.assertFalse(PropertyBeanUtils.write(obj, "name1", "John"));
    }


    public static class TestObj1 {

        private String name;

        public TestObj1() {
            this.name = "Unknown";
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        public boolean isAlive() {
            return "John".equals(this.name);
        }
    }
    
    public static class TestObj2 extends TestObj1 {

        private String job;

        public TestObj2() {
            this.job = "Blank";
        }
        
        public String getJob() {
            return this.job;
        }
    }
}
