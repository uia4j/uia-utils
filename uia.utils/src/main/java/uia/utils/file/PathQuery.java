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
     *
     * @param root
     * @param pathPattern
     * @param rolling
     */
    public PathQuery(String root, String pathPattern, TimeRollingType rolling) {
        this.root = root;
        this.pathPattern = new SimpleDateFormat(pathPattern);
        this.rolling = rolling;
    }

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
