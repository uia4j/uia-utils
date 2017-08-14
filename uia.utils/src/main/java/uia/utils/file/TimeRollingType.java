package uia.utils.file;

import java.util.Calendar;

public enum TimeRollingType {

    MINUTE(Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND),

    HOUR(Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND),

    DAY(Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND),

    MONTH(Calendar.MONTH, Calendar.DATE, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND);

    private int[] fields;

    TimeRollingType(int... fields) {
        this.fields = fields;
    }

    public synchronized long next(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.add(this.fields[0], 1);
        return cal.getTimeInMillis();
    }

    public synchronized long peroidFrom(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        for (int i = 1; i < this.fields.length; i++) {
            cal.set(this.fields[i], 0);
        }
        return cal.getTimeInMillis();
    }

    public synchronized long peroidTo(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        cal.add(this.fields[0], 1);
        for (int i = 1; i < this.fields.length; i++) {
            cal.set(this.fields[i], 0);
        }
        return cal.getTimeInMillis();
    }

}
