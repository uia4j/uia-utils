package uia.utils;

import java.util.TreeMap;

import org.junit.Test;

public class ElemArithmeticTest {

	@Test
	public void test1(){
		ElemArithmetic ea = new ElemArithmetic("-1+2+-1+2+(-3) -4 - 5 + 6 --7");
		ea.println();
		System.out.println(ea.calculate(null));
	}

	@Test
	public void test2(){
		ElemArithmetic ea = new ElemArithmetic("-4*8*-27/3/2/(3)*2*(-3)");
		ea.println();
		System.out.println(ea.calculate(null));
	}

	@Test
	public void test3(){
		ElemArithmetic ea = new ElemArithmetic("-1+4*2^3-3(-4)-2^2^3/2*3/2*4");
		ea.println();
		System.out.println(ea.calculate(null));
	}

	@Test
	public void test4(){
		ElemArithmetic ea = new ElemArithmetic("(2^2)^3 + 2^2^3");
		ea.println();
		System.out.println(ea.calculate(null));
	}

	@Test
	public void test5(){
		ElemArithmetic ea = new ElemArithmetic("1+3/2^3*4-5");
		ea.println();
		System.out.println(ea.calculate(null));
	}

	@Test
	public void test6(){
		TreeMap<String, Object> pv = new TreeMap<String, Object>();
		pv.put("abc", 3.0);
		
		ElemArithmetic ea = new ElemArithmetic("1+(abc*((3+4)))+5*6-1-2+3");
		
		ea.println();
		System.out.println(ea.calculate(pv));
	}
}
