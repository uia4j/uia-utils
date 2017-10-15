package uia.utils.file;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

public class FileUtilsTest {

    @Test
    public void testLoadProps() {

        Properties props = new Properties();
        Assert.assertFalse(FileUtils.loadProps("data/test0.properties", props));
        Assert.assertTrue(FileUtils.loadProps("data/test1.properties", props));
        Assert.assertEquals("good job", "" + props.get("string"));
        Assert.assertEquals("10.2", "" + props.get("number"));
        Assert.assertEquals("2017-06-07 14:59:09.000", "" + props.get("datetime"));
        Assert.assertEquals("true", "" + props.get("enable"));
    }

    @Test
    public void testSaveProps() {
        Properties props = new Properties();
        
        // load
        FileUtils.loadProps(new File("data/test1.properties"), props);
        props.setProperty("int", "12345");

        // save
        File file = new File("data/test1_result.properties");
        if(file.exists()) {
            file.delete();
        }
        Assert.assertTrue(FileUtils.saveProps("data/test1_result.properties", props));
        Assert.assertFalse(FileUtils.saveProps("data/???.properties", props));

        // load
        props = new Properties();
        FileUtils.loadProps(file, props);
        Assert.assertEquals("good job", "" + props.get("string"));
        Assert.assertEquals("10.2", "" + props.get("number"));
        Assert.assertEquals("2017-06-07 14:59:09.000", "" + props.get("datetime"));
        Assert.assertEquals("true", "" + props.get("enable"));
        Assert.assertEquals("12345", "" + props.get("int"));
        
        file.delete();
    }
    
    @Test
    public void testReadContent() throws IOException {
        Assert.assertEquals("1234567890", FileUtils.readContent("data/test4.txt"));
        Assert.assertEquals("1234567890", FileUtils.readContent("data/test4.txt", "utf-8"));
    }
}
