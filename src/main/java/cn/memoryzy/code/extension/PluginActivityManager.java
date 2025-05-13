package cn.memoryzy.code.extension;

import cn.memoryzy.code.CodeWeaverPlugin;
import com.intellij.ide.plugins.DynamicPluginListener;
import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

/**
 * @author Memory
 * @since 2025/4/21
 */
public class PluginActivityManager implements StartupActivity, DynamicPluginListener {
    @Override
    public void runActivity(@NotNull Project project) {
        // 获取版本
        String currentVersion = CodeWeaverPlugin.getVersion();
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        String lastVersion = propertiesComponent.getValue(CodeWeaverPlugin.PLUGIN_VERSION);

        if (lastVersion == null) {
            // TODO 判断是否支持 jcef，支持就写个网页展示，不支持就用通知

            // TODO Notifications.showWelcomeNotification(project);
            propertiesComponent.setValue(CodeWeaverPlugin.PLUGIN_VERSION, currentVersion);
        } else {
            // 是否版本更高
            if (CodeWeaverPlugin.isNewerVersion(lastVersion, currentVersion)) {
                // TODO Notifications.showUpdateNotification(project);
                propertiesComponent.setValue(CodeWeaverPlugin.PLUGIN_VERSION, currentVersion);
            }
        }
    }

    @Override
    public void beforePluginUnload(@NotNull IdeaPluginDescriptor pluginDescriptor, boolean isUpdate) {

    }
}
