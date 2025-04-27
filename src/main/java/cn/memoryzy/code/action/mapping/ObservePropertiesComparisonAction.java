package cn.memoryzy.code.action.mapping;

import cn.memoryzy.code.bundle.CodeWeaverBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.UpdateInBackground;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Memory
 * @since 2025/4/25
 */
public class ObservePropertiesComparisonAction extends DumbAwareAction implements UpdateInBackground {

    public ObservePropertiesComparisonAction() {
        super(CodeWeaverBundle.message("action.observePropertiesMatch.text"),
                CodeWeaverBundle.messageOnSystem("action.observePropertiesMatch.description"),
                // TODO 图标用表格类型的
                null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
