package uia.utils;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import uia.utils.layout.GridType;
import uia.utils.layout.LayoutType;

public class JaxbUtilsTest {

	@Test
	public void testFromXml() throws Exception {
		JaxbUtils<LayoutType> jaxb = new JaxbUtils<LayoutType>(
				LayoutType.class,  
				"layout", 
				"http://utils.uia/layout", 
				"uia.utils.layout"); 
		
		LayoutType layout = jaxb.fromXml(new File("data/layout.xml"));
		Assert.assertEquals(3, layout.getGrid().size());
	}

	@Test
	public void testToXml() throws Exception {
		JaxbUtils<LayoutType> jaxb = new JaxbUtils<LayoutType>(
				LayoutType.class,  
				"layout", 
				"http://utils.uia/layout", 
				"uia.utils.layout"); 
		
		LayoutType layout = new LayoutType();
		layout.getGrid().add(new GridType());
		layout.getGrid().add(new GridType());
		System.out.println(jaxb.toXml(layout));
	}
}
