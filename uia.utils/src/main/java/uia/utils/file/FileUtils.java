package uia.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * File utilities.
 *
 * @author Kyle K. Lin
 *
 */
public class FileUtils {

    /**
     * 
     * @param path
     * @param props
     */
    public static void loadProps(String path, Properties props) {
        loadProps(new File(path), props);
    }

    /**
     * 
     * @param file
     * @param props
     */
    public static void loadProps(File file, Properties props) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            props.load(is);
        }
        catch (Exception e) {

        }
        finally {
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {

                }
            }
        }
    }

    /**
     * 
     * @param path
     * @param props
     */
    public static void saveProps(String path, Properties props) {
        saveProps(new File(path), props);
    }

    /**
     * 
     * @param file
     * @param props
     */
    public static void saveProps(File file, Properties props) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            props.store(out, null);
        }
        catch (Exception e) {

        }
        finally {
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {

                }
            }
        }
    }

    /**
     * 
     * @param path
     * @return
     * @throws IOException
     */
    public static String readContent(String path) throws IOException {
        return readContent(new File(path));
        // return new String(Files.readAllBytes(Paths.get(path)));
    }
    
    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String readContent(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray);
        fis.close();

        return new String(bytesArray);   
    }
}
