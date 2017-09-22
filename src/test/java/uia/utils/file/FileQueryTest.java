package uia.utils.file;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class FileQueryTest {

    @Test
    public void testSelectHour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.DATE, 7);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);   // !!

        long begin = cal.getTimeInMillis();
        long end = begin + 3600 * 1000;

        PathQuery query = new PathQuery("data/", "yyyy/MM/dd", TimeRollingType.DAY);
        for(FileQuery q : query.select(begin, end)) {
            List<FileInfo> fis = q.select("HR_", ".json", "yyyyMMddHH", TimeRollingType.HOUR);
            for(FileInfo fi : fis) {
                System.out.println(fi);
            }
        }
    }

    @Test
    public void testSelectMinute() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.DATE, 7);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 57);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);   // !!

        long begin = cal.getTimeInMillis();
        long end = begin + 10 * 60 * 1000;

        PathQuery query = new PathQuery("data/", "yyyy/MM/dd", TimeRollingType.DAY);
        for(FileQuery q : query.select(begin, end)) {
            List<FileInfo> fis = q.select("MIN_", ".json", "yyyyMMddHHmm", TimeRollingType.MINUTE);
            for(FileInfo fi : fis) {
                System.out.println(fi);
            }
        }
    }
}
