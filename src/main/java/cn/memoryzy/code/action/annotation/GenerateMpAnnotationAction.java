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
public class GenerateMpAnnotationAction extends DumbAwareAction implements UpdateInBackground {

    public GenerateMpAnnotationAction() {
        super(CodeWeaverBundle.message("action.anno.mp.text"),
                CodeWeaverBundle.messageOnSystem("action.anno.mp.description"),
                null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
