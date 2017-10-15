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
     * Load properties from file.
     * @param path File path.
     * @param props Original properties.
     */
    public static boolean loadProps(String path, Properties props) {
        return loadProps(new File(path), props);
    }

    /**
     * Load properties from file.
     * @param file File.
     * @param props Original properties.
     */
    public static boolean loadProps(File file, Properties props) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            props.load(is);
            return true;
        }
        catch (Exception e) {
            return false;
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
     * Save properties to file.
     * @param path File path.
     * @param props Properties.
     */
    public static boolean saveProps(String path, Properties props) {
        return saveProps(new File(path), props);
    }

    /**
     * Save properties to file.
     * @param file File
     * @param props Properties.
     */
    public static boolean saveProps(File file, Properties props) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            props.store(out, null);
            return true;
        }
        catch (Exception e) {
            return false;
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
     * Read content from file.
     * @param path File path.
     * @return Content.
     * @throws IOException IO exception.
     */
    public static String readContent(String path) throws IOException {
        return readContent(new File(path));
    }

    /**
     * Read content from file.
     * @param path File path.
     * @param charsetName Charset name.
     * @return Content.
     * @throws IOException IO exception.
     */
    public static String readContent(String path, String charsetName) throws IOException {
        return readContent(new File(path), charsetName);
    }
    
    /**
     * Read content from file.
     * @param file File.
     * @return Content.
     * @throws IOException IO exception.
     */
    public static String readContent(File file) throws IOException {
        return readContent(file, "utf-8");
    }
    
    /**
     * Read content from file.
     * @param file File.
     * @param charsetName Charset name.
     * @return Content.
     * @throws IOException IO exception.
     */
    public static String readContent(File file, String charsetName) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray);
        fis.close();

        return new String(bytesArray, charsetName);   
    }
}
