package uia.utils.file;

import java.util.Calendar;

import org.junit.Test;

public class PathQueryTest {

    @Test
    public void testSelectHour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.DATE, 7);
        cal.set(Calendar.HOUR_OF_DAY, 22); 
        cal.set(Calendar.MINUTE, 57);
        cal.set(Calendar.SECOND, 3);
        cal.set(Calendar.MILLISECOND, 0);

        long begin = cal.getTimeInMillis();
        long end = begin + 7000 * 1000;

        PathQuery query = new PathQuery("data/min/", "yyyy/MM/dd/HH", TimeRollingType.HOUR);
        for(FileQuery q : query.select(begin, end)) {
            System.out.println(q);
        }
    }

    @Test
    public void testSelectDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2017);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.DATE, 7);
        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 57);
        cal.set(Calendar.SECOND, 3);
        cal.set(Calendar.MILLISECOND, 0);

        long begin = cal.getTimeInMillis();
        long end = begin + 7000 * 1000;

        PathQuery query = new PathQuery("data/min/", "yyyy/MM/dd", TimeRollingType.DAY);
        for(FileQuery q : query.select(begin, end)) {
            System.out.println(q);
        }
    }
}
