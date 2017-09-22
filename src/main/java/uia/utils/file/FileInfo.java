package uia.utils.file;

/**
 * File information.
 * 
 * @author Kyle K. Lin
 *
 */
public class FileInfo {

    public final String path;

    public final long begin;

    public final long end;

    public FileInfo(String path, long begin, long end) {
        this.path = path;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return this.path;
    }

}
