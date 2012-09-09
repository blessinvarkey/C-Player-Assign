package cs5031.catalogplayer.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cs5031.catalogplayer.catalog.exist.ExceptionElement;

/**
 * This class implements common logic used in unit tests
 *
 */
public class TestHelper {
	/**
	 * Invoke a non-public method
	 * @param instance Instance of the object whose method will be invoked
	 * @param methodName Name of the method to invoke
	 * @param arguments List of arguments. Type is important
	 * @return Value returned by the method, or null if it returns void
	 * @throws SecurityException  Caller doesn't have the sufficient privileges to call the method or use reflection
	 * @throws NoSuchMethodException If no method was found
	 * @throws IllegalArgumentException if the method is an instance method and the specified object argument is not an instance of the class or interface declaring the underlying method (or of a subclass or implementor thereof); if the number of actual and formal parameters differ; if an unwrapping conversion for primitive arguments fails; or if, after possible unwrapping, a parameter value cannot be converted to the corresponding formal parameter type by a method invocation conversion.
	 * @throws IllegalAccessException if this Method object enforces Java language access control and the underlying method is inaccessible.
	 * @throws InvocationTargetException Exception thrown by the invoked method
	 */
	public static Object invokeNonPublic(Object instance, String methodName, Object[] arguments) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?>[] argumentTypes = new Class<?>[arguments.length];
		for (int i = 0; i < argumentTypes.length; i++) {
			argumentTypes[i] = arguments[i].getClass();
		}
		Class<?> type = instance.getClass();
		Method method = type.getMethod(methodName, argumentTypes);
		return method.invoke(instance, arguments);		
	}
	
	/**
	 * Invoke a non-public method
	 * @param instance Instance of the object whose method will be invoked
	 * @param methodName Name of the method to invoke
	 * @param paramTypes Types of parameters
	 * @param arguments List of arguments
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object invokeNonPublic(Object instance, String methodName, Class<?>[] paramTypes, Object[] arguments) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Class<?> type = instance.getClass();
		Method method = type.getMethod(methodName, paramTypes);
		return method.invoke(instance, arguments);
	}
	
	
}
