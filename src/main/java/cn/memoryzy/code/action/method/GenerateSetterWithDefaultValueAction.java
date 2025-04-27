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
public class GenerateSetterWithDefaultValueAction extends DumbAwareAction implements UpdateInBackground {

    public GenerateSetterWithDefaultValueAction() {
        super(CodeWeaverBundle.message("action.listSetter.defaults.text"),
                CodeWeaverBundle.messageOnSystem("action.listSetter.defaults.description"),
                null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
