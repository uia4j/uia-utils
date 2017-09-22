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

import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Kyle
 */
public class ElemArithmeticTest {

    @Test
    public void testCalculate() {
        Assert.assertEquals(0.0d, new ElemArithmetic("1-3+2").calculate(), 0);
        Assert.assertEquals(-12.0d, new ElemArithmetic("1-3*2-3*4+5").calculate(), 0);
        Assert.assertEquals(-2d, new ElemArithmetic("1-6/2").calculate(), 0);
        Assert.assertEquals(-4d, new ElemArithmetic("1-3*5/3").calculate(), 0);
        Assert.assertEquals(-4d, new ElemArithmetic("1-5/3*3").calculate(), 0);
        Assert.assertEquals(-3d, new ElemArithmetic("1-3*4/3").calculate(), 0);
        Assert.assertEquals(-3d, new ElemArithmetic("1-4/3*3").calculate(), 0);

        Assert.assertEquals(3.0d, new ElemArithmetic("-1+2+-1+2+(-3) -4 - 5 + 6 --7").calculate(null), 0);
        Assert.assertEquals(3.0d, new ElemArithmetic("-1+2+-1+2+(-3) -4 - 5 + 6 --7").calculate(null), 0);
        Assert.assertEquals(-288.0d, new ElemArithmetic("-4*8*-27/3/2/(3)*2*(-3)").calculate(null), 0);

        Assert.assertEquals(-725.0d, new ElemArithmetic("-1+4*2^3-3(-4)-2^2^3/2*3/2*4").calculate(null), 0);
        Assert.assertEquals(320.0d, new ElemArithmetic("(2^2)^3 + 2^2^3").calculate(null), 0);
        Assert.assertEquals(-2.5d, new ElemArithmetic("1+3/2^3*4-5").calculate(null), 1);
    }
    
    @Test
    public void testFailed() {
        ElemArithmetic ea1 = new ElemArithmetic("1 + 6 *    2 /( 3*4)-1");
        Assert.assertEquals("1 6 2 3 4 * / * 1 - +", ea1.path());
        Assert.assertEquals(1, ea1.calculate(), 0);
        
        ElemArithmetic ea2 = new ElemArithmetic("1 + x *    y /( 3*4)-1");
        Assert.assertEquals("1 x y 3 4 * / * 1 - +", ea2.path());

        try {
            new ElemArithmetic("2*(3+4))");
            Assert.assertTrue(false);
        }
        catch(Exception ex3) {
        }
        
        try {
            new ElemArithmetic("2*(3+4");
            Assert.assertTrue(false);
        }
        catch(Exception ex4) {
        }

        ElemArithmetic ea5 = new ElemArithmetic("1  + 6 *  2 /( 3 *4)-");
        Assert.assertEquals("1 6 2 3 4 * / * - +", ea5.path());
        try {
            ea5.calculate();
            Assert.assertTrue(false);
        }
        catch(Exception ex2) {

        }
    }

    @Test
    public void testCalcuateWithArgs() {
        TreeMap<String, Object> pv = new TreeMap<String, Object>();

        ElemArithmetic ea = new ElemArithmetic("x +6*  y /( z  *4)-9");
        System.out.println(ea.path());

        pv.put("x", 9);
        pv.put("y", 8);
        pv.put("z", 2);
        Assert.assertEquals(6.0d, ea.calculate(pv), 0);

        pv.put("x", 10);
        Assert.assertEquals(7.0d, ea.calculate(pv), 0);

        pv.put("y", 16);
        Assert.assertEquals(13.0d, ea.calculate(pv), 0);

        pv.put("z", 4);
        Assert.assertEquals(7.0d, ea.calculate(pv), 0);
    }
}
