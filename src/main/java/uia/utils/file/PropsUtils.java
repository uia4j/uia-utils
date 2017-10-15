package uia.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public final class PropsUtils {

    private Properties props;

    /**
     * Constructor.
     * @param path Path.
     * @throws IOException IO failed.
     */
    public PropsUtils(String path) throws IOException {
        this(new File(path));
    }

    /**
     * Constructor.
     * @param path Path.
     * @param props Initial properties.
     * @throws IOException IO failed.
     */
    public PropsUtils(String path, Properties props) throws IOException {
        this(new File(path), props);
    }

    /**
     * Constructor.
     * @param file File.
     * @throws IOException IO failed.
     */
    public PropsUtils(File file) throws IOException {
        this(file, new Properties());
    }

    /**
     * Constructor.
     * @param file File.
     * @param props Initial properties.
     * @throws IOException IO failed.
     */
    public PropsUtils(File file, Properties props) throws IOException {
        InputStream is = null;
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                props.load(is);
            }
        }
        finally {
            this.props = props;
            if (is != null) {
                try {
                    is.close();
                }
                catch (IOException e) {

                }
            }
        }
    }

    public PropsUtils(Properties props) {
        this.props = props;
    }

    public PropsUtils config(Callback<Properties> callback) {
        callback.accept(this.props);
        return this;
    }

    public void println() {
        this.props.list(System.out);
    }

    public Properties getProperties() {
        return this.props;
    }

    public PropsUtils load(String path) throws IOException {
        File file = new File(path);
        return file.exists() ? new PropsUtils(file, this.props) : this;
    }

    public String getValue(String name, String defaultValue) {
        return this.props.getProperty(name, defaultValue);
    }

    public String getValue(String nameWithArgs, String defaultValue, Object... args) {
        return getValue(String.format(nameWithArgs, args), defaultValue);
    }

    public String getValue(String name, String defaultValue, Callback<String> callback) {
        String value = getValue(name, defaultValue);
        callback.accept(name + "=" + value);
        return value;
    }

    public String getValue(String nameWithArgs, String defaultValue, Callback<String> callback, Object... args) {
        return getValue(String.format(nameWithArgs, args), defaultValue, callback);
    }

    public void setValue(String name, String value) {
        this.props.setProperty(name, value);
    }

    public void setInt(String name, int value) {
        this.props.setProperty(name, "" + value);
    }

    public void setDouble(String name, double value, int scale) {
        this.props.setProperty(name, new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).toString());
    }

    public void setDate(String name, Date time, String pattern) {
        this.props.setProperty(name, new SimpleDateFormat(pattern).format(time));
    }

    public int getInt(String name, int defaultValue) {
        try {
            return Integer.parseInt(this.props.getProperty(name, "" + defaultValue));
        }
        catch (Exception ex) {
            return defaultValue;
        }
    }

    public int getInt(String name, int defaultValue, Callback<String> callback) {
        int result = getInt(name, defaultValue);
        callback.accept(name + "=" + result);
        return result;
    }

    public double getDouble(String name, double defaultValue) {
        try {
            return Double.parseDouble(this.props.getProperty(name, "" + defaultValue));
        }
        catch (Exception ex) {
            return defaultValue;
        }
    }

    public double getDouble(String name, double defaultValue, Callback<String> callback) {
        double result = getDouble(name, defaultValue);
        callback.accept(name + "=" + result);
        return result;
    }

    public boolean isTrue(String name, boolean defaultValue) {
        String b = getValue(name, "N").toUpperCase();
        return "Y".equals(b) || "YES".equals(b) || "TRUE".equals(b);
    }

    public boolean isTrue(String name, boolean defaultValue, Callback<String> callback) {
        boolean result = isTrue(name, defaultValue);
        callback.accept(name + "=" + result);
        return result;
    }

    public Date getDate(String name, String pattern, Date defaultValue) {
        String value = this.props.getProperty(name);
        if (value == null) {
            return defaultValue;
        }

        try {
            return new SimpleDateFormat(pattern).parse(value);
        }
        catch (ParseException e) {
            return defaultValue;
        }
    }

    public Date getDate(String name, String pattern, Date defaultValue, Callback<String> callback) {
        Date result = getDate(name, pattern, defaultValue);
        callback.accept(name + "=" + result);
        return result;
    }

    public void save(String path) throws IOException {
        save(new File(path));
    }

    public void save(File file) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            this.props.store(out, null);
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
    
    public static interface Callback<T> {
        public void accept(T value);
    }
}
