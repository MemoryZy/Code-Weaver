package cn.memoryzy.code.toolwindow;

import cn.memoryzy.code.constant.PluginIds;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author Memory
 * @since 2025/5/8
 */
public class PropertiesComparisonToolWindowManager {

    private final Project project;
    private final ToolWindow toolWindow;

    public PropertiesComparisonToolWindowManager(Project project) {
        this.project = project;
        this.toolWindow = registerToolWindow(project);
    }

    public static PropertiesComparisonToolWindowManager getInstance(@NotNull Project project) {
        return project.getService(PropertiesComparisonToolWindowManager.class);
    }


    @SuppressWarnings("deprecation")
    private ToolWindow registerToolWindow(Project project) {
        ToolWindowManager windowManager = ToolWindowManager.getInstance(project);
        ToolWindow toolWindow = windowManager.getToolWindow(PluginIds.ToolWindow.PROPERTIES_COMPARISON_TOOL_WINDOW_ID);
        if (toolWindow == null) {
            // 这里的parentDisposable无效
            toolWindow = windowManager.registerToolWindow(
                    PluginIds.ToolWindow.PROPERTIES_COMPARISON_TOOL_WINDOW_ID,
                    true,
                    ToolWindowAnchor.RIGHT,
                    () -> {},
                    true,
                    true);
        }

        // 折叠组件
        // CollapsiblePanel

        // String title = JsonAssistantBundle.message("toolwindow.auxiliary.tree.name");
        // toolWindow.setTitle(title);
        // toolWindow.setStripeTitle(title);
        // toolWindow.setIcon(JsonAssistantIcons.ToolWindow.STRUCTURE_LOGO);
        //
        // registerAction((ToolWindowEx) toolWindow);
        return toolWindow;
    }


}
