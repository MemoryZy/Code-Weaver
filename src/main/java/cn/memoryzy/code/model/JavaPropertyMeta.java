package cn.memoryzy.code.model;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;

/**
 * @author Memory
 * @since 2025/4/30
 */
public class JavaPropertyMeta {

    private PsiField field;

    private PsiMethod setterMethod;

    private PsiMethod getterMethod;

    public JavaPropertyMeta(PsiField field, PsiMethod setterMethod, PsiMethod getterMethod) {
        this.field = field;
        this.setterMethod = setterMethod;
        this.getterMethod = getterMethod;
    }

    public PsiField getField() {
        return field;
    }

    public void setField(PsiField field) {
        this.field = field;
    }

    public PsiMethod getSetterMethod() {
        return setterMethod;
    }

    public void setSetterMethod(PsiMethod setterMethod) {
        this.setterMethod = setterMethod;
    }

    public PsiMethod getGetterMethod() {
        return getterMethod;
    }

    public void setGetterMethod(PsiMethod getterMethod) {
        this.getterMethod = getterMethod;
    }
}
