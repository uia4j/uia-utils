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
    public static void loadProps(String path, Properties props) {
        loadProps(new File(path), props);
    }

    /**
     * Load properties from file.
     * @param file File.
     * @param props Original properties.
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
     * Save properties to file.
     * @param path File path.
     * @param props Properties.
     */
    public static void saveProps(String path, Properties props) {
        saveProps(new File(path), props);
    }

    /**
     * Save properties to file.
     * @param file File
     * @param props Properties.
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
     * Read content from file.
     * @param path File path.
     * @return Content.
     * @throws IOException IO exception.
     */
    public static String readContent(String path) throws IOException {
        return readContent(new File(path));
        // return new String(Files.readAllBytes(Paths.get(path)));
    }
    
    /**
     * Read content from file.
     * @param file File.
     * @return Content.
     * @throws IOException IO exception.
     */
    public static String readContent(File file) throws IOException {
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray);
        fis.close();

        return new String(bytesArray);   
    }
}
