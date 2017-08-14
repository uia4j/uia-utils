package uia.utils.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Path utilities.
 *
 * @author Kyle K. Lin
 *
 */
public class PathUtils {

    /**
     * Get file names.
     * @param path Path.
     * @return File names.
     * @throws IOException IO exception.
     */
    public static List<String> listFiles(final String path, final String ext) throws IOException {
        final ArrayList<String> fileNames = new ArrayList<String>();

        for (final File fileEntry : new File(path).listFiles()) {
            String f = fileEntry.getAbsolutePath().toLowerCase();
            if (fileEntry.isDirectory()) {
                fileNames.addAll(listFiles(f, ext));
            }
            else if (f.endsWith(ext)) {
                fileNames.add(fileEntry.getAbsolutePath());
            }
        }

        return fileNames;
    }
}
