package uia.utils.file;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import uia.utils.file.PropsUtils.Callback;

public class PropsUtilsTest implements Callback<String> {

    @Test
    public void testGet() throws IOException {
        PropsUtils props = new PropsUtils("data/test1.properties");

        // int
        Assert.assertEquals(9, props.getInt("int", 0, this), 0);
        Assert.assertEquals(0, props.getInt("number", 0), 0);

        // number
        Assert.assertEquals(10.2, props.getDouble("number", 0.0d), 0);
        Assert.assertEquals(10.98, props.getDouble("number1", 10.98d, this), 0);
        Assert.assertEquals(123.1, props.getDouble("string", 123.1), 0);

        // string
        Assert.assertEquals("good job", props.getValue("string", ""));
        Assert.assertEquals("?", props.getValue("string1", "?", this));
        Assert.assertEquals("10.2", props.getValue("%sber", "0.0", "num", this));
        
        // boolean
        Assert.assertTrue(props.isTrue("enable", false));
        Assert.assertFalse(props.isTrue("enable1", false, this));
        
        // pattern
        Assert.assertEquals("ab", props.getValue("%s_%s", null, "A", "B"));
        Assert.assertEquals("ac", props.getValue("%s_%s", null, this, "A", "C"));
        
        props = props.load("data/test2.properties").load("data/test3.properties");
        Assert.assertEquals(10.3d, props.getDouble("number", 0.0d, this), 0);
        Assert.assertEquals("good job2", props.getValue("string", "", this));
        Assert.assertFalse(props.isTrue("enable", true, this));
 
        Assert.assertNull(props.getValue("user.country", null));
    }


    @Test
    public void testGetWithSys() throws IOException {
        PropsUtils props = new PropsUtils("data/test1.properties", System.getProperties());
        Assert.assertNotNull(props.getValue("user.country", null));
    }

    @Test
    public void testSet() throws IOException {
        Date now = new Date();
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        
        // load
        PropsUtils props = new PropsUtils("data/test1.properties");
        Assert.assertEquals(10.2, props.getDouble("number", 0.0d, this), 0);
        Assert.assertEquals("good job", props.getValue("string", "", this));

        // set value
        props.setInt("int", 10998);
        props.setDouble("number", 12.34567, 2);
        props.setValue("string", "wonderful");
        props.setDate("datetime", now, pattern);
        props.setValue("enable", "false");

        // save
        File file = new File("data/test1_result.properties");
        if(file.exists()) {
            file.delete();
        }
        props.save("data/test1_result.properties");
        Assert.assertTrue(file.exists());

        // load
        props = new PropsUtils("data/test1_result.properties");
        Assert.assertEquals(10998, props.getInt("int", 0, this));
        Assert.assertEquals(12.35, props.getDouble("number", 0.0d, this), 2);
        Assert.assertEquals("wonderful", props.getValue("string", "", this));
        Assert.assertFalse(props.isTrue("enable", true, this));
        Assert.assertEquals(now.getTime(), props.getDate("datetime", pattern, new Date(), this).getTime());
        
        // delete
        file.delete();
    }

    @Override
    public void accept(String value) {
        System.out.println(value);
    }
}
