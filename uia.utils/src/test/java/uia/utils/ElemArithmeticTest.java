package uia.utils;

import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class ElemArithmeticTest {

	@Test
	public void testCalculate(){
		Assert.assertEquals(0.0d, new ElemArithmetic("1-3+2").calculate(null), 0);
		Assert.assertEquals(-12.0d, new ElemArithmetic("1-3*2-3*4+5").calculate(null), 0);
		Assert.assertEquals(-2d, new ElemArithmetic("1-6/2").calculate(null), 0);
		Assert.assertEquals(-4d, new ElemArithmetic("1-3*5/3").calculate(null), 0);
		Assert.assertEquals(-4d, new ElemArithmetic("1-5/3*3").calculate(null), 0);
		Assert.assertEquals(-3d, new ElemArithmetic("1-3*4/3").calculate(null), 0);
		Assert.assertEquals(-3d, new ElemArithmetic("1-4/3*3").calculate(null), 0);
		
		Assert.assertEquals(3.0d, new ElemArithmetic("-1+2+-1+2+(-3) -4 - 5 + 6 --7").calculate(null), 0);
		Assert.assertEquals(3.0d, new ElemArithmetic("-1+2+-1+2+(-3) -4 - 5 + 6 --7").calculate(null), 0);
		Assert.assertEquals(-288.0d, new ElemArithmetic("-4*8*-27/3/2/(3)*2*(-3)").calculate(null), 0);
		
		Assert.assertEquals(-725.0d, new ElemArithmetic("-1+4*2^3-3(-4)-2^2^3/2*3/2*4").calculate(null), 0);
		Assert.assertEquals(320.0d, new ElemArithmetic("(2^2)^3 + 2^2^3").calculate(null), 0);
		Assert.assertEquals(-2.5d, new ElemArithmetic("1+3/2^3*4-5").calculate(null), 1);
	}

	@Test
	public void testCalcuateWithArgs(){
		TreeMap<String, Object> pv = new TreeMap<String, Object>();
		
		ElemArithmetic ea = new ElemArithmetic("x+6*y/(z*4)-9");
		
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
