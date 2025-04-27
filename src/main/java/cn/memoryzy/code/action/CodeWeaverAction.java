package cn.memoryzy.code.action;

import cn.memoryzy.code.action.group.CodeWeaverGroup;
import cn.memoryzy.code.util.JavaUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.UpdateInBackground;
import com.intellij.openapi.project.DumbAwareAction;
import org.jetbrains.annotations.NotNull;

/**
 * @author Memory
 * @since 2025/4/23
 */
public class CodeWeaverAction extends DumbAwareAction implements UpdateInBackground {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        new CodeWeaverGroup(true).showPopupMenu(e.getDataContext());
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        presentation.setVisible(false);
        presentation.setEnabled(JavaUtil.isJavaFile(e.getDataContext()));
    }
}
