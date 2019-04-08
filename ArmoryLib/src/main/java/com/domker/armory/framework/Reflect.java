package com.domker.armory.framework;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射库的类
 * <p>
 * Created by wanlipeng on 2019/4/8 5:17 PM
 */
public final class Reflect {

    public static Object getField(String clazzName, Object target, String name) throws Exception {
        return getField(Class.forName(clazzName), target, name);
    }

    public static <T> T getField(Object target, String name) throws Exception {
        return getField(target.getClass(), target, name);
    }

    public static <T> T getField(Class clazz, Object target, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return (T) field.get(target);
    }

    public static <T> T getFieldNoException(Object target, String name) {
        return getFieldNoException(target.getClass(), target, name);
    }

    public static <T> T getFieldNoException(Class clazz, Object target, String name) {
        while (clazz != null) {
            try {
                return Reflect.getField(clazz, target, name);
            } catch (Exception e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }

    public static void setField(String clazzName, Object target, String name, Object value) throws Exception {
        setField(Class.forName(clazzName), target, name, value);
    }

    public static void setField(Class clazz, Object target, String name, Object value) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    public static void setFieldNoException(String clazzName, Object target, String name, Object value) {
        try {
            setFieldNoException(Class.forName(clazzName), target, name, value);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setFieldNoException(Class clazz, Object target, String name, Object value) {
        try {
            Reflect.setField(clazz, target, name, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invoke(String clazzName, Object target, String name, Object... args)
            throws Exception {
        return invoke(Class.forName(clazzName), target, name, args);
    }

    @SuppressWarnings("unchecked")
    public static Object invoke(Class clazz, Object target, String name, Object... args)
            throws Exception {
        Class[] parameterTypes = null;
        if (args != null) {
            parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }

        Method method = clazz.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method.invoke(target, args);
    }

    public static Object invoke(String clazzName, Object target, String name, Class[] parameterTypes, Object... args)
            throws Exception {
        return invoke(Class.forName(clazzName), target, name, parameterTypes, args);
    }

    @SuppressWarnings("unchecked")
    public static Object invoke(Class clazz, Object target, String name, Class[] parameterTypes, Object... args)
            throws Exception {
        Method method = clazz.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method.invoke(target, args);
    }

    public static Object invokeNoException(String clazzName, Object target, String name, Class[] parameterTypes, Object... args) {
        try {
            return invokeNoException(Class.forName(clazzName), target, name, parameterTypes, args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Object invokeNoException(Class clazz, Object target, String name, Class[] parameterTypes, Object... args) {
        try {
            return invoke(clazz, target, name, parameterTypes, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
