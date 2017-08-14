package uia.utils.file;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kyle K. Lin
 *
 */
public class FileQuery {

    private final static SimpleDateFormat FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final String path;

    public final long begin;

    public final long end;

    public FileQuery(String path, long begin, long end) {
        this.path = path;
        this.begin = begin;
        this.end = end;
    }

    public List<FileInfo> select(String prefix, String postfix, String timePattern, TimeRollingType rolling) {
        ArrayList<FileInfo> result = new ArrayList<FileInfo>();

        SimpleDateFormat fmt = new SimpleDateFormat(timePattern);

        long timeFrom = this.begin;
        long timeTo = Math.min(this.end, rolling.peroidTo(this.begin));
        while (timeFrom < this.end) {
            String file = String.format("%s%s%s%s",
                    this.path,
                    prefix,
                    fmt.format(new Date(timeFrom)),
                    postfix);

            result.add(new FileInfo(file, timeFrom, timeTo));

            timeFrom = timeTo;
            timeTo = Math.min(this.end, rolling.peroidTo(timeFrom));
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format("%s, time: %s~%s",
                this.path,
                FMT.format(new Date(this.begin)),
                FMT.format(new Date(this.end)));
    }

}
