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
public class PathQuery {

    private final String root;

    private final TimeRollingType rolling;

    private SimpleDateFormat pathPattern;

    /**
     * Constructor.
     * @param root Root path.
     * @param pathPattern Date time naming pattern.
     * @param rolling Rolling type of file name.
     */
    public PathQuery(String root, String pathPattern, TimeRollingType rolling) {
        this.root = root;
        this.pathPattern = new SimpleDateFormat(pathPattern);
        this.rolling = rolling;
    }

    /**
     * Select files depending on file name with date time pattern.
     * @param begin Begin time.
     * @param end End time.
     * @return Result.
     */
    public synchronized List<FileQuery> select(final long begin, final long end) {
        ArrayList<FileQuery> result = new ArrayList<FileQuery>();

        long timeFrom = Math.max(begin, this.rolling.peroidFrom(begin));
        long timeTo = Math.min(end, this.rolling.peroidTo(begin));
        while (timeFrom < end) {
            String path = String.format("%s%s/",
                    this.root,
                    this.pathPattern.format(new Date(timeFrom)));

            result.add(new FileQuery(path, timeFrom, timeTo));

            timeFrom = timeTo;
            timeTo = Math.min(end, this.rolling.peroidTo(timeFrom));
        }

        return result;
    }
}
