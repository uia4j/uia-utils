package uia.utils.file;

import org.junit.Assert;
import org.junit.Test;

public class TimeRollingTypeTest {

    @Test
    public void testMinute() {
        long now = System.currentTimeMillis();
        long next = TimeRollingType.MINUTE.next(now);
        long min = TimeRollingType.MINUTE.peroidFrom(now);
        long max = TimeRollingType.MINUTE.peroidTo(now);

        Assert.assertEquals(60000, next - now);
        Assert.assertEquals(60000, max - min);
        
        // -----|-----|----------|-----|----------> t
        //      now   min        next  max
    }

    @Test
    public void testHour() {
        long now = System.currentTimeMillis();
        long next = TimeRollingType.HOUR.next(now);
        long min = TimeRollingType.HOUR.peroidFrom(now);
        long max = TimeRollingType.HOUR.peroidTo(now);

        Assert.assertEquals(3600000, next - now);
        Assert.assertEquals(3600000, max - min);
    }

    @Test
    public void testDay() {
        long now = System.currentTimeMillis();
        long next = TimeRollingType.DAY.next(now);
        long min = TimeRollingType.DAY.peroidFrom(now);
        long max = TimeRollingType.DAY.peroidTo(now);

        Assert.assertEquals(86400000, next - now);
        Assert.assertEquals(86400000, max - min);
    }

    @Test
    public void testMonth() {
        long now = System.currentTimeMillis();
        long next = TimeRollingType.MONTH.next(now);
        long min = TimeRollingType.MONTH.peroidFrom(now);
        long max = TimeRollingType.MONTH.peroidTo(now);

        Assert.assertEquals(next - now, max - min);
    }
}
