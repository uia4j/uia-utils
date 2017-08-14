package uia.utils.file;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class PathUtilsTest {
    
    @Test
    public void testListFiles() throws IOException {
       List<String> fs = PathUtils.listFiles("data", "json");
       for(String f : fs) {
           System.out.println(f);
       }
       Assert.assertEquals(13,  fs.size());
    }

}
