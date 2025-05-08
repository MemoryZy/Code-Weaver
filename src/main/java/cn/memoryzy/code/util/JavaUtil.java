package cn.memoryzy.code.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.memoryzy.code.constant.JavaTypes;
import cn.memoryzy.code.enums.PsiElementType;
import cn.memoryzy.code.model.JavaPropertyMeta;
import cn.memoryzy.code.model.PsiElementContext;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.impl.source.PsiImmediateClassType;
import com.intellij.psi.util.PsiTypesUtil;

import java.util.*;

/**
 * @author Memory
 * @since 2025/4/27
 */
public class JavaUtil {

    /**
     * 是否处于Java文件中
     *
     * @param dataContext 数据上下文
     * @return true -> 处于；false -> 不处于
     */
    public static boolean isJavaFile(DataContext dataContext) {
        // 获取当前选中的 PsiClass
        PsiFile psiFile = dataContext.getData(CommonDataKeys.PSI_FILE);
        return psiFile instanceof PsiJavaFile;
    }

    /**
     * 判断是否为引用类型（自己项目中的类）（不包括String、BigDecimal之类的）
     *
     * @param psiType 类型
     * @return true，引用类型；false，不为引用类型
     */
    public static boolean isNonJdkType(PsiType psiType) {
        // 不为引用类型
        if ((!(psiType instanceof PsiClassReferenceType)) && (!(psiType instanceof PsiImmediateClassType))) {
            return false;
        }

        // 全限定名（基本类型就只有基本类型名 long、int）
        String canonicalText = psiType.getCanonicalText();

        // 排除可继承的类型，如果是 集合、BigDecimal、Date、Time等，不将其视为对象
        if (isTypeAssignableToAny(psiType, JavaTypes.COLLECTION_FQN)
                || isTypeAssignableToAny(psiType, JavaTypes.BIG_DECIMAL_FQN)
                || isTypeAssignableToAny(psiType, JavaTypes.DATE_TIME_FQN)
                || isTypeAssignableToAny(psiType, JavaTypes.DATE_FQN)
                || isTypeAssignableToAny(psiType, JavaTypes.TIME_FQN)) {
            return false;
        }

        // 判断是否为java包其他类
        return !StrUtil.startWith(canonicalText, "java.");
    }

    public static boolean isTypeAssignableToAny(PsiType psiType, String[] candidateClassNames) {
        // 顶级接口预判断（Collection 接口父接口只有 Iterable 接口）
        String canonicalText = psiType.getCanonicalText();
        for (String className : candidateClassNames) {
            if (canonicalText.startsWith(className)) {
                return true;
            }
        }

        Set<String> superTypeNames = new HashSet<>();
        collectSuperTypeNames(psiType.getSuperTypes(), superTypeNames);

        return superTypeNames.stream()
                .anyMatch(superType ->
                        Arrays.stream(candidateClassNames).anyMatch(superType::startsWith));
    }


    private static void collectSuperTypeNames(PsiType[] psiTypes, Set<String> collector) {
        for (PsiType psiType : psiTypes) {
            collector.add(psiType.getCanonicalText());
            PsiType[] superTypes = psiType.getSuperTypes();

            if (ArrayUtil.isNotEmpty(superTypes)) {
                collectSuperTypeNames(superTypes, collector);
            }
        }
    }


    /**
     * 当前 Java 类中是否存在属性
     *
     * @param psiClass 类
     * @return true -> 存在；false -> 不存在
     */
    public static boolean hasProperty(PsiClass psiClass) {
        return CollUtil.isNotEmpty(resolveAllProperties(psiClass));
    }

    /**
     * 获取该类的所有字段（除去静态字段）
     *
     * @param psiClass class
     * @return 所有字段
     */
    public static PsiField[] getNonStaticFields(PsiClass psiClass) {
        return (Objects.isNull(psiClass))
                ? new PsiField[0]
                : Arrays.stream(psiClass.getAllFields())
                .filter(el -> !el.hasModifierProperty(PsiModifier.STATIC))
                .toArray(PsiField[]::new);
    }

    /**
     * 获取该类的所有方法（除去静态方法）
     *
     * @param psiClass class
     * @return 所有方法
     */
    public static PsiMethod[] getNonStaticMethods(PsiClass psiClass) {
        return (Objects.isNull(psiClass))
                ? new PsiMethod[0]
                : Arrays.stream(psiClass.getAllMethods())
                .filter(el -> !el.hasModifierProperty(PsiModifier.STATIC))
                .toArray(PsiMethod[]::new);
    }

    /**
     * 解析类中所有有效属性（必须同时存在字段、getter 和 setter）
     *
     * @param psiClass 目标类
     * @return 有效属性列表（若无满足条件的属性，返回空列表）
     */
    public static List<JavaPropertyMeta> resolveAllProperties(PsiClass psiClass) {
        PsiMethod[] methods = getNonStaticMethods(psiClass);
        List<JavaPropertyMeta> propertyMetas = new ArrayList<>();

        for (PsiField field : getNonStaticFields(psiClass)) {
            boolean isFinal = field.hasModifierProperty(PsiModifier.FINAL);
            PsiMethod getter = findGetterByField(field, methods);
            PsiMethod setter = isFinal ? null : findSetterByField(field, methods);

            if (Objects.isNull(getter) || (!isFinal && Objects.isNull(setter))) {
                continue;
            }

            propertyMetas.add(new JavaPropertyMeta(field, setter, getter));
        }

        return propertyMetas;
    }


    public static PsiMethod findSetterByField(PsiField field, PsiMethod[] methods) {
        String fieldName = field.getName();
        PsiType fieldType = field.getType();

        String methodName;
        if (Objects.equals(PsiType.BOOLEAN, fieldType)) {
            if (fieldName.startsWith("is")) {
                methodName = StrUtil.isUpperCase(fieldName.substring(2, 3))
                        // isXxx -> setXxx (boolean 基本类型；is的后一个字符为大写)
                        ? StrUtil.genSetter(fieldName.substring(2))
                        // isxxx -> setIsxxx (is的后一个字符为小写)
                        : StrUtil.genSetter(fieldName);
            } else {
                methodName = StrUtil.genSetter(fieldName);
            }

        } else {
            methodName = StrUtil.genSetter(fieldName);
        }

        return Arrays.stream(methods)
                .filter(el -> {
                    // 名称匹配
                    if (!Objects.equals(methodName, el.getName())) return false;

                    // 型参数匹配
                    PsiParameter[] parameters = el.getParameterList().getParameters();
                    if (parameters.length != 1) return false;

                    // 形参类型匹配
                    return Objects.equals(fieldType, parameters[0].getType());
                })
                .findFirst()
                .orElse(null);
    }


    public static PsiMethod findGetterByField(PsiField field, PsiMethod[] methods) {
        String fieldName = field.getName();
        PsiType fieldType = field.getType();

        String methodName;
        if (Objects.equals(PsiType.BOOLEAN, fieldType)) {
            if (fieldName.startsWith("is")) {
                methodName = StrUtil.isUpperCase(fieldName.substring(2, 3))
                        // isXxx -> isXxx (is的后一个字符为大写)
                        ? fieldName
                        // isxxx -> isIsxxx (is的后一个字符为小写)
                        : "is" + StrUtil.upperFirst(fieldName);
            } else {
                methodName = "is" + StrUtil.upperFirst(fieldName);
            }

        } else {
            methodName = StrUtil.genGetter(fieldName);
        }

        return Arrays.stream(methods)
                .filter(el -> {
                    // 名称匹配
                    if (!Objects.equals(methodName, el.getName())) return false;

                    // 形参数匹配
                    if (el.getParameterList().getParametersCount() > 0) return false;

                    // 形参类型匹配
                    return Objects.equals(fieldType, el.getReturnType());
                })
                .findFirst()
                .orElse(null);
    }


    public static boolean isStaticInnerClass(PsiClass psiClass) {
        return psiClass.hasModifierProperty(PsiModifier.STATIC) && psiClass.getContainingClass() != null;
    }

    public static Set<String> listVariables(PsiMethod method) {
        Set<String> varList = new HashSet<>();
        method.acceptChildren(new JavaRecursiveElementVisitor() {
            @Override
            public void visitParameter(PsiParameter parameter) {
                super.visitParameter(parameter);
                varList.add(parameter.getName());
            }

            @Override
            public void visitLocalVariable(PsiLocalVariable variable) {
                super.visitLocalVariable(variable);
                varList.add(variable.getName());
            }
        });

        return varList;
    }

    public static PsiElementContext getPsiClass2(PsiJavaCodeReferenceElement referenceElement) {
        PsiType type = null;
        PsiClass typeClass = null;
        PsiElementType elementType = null;

        PsiElement resolve = referenceElement.resolve();
        if (resolve instanceof PsiClass) {
            typeClass = (PsiClass) resolve;
            type = PsiTypesUtil.getClassType(typeClass);
            elementType = PsiElementType.REFERENCED_CLASS;

        } else if (resolve instanceof PsiLocalVariable) {
            type = ((PsiLocalVariable) resolve).getType();
            typeClass = getPsiClass(type);
            elementType = PsiElementType.LOCAL_VARIABLE;

        } /*else if (resolve instanceof PsiField) {
            PsiType psiType = ((PsiField) resolve).getType();
            if (isNonJdkType(psiType)) {
                psiClass = PsiTypesUtil.getPsiClass(psiType);
            } else if (isCollectionOrArray(psiType)) {
                psiClass = getGenericTypeOfCollection(referenceElement.getProject(), psiType);
            }
            target = JsonConversionTarget.CLASS_FIELD;

        }*/ else if (resolve instanceof PsiParameter) {
            type = ((PsiParameter) resolve).getType();
            typeClass = getPsiClass(type);
            elementType = PsiElementType.PARAMETER;
        }

        return new PsiElementContext(elementType, typeClass, type);
    }

    public static PsiClass getPsiClass(PsiType psiType) {
        return psiType instanceof PsiClassReferenceType ? ((PsiClassReferenceType) psiType).resolve() : null;
    }

    public static boolean isCollectionOrArray(PsiType psiType) {
        return isTypeAssignableToAny(psiType, JavaTypes.COLLECTION_FQN) || psiType instanceof PsiArrayType;
    }

    public static String removeGenericTags(String str) {
        if (str == null) return null;
        // 正则说明：匹配 < 后跟非 > 字符直到遇到 >（防止跨多对标签）
        return str.replaceAll("<([^>]*)>", "");
    }

}
