package cn.memoryzy.code.action.method;

import cn.memoryzy.code.action.GenerateMethodCodeAbstractAction;
import cn.memoryzy.code.bundle.CodeWeaverBundle;
import cn.memoryzy.code.model.MethodParameter;
import cn.memoryzy.code.util.PlatformUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * @author Memory
 * @since 2025/4/24
 */
public class GenerateSetterWithDefaultValueAction extends GenerateMethodCodeAbstractAction {

    public GenerateSetterWithDefaultValueAction() {
        super(CodeWeaverBundle.message("action.listSetter.defaults.text"), CodeWeaverBundle.messageOnSystem("action.listSetter.defaults.description"));
    }

    @Override
    protected List<String> generateCodeFragment(MethodParameter methodParameter) {
        return GenerateSetterAction.generateCodeFragment(methodParameter, true);
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public void update(@NotNull AnActionEvent e) {
        boolean enabled = false;
        DataContext dataContext = e.getDataContext();
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        PsiFile psiFile = PlatformUtil.getPsiFile(dataContext);
        if (null != project && null != psiFile) {
            PsiElement element = PlatformUtil.getPsiElementByOffset(dataContext);
            MethodParameter methodParameter = resolveMethodParameter(element);
            if (null != methodParameter) {
                // 不能不存在 Setter
                enabled = methodParameter.getPropertyMetas().stream().anyMatch(el -> Objects.nonNull(el.getSetterMethod()));
            }
        }

        e.getPresentation().setEnabled(enabled);
    }
}
