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

    /**
     * 判断末尾是否为数字
     *
     * @param str 文本
     * @return 若为数字，则为true；反之为false
     */
    public static boolean endsWithDigitRegex(String str) {
        return StrUtil.isNotBlank(str) && str.matches(".*\\d$");
    }


    /**
     * 区分开前缀和数字
     *
     * @param str 文本
     * @return 数组[0]: 前缀；数组[1]: 数字
     */
    public static String[] splitEndNumber(String str) {
        if (StrUtil.isBlank(str)) {
            return new String[]{"", ""};
        }
        int index = str.length() - 1;
        // 逆序查找第一个非数字的位置
        while (index >= 0 && Character.isDigit(str.charAt(index))) {
            index--;
        }
        // 分割字符串：前缀部分 + 末尾数字部分
        String prefix = str.substring(0, index + 1);
        String number = str.substring(index + 1);
        return new String[]{prefix, number};
    }


}
