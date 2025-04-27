package cn.memoryzy.code.action.method;

import cn.memoryzy.code.bundle.CodeWeaverBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.UpdateInBackground;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Memory
 * @since 2025/4/24
 */
public class GenerateSetterAction extends DumbAwareAction implements UpdateInBackground {

    public GenerateSetterAction() {
        super(CodeWeaverBundle.message("action.listSetter.noArgs.text"),
                CodeWeaverBundle.messageOnSystem("action.listSetter.noArgs.description"),
                null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
