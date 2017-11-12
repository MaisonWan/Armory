package com.domker.armory.framework

import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Created by wanlipeng on 2017/11/12.
 */
object Reflect {

    /**
     * 调用静态方法
     */
    fun invokeStaticMethod(class_name: String, method_name: String): Any? {
        return invokeStaticMethod(class_name, method_name, null, null)
    }

    fun invokeStaticMethod(class_name: String, method_name: String, pareTyple: Array<Class<*>>?, pareVaules: Array<Any>?): Any? {
        try {
            val clazz = Class.forName(class_name)
            val method: Method
            method = if (pareTyple == null) {
                clazz.getMethod(method_name)
            } else {
                clazz.getMethod(method_name, *pareTyple)
            }
            return if (pareVaules == null) {
                method.invoke(null)
            } else method.invoke(null, *pareVaules)
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 调用类或对象的方法并返回结果
     *
     * @param clazz          类
     * @param methodName     方法名
     * @param obj            调用该方法的对象，如果是静态方法则传null
     * @param args           参数，如果没有则传null
     * @param parameterTypes 方法参数类型的class，如果没有则传null
     * @return 调用结果
     */
    fun invokeMethod(clazz: Class<*>, obj: Any, methodName: String, args: Array<Any>, vararg parameterTypes: Class<*>): Any? {
        try {
            // 反射类指定方法
            val method = clazz.getDeclaredMethod(methodName, *parameterTypes)
            method.isAccessible = true // 暴力反射
            // 调用方法并返回结果
            return method.invoke(obj, *args)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 调用类或对象的方法并返回结果
     *
     * @param className      类名
     * @param methodName     方法名
     * @param obj            调用该方法的对象，如果是静态方法则传null
     * @param args           参数，如果没有则传null
     * @param parameterTypes 方法参数类型的class，如果没有则传null
     * @return 调用结果
     */
    fun invokeMethod(className: String, obj: Any, methodName: String, args: Array<Any>?, vararg parameterTypes: Class<*>): Any? {
        var methodArgs = args
        var parameter = parameterTypes
        try {
            // 防止空指针错误
            if (parameter == null) {
                parameter = arrayOf()
            }
            if (methodArgs == null) {
                methodArgs = arrayOf()
            }
            // 加载类的字节码
            val clazz = Class.forName(className)
            return invokeMethod(clazz, obj, methodName, methodArgs, *parameter)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun invokeMethod(class_name: String, method_name: String, obj: Any, pareTyple: Array<Class<*>>, pareVaules: Array<Any>): Any? {
        try {
            val obj_class = Class.forName(class_name)
            val method = obj_class.getMethod(method_name, *pareTyple)
            return method.invoke(obj, *pareVaules)
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取对象或类某个字段的值
     *
     * @param clazz     类
     * @param obj       对象，如果是静态字段则传null
     * @param fieldName 字段名称
     * @return 字段的值
     */
    fun getFieldValue(clazz: Class<*>, obj: Any, fieldName: String): Any? {
        try {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            return field.get(obj)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 设置类的属性（包括私有和保护）
     *
     * @param classname
     * @param filedName
     * @param obj
     * @param filedVaule
     */
    fun setFieldOjbect(classname: String, filedName: String, obj: Any, filedValue: Any) {
        try {
            val clazz = Class.forName(classname)
            val field = clazz.getDeclaredField(filedName)
            field.isAccessible = true
            field.set(obj, filedValue)
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 反射得到类的属性（包括私有和保护）
     *
     * @param class_name
     * @param obj
     * @param filedName
     * @return
     */
    fun getFieldOjbect(class_name: String, obj: Any, filedName: String): Any? {
        try {
            val clazz = Class.forName(class_name)
            val field = clazz.getDeclaredField(filedName)
            field.isAccessible = true
            return field.get(obj)
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取对象或类某个字段的值
     *
     * @param className 类名
     * @param obj       对象，如果是静态字段则传null
     * @param fieldName 字段名称
     * @return 字段的值
     */
    fun getFieldValue(className: String, obj: Any, fieldName: String): Any? {
        try {
            val clazz = Class.forName(className)
            return getFieldValue(clazz, obj, fieldName)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 设置对象或类某个字段的值
     *
     * @param clazz     类
     * @param obj       对象，如果是静态字段则传null
     * @param fieldName 字段名称
     * @param value     字段值
     * @return 是否设置成功
     */
    fun setFieldValue(clazz: Class<*>, obj: Any, fieldName: String, value: Any): Boolean {
        try {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(obj, value)
            return true
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 设置对象或类某个字段的值
     *
     * @param className 类名
     * @param obj       对象，如果是静态字段则传null
     * @param fieldName 字段名称
     * @param value     字段值
     * @return 是否设置成功
     */
    fun setFieldValue(className: String, obj: Any, fieldName: String, value: Any): Boolean {
        try {
            val clazz = Class.forName(className)
            setFieldValue(clazz, obj, fieldName, value)
            return true
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 根据类名实例化一个对象
     *
     * @param className 类名
     * @return 对象实例，如果实例化失败返回null
     */
    fun newInstance(className: String): Any? {
        try {
            val clazz = Class.forName(className)
            return clazz.newInstance()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return null
    }

    @Throws(NoSuchFieldException::class)
    fun findField(instance: Any, name: String): Field {
        var clazz: Class<*>? = instance.javaClass
        while (clazz != null) {
            try {
                val field = clazz.getDeclaredField(name)
                if (!field.isAccessible) {
                    field.isAccessible = true
                }
                return field
            } catch (e: NoSuchFieldException) {
                // ignore and search next
            }
            clazz = clazz.superclass
        }
        throw NoSuchFieldException("Field " + name + " not found in " + instance.javaClass)
    }
}