package cn.memoryzy.code.action.method;

import cn.hutool.core.util.StrUtil;
import cn.memoryzy.code.action.GenerateMethodCodeAbstractAction;
import cn.memoryzy.code.bundle.CodeWeaverBundle;
import cn.memoryzy.code.constant.CodeFragments;
import cn.memoryzy.code.model.JavaPropertyMeta;
import cn.memoryzy.code.model.MethodParameter;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.psi.util.PsiUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Memory
 * @since 2025/4/24
 */
public class GenerateGetterAction extends GenerateMethodCodeAbstractAction {

    public GenerateGetterAction() {
        super(CodeWeaverBundle.message("action.listGetter.text"), CodeWeaverBundle.messageOnSystem("action.listGetter.description"));
    }

    @Override
    protected List<String> generateCodeFragment(MethodParameter methodParameter) {
        List<String> codeFragments = new ArrayList<>();
        // 参数名
        String varName = methodParameter.getVarName();
        // 包含参数的方法
        PsiMethod containingMethod = methodParameter.getContainingMethod();

        for (JavaPropertyMeta propertyMeta : methodParameter.getPropertyMetas()) {
            PsiField field = propertyMeta.getField();
            String fieldName = suggestNonConflictingVariableName(field.getName(), containingMethod);
            String fieldType = getFieldType(field.getType());
            String methodName = propertyMeta.getGetterMethod().getName();
            codeFragments.add(StrUtil.format(CodeFragments.GETTER_TEMPLATE, fieldType, fieldName, varName, methodName));
        }

        return codeFragments;
    }

    private String getFieldType(PsiType type) {
        String fieldType = type.getPresentableText();
        // 考虑内部类的情况
        PsiClass psiClass = PsiTypesUtil.getPsiClass(type);
        if (Objects.nonNull(psiClass)) {
            String packageName = PsiUtil.getPackageName(psiClass);
            if (StrUtil.isNotBlank(packageName) && !packageName.startsWith("java.")) {
                // 内部类会存在值
                PsiClass containingClass = psiClass.getContainingClass();
                if (Objects.nonNull(containingClass)) {
                    fieldType = containingClass.getName() + "." + psiClass.getName();
                }
            }
        }

        return fieldType;
    }

}
