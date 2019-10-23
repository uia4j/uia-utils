package uia.utils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

public class ObjectUtils {

	/**
	 * Check if value is not null.
	 * 
	 * @param value The value.
	 * @return The value self.
	 * @throws NullPointerException Raise if value is null.
	 */
	public <T> T notNull(T value) throws NullPointerException {
		if(value == null) {
			throw new NullPointerException();
		}
		return value;
	}

	/**
	 * Check if a value is not null.
	 * 
	 * @param value The value.
	 * @param message Exception message.
	 * @return The value self.
	 * @throws NullPointerException Raise if value is null.
	 */
	public <T> T notNull(T value, String message) throws NullPointerException {
		if(value == null) {
			throw new NullPointerException(message);
		}
		return value;
	}
	
	/**
	 * Get a value.
	 * 
	 * @param value The current value.
	 * @param defaultValue The value to be return if the current value is null.
	 * @return Value The current value or default value.
	 */
	public <T> T value(T value, T defaultValue) {
		return value == null ? defaultValue : value;
	}

    /**
     * Apply value to the property of the object.
     *
     * @param obj The block converter.
     * @param propName The property name.
     * @param value The property value.
     * @return Result.
     * @throws IntrospectionException bean info exception.
     * @throws IllegalAccessException bean info exception.
     * @throws IllegalArgumentException bean info exception.
     * @throws InvocationTargetException bean info exception.
     */
    public static boolean write(Object obj, String propName, Object value) 
    		throws IntrospectionException, 
    		IllegalArgumentException, 
    		IllegalAccessException, 
    		InvocationTargetException {
    	return PropertyBeanUtils.write(obj, propName, value);
    }

    /**
     * Get the property value of the object.
     *
     * @param obj The block converter.
     * @param propName The property name.
     * @return The property value or null if not exists.
     * @throws IntrospectionException bean info exception.
     * @throws IllegalAccessException bean info exception.
     * @throws IllegalArgumentException bean info exception.
     * @throws InvocationTargetException bean info exception.
     */
    public static Object read(Object obj, String propName)
            throws IntrospectionException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
    	return PropertyBeanUtils.read(obj, propName);
    }
}
