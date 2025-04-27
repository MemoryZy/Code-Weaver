package cn.memoryzy.code.action.group;

import cn.memoryzy.code.bundle.CodeWeaverBundle;
import cn.memoryzy.code.constant.ActionHolder;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.UpdateInBackground;
import com.intellij.openapi.project.DumbAware;
import icons.CodeWeaverIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Memory
 * @since 2025/4/25
 */
public class GenerateAnnotationGroup extends DefaultActionGroup implements DumbAware, UpdateInBackground {

    public GenerateAnnotationGroup() {
        super(CodeWeaverBundle.message("action.generateAnnotation.text"),
                CodeWeaverBundle.messageOnSystem("action.generateAnnotation.description"),
                CodeWeaverIcons.at);
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();
        actions.add(ActionHolder.GENERATE_JSON_ANNOTATION_ACTION);
        actions.add(ActionHolder.GENERATE_MP_ANNOTATION_ACTION);
        actions.add(ActionHolder.GENERATE_SWAGGER_ANNOTATION_ACTION);

        return actions.toArray(new AnAction[0]);
    }
}
