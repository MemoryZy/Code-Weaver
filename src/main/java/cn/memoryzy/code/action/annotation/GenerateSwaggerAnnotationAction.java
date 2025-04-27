package cn.memoryzy.code.action.annotation;

import cn.memoryzy.code.bundle.CodeWeaverBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.UpdateInBackground;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Memory
 * @since 2025/4/27
 */
public class GenerateSwaggerAnnotationAction extends DumbAwareAction implements UpdateInBackground {

    public GenerateSwaggerAnnotationAction() {
        super(CodeWeaverBundle.message("action.anno.swagger.text"),
                CodeWeaverBundle.messageOnSystem("action.anno.swagger.description"),
                null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // TODO 提供一个选项，支持从注释中导入过来
    }
}
