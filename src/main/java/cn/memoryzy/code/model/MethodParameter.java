package cn.memoryzy.code.model;

import cn.memoryzy.code.enums.PsiElementType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;

import java.util.List;

/**
 * @author Memory
 * @since 2025/4/28
 */
public class MethodParameter {

    /**
     * 参数名称
     */
    private String varName;

    /**
     * 参数类型对应的类
     */
    private PsiClass typeClass;

    /**
     * 参数类型对应类的属性列表
     */
    private List<JavaPropertyMeta> propertyMetas;

    /**
     * 包含参数的方法
     */
    private PsiMethod containingMethod;

    /**
     * 当前位置表示的元素类型
     */
    private PsiElementType elementType;

    public MethodParameter(String varName, PsiClass typeClass, List<JavaPropertyMeta> propertyMetas, PsiMethod containingMethod, PsiElementType elementType) {
        this.varName = varName;
        this.typeClass = typeClass;
        this.propertyMetas = propertyMetas;
        this.containingMethod = containingMethod;
        this.elementType = elementType;
    }

    public String getVarName() {
        return varName;
    }

    public MethodParameter setVarName(String varName) {
        this.varName = varName;
        return this;
    }

    public PsiClass getTypeClass() {
        return typeClass;
    }

    public MethodParameter setTypeClass(PsiClass typeClass) {
        this.typeClass = typeClass;
        return this;
    }

    public List<JavaPropertyMeta> getPropertyMetas() {
        return propertyMetas;
    }

    public MethodParameter setPropertyMetas(List<JavaPropertyMeta> propertyMetas) {
        this.propertyMetas = propertyMetas;
        return this;
    }

    public PsiMethod getContainingMethod() {
        return containingMethod;
    }

    public MethodParameter setContainingMethod(PsiMethod containingMethod) {
        this.containingMethod = containingMethod;
        return this;
    }

    public PsiElementType getElementType() {
        return elementType;
    }

    public MethodParameter setElementType(PsiElementType elementType) {
        this.elementType = elementType;
        return this;
    }
}
