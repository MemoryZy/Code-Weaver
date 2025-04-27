package cn.memoryzy.code.action.group;

import cn.memoryzy.code.bundle.CodeWeaverBundle;
import cn.memoryzy.code.constant.ActionHolder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.UpdateInBackground;
import com.intellij.openapi.project.DumbAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成方法引用，例如列出所有Getter、Setter
 *
 * @author Memory
 * @since 2025/4/24
 */
public class GenerateMethodReferenceGroup extends DefaultActionGroup implements DumbAware, UpdateInBackground {

    public GenerateMethodReferenceGroup() {
        super(CodeWeaverBundle.message("action.methodReference.text"),
                CodeWeaverBundle.messageOnSystem("action.methodReference.description"),
                null);
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        actions.add(ActionHolder.GENERATE_GETTER_ACTION);
        actions.add(ActionHolder.GENERATE_SETTER_ACTION);
        actions.add(ActionHolder.GENERATE_SETTER_WITH_DEFAULT_VALUE_ACTION);
        return actions.toArray(new AnAction[0]);
    }
}
