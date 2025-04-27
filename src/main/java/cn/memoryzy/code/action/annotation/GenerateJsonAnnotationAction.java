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
public class GenerateJsonAnnotationAction extends DumbAwareAction implements UpdateInBackground {

    public GenerateJsonAnnotationAction() {
        super(CodeWeaverBundle.message("action.anno.json.text"),
                CodeWeaverBundle.messageOnSystem("action.anno.json.description"),
                null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // TODO 提供 ui 界面，在界面中选择注解


    }
}
