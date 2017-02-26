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
public class PropertyUtilsTest {

    @Test
    public void testRW() throws Exception {
        TestObj obj = new TestObj();
        Assert.assertEquals("Unknown", PropertyUtils.read(obj, "name"));
        Assert.assertEquals(true, PropertyUtils.write(obj, "name", "John"));
        Assert.assertEquals("John", PropertyUtils.read(obj, "name"));
    }

    public static class TestObj {

        private String name;

        public TestObj() {
            this.name = "Unknown";
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
