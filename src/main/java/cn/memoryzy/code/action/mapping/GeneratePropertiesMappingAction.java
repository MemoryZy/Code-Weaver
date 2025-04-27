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
public class GeneratePropertiesMappingAction extends DumbAwareAction implements UpdateInBackground {
    public GeneratePropertiesMappingAction() {
        super(CodeWeaverBundle.message("action.generatePropertiesMapping.text"),
                CodeWeaverBundle.messageOnSystem("action.generatePropertiesMapping.description"),
                // TODO 对比/匹配 性质的图标
                null);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
