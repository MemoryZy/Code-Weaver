package cn.memoryzy.code.util;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author Memory
 * @since 2025/4/21
 */
public class CodeWeaverUtil {


    /**
     * 判断对象是否继承自某个类
     *
     * @param obj                对象
     * @param fullyQualifiedName 类的完全限定名
     * @return 是否继承自指定类
     */
    public static boolean isInheritedFrom(Object obj, String fullyQualifiedName) {
        if (obj == null || StrUtil.isBlank(fullyQualifiedName)) {
            return false;
        }

        Class<?> currentClass = obj.getClass();
        while (currentClass != null) {
            if (fullyQualifiedName.equals(currentClass.getName())) {
                return true;
            }
            currentClass = currentClass.getSuperclass();
        }
        return false;
    }


    @SuppressWarnings("UnusedReturnValue")
    public static Object invokeMethod(Object obj, String methodName, Object... params) {
        Method method = getMethod(obj, methodName, params);
        if (Objects.nonNull(method)) {
            return ReflectUtil.invoke(obj, method, params);
        }

        return null;
    }


    public static Method getMethod(Object obj, String methodName, Object... params) {
        Class<?> clazz = obj.getClass();
        Class<?>[] paramTypes = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            paramTypes[i] = params[i].getClass();
        }

        return ReflectUtil.getMethod(clazz, methodName, paramTypes);
    }
}
