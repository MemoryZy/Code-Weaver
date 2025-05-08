package cn.memoryzy.code.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.memoryzy.code.constant.CodeFragments;
import cn.memoryzy.code.enums.PsiElementType;
import cn.memoryzy.code.model.JavaPropertyMeta;
import cn.memoryzy.code.model.MethodParameter;
import cn.memoryzy.code.model.PsiElementContext;
import cn.memoryzy.code.util.CodeWeaverUtil;
import cn.memoryzy.code.util.JavaUtil;
import cn.memoryzy.code.util.PlatformUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.UpdateInBackground;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Memory
 * @since 2025/4/28
 */
public abstract class GenerateMethodCodeAbstractAction extends DumbAwareAction implements UpdateInBackground {

    public GenerateMethodCodeAbstractAction(@Nullable String text, @Nullable String description) {
        super(text, description, null);
    }

    public GenerateMethodCodeAbstractAction(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
        super(text, description, icon);
    }


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        DataContext dataContext = e.getDataContext();
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        PsiFile psiFile = PlatformUtil.getPsiFile(dataContext);
        if (null == project || null == psiFile) return;

        PsiElement element = PlatformUtil.getPsiElementByOffset(dataContext);
        MethodParameter methodParameter = resolveMethodParameter(element);

        // 获取当前光标下一行的偏移量
        Editor editor = PlatformUtil.getEditor(dataContext);
        Document document = editor.getDocument();
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int leadSelectionOffset = primaryCaret.getLeadSelectionOffset();
        int nextLineNumber = document.getLineNumber(leadSelectionOffset) + 1;
        int nextLineOffset = document.getLineStartOffset(nextLineNumber);

        // 代码片段
        String codeFragment = combineCodeFragment(methodParameter);

        // 写入
        WriteCommandAction.runWriteCommandAction(project, () -> {
            document.insertString(nextLineOffset, codeFragment);
            CodeStyleManager.getInstance(project).reformatText(psiFile, nextLineOffset, nextLineOffset + codeFragment.length());
        });
    }


    @Override
    public void update(@NotNull AnActionEvent e) {
        boolean enabled = false;
        DataContext dataContext = e.getDataContext();
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        PsiFile psiFile = PlatformUtil.getPsiFile(dataContext);
        if (null != project && null != psiFile) {
            PsiElement element = PlatformUtil.getPsiElementByOffset(dataContext);
            enabled = null != resolveMethodParameter(element);
        }

        e.getPresentation().setEnabled(enabled);
    }


    /**
     * 解析当前选定的方法参数
     *
     * @param element 当前元素
     * @return 方法参数
     */
    public MethodParameter resolveMethodParameter(PsiElement element) {
        if (Objects.isNull(element)) {
            return null;
        }

        // 形参
        PsiParameter parameter = PsiTreeUtil.getParentOfType(element, PsiParameter.class);
        // 变量
        PsiLocalVariable localVariable = PsiTreeUtil.getParentOfType(element, PsiLocalVariable.class);
        // 引用
        PsiJavaCodeReferenceElement codeReference = PsiTreeUtil.getParentOfType(element, PsiJavaCodeReferenceElement.class);
        // 方法
        PsiMethod method = PsiTreeUtil.getParentOfType(element, PsiMethod.class);

        if (Objects.isNull(method) || (Objects.isNull(parameter) && Objects.isNull(localVariable) && Objects.isNull(codeReference))) {
            return null;
        }

        String name = null;
        PsiType type;
        PsiClass typeClass;
        PsiElementType elementType;

        if (Objects.nonNull(parameter)) {
            name = parameter.getName();
            type = parameter.getType();
            typeClass = PsiTypesUtil.getPsiClass(type);
            elementType = PsiElementType.PARAMETER;

        } else if (Objects.nonNull(localVariable)) {
            name = localVariable.getName();
            type = localVariable.getType();
            typeClass = PsiTypesUtil.getPsiClass(type);
            elementType = PsiElementType.LOCAL_VARIABLE;

        } else {
            PsiElementContext context = JavaUtil.getPsiClass2(codeReference);
            type = context.getPsiType();
            typeClass = context.getPsiClass();
            elementType = context.getElementType();
            // 引用类没有变量名
            if (PsiElementType.LOCAL_VARIABLE == elementType || PsiElementType.PARAMETER == elementType) {
                name = codeReference.getReferenceName();
            }
        }

        if (Objects.isNull(typeClass)) return null;

        // 判断是否为自定义类型
        if (!JavaUtil.isNonJdkType(type)) return null;

        // 判断是否存在属性
        List<JavaPropertyMeta> propertyMetas = JavaUtil.resolveAllProperties(typeClass);
        if (CollUtil.isEmpty(propertyMetas)) return null;

        return new MethodParameter(name, typeClass, propertyMetas, method, elementType);
    }


    @SuppressWarnings("DataFlowIssue")
    public String combineCodeFragment(MethodParameter methodParameter) {
        String newLineCode = null;
        String variableName = null;

        // 包含的方法
        PsiMethod containingMethod = methodParameter.getContainingMethod();
        // 当前位置表示的元素类型
        PsiElementType elementType = methodParameter.getElementType();

        // 若是引用类，则需要手动构建对象
        if (PsiElementType.REFERENCED_CLASS == elementType) {
            PsiClass typeClass = methodParameter.getTypeClass();
            String className = typeClass.getName();
            variableName = StrUtil.lowerFirst(className);

            // 若类名为小写开头，则变量名 + 1
            if (StrUtil.isLowerCase(className.substring(0, 1))) {
                // 防止变量名冲突
                variableName = suggestNonConflictingVariableName(variableName + 1, containingMethod);
            }

            newLineCode = StrUtil.format(CodeFragments.NEW_TEMPLATE, className, variableName, className);
        }

        if (StrUtil.isNotBlank(variableName)) {
            methodParameter.setVarName(variableName);
        }

        List<String> codeFragments = generateCodeFragment(methodParameter);

        if (StrUtil.isNotBlank(newLineCode)) {
            codeFragments.add(0, newLineCode);
        }

        return StrUtil.join("\n", codeFragments) + "\n";
    }


    /**
     * 提供一个方法作用域中不冲突的变量名
     *
     * @param fieldName        字段名
     * @param containingMethod 方法
     * @return 变量名
     */
    public String suggestNonConflictingVariableName(String fieldName, PsiMethod containingMethod) {
        // 避开方法内已存在的变量名
        Set<String> existingNames = JavaUtil.listVariables(containingMethod);

        if (!existingNames.contains(fieldName)) {
            // 原始名称无冲突，直接返回
            return fieldName;
        }

        String prefix;
        long number;
        // 判断变量名末尾是否为数字
        if (CodeWeaverUtil.endsWithDigitRegex(fieldName)) {
            String[] split = CodeWeaverUtil.splitEndNumber(fieldName);
            prefix = split[0];
            number = Long.parseLong(split[1]);
        } else {
            prefix = fieldName;
            number = 0;
        }

        // 循环生成新名称，直到不冲突
        String candidate;
        do {
            number++;
            candidate = prefix + number;
        } while (existingNames.contains(candidate));

        return candidate;
    }


    /**
     * 组合代码行列表
     *
     * @return 代码行列表
     */
    protected abstract List<String> generateCodeFragment(MethodParameter methodParameter);

}
