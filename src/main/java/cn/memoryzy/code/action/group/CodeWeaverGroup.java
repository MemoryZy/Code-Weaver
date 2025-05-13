package cn.memoryzy.code.action.group;

import cn.memoryzy.code.bundle.CodeWeaverBundle;
import cn.memoryzy.code.constant.ActionHolder;
import cn.memoryzy.code.util.JavaUtil;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import icons.CodeWeaverIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Memory
 * @since 2025/4/23
 */
public class CodeWeaverGroup extends DefaultActionGroup implements DumbAware, UpdateInBackground {

    private final boolean actionEventPopup;

    public CodeWeaverGroup() {
        super();
        setPopup(true);
        setEnabledInModalContext(true);
        Presentation presentation = getTemplatePresentation();
        presentation.setText("_Code Weaver");
        presentation.setDescription(CodeWeaverBundle.messageOnSystem("action.main.description"));
        presentation.setIcon(CodeWeaverIcons.logo);
        this.actionEventPopup = false;
    }

    public CodeWeaverGroup(boolean actionEventPopup) {
        this.actionEventPopup = actionEventPopup;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        showPopupMenu(e.getDataContext());
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(JavaUtil.isJavaFile(e.getDataContext()));
    }

    @Override
    public AnAction @NotNull [] getChildren(@Nullable AnActionEvent e) {
        List<AnAction> actions = new ArrayList<>();

        actions.add(ActionHolder.GENERATE_ANNOTATION_GROUP);
        actions.add(Separator.create());
        actions.add(ActionHolder.GENERATE_METHOD_REFERENCE_GROUP);
        actions.add(Separator.create());
        actions.add(ActionHolder.OBSERVE_PROPERTIES_MATCH_ACTION);
        actions.add(Separator.create());
        actions.add(ActionHolder.GENERATE_PROPERTIES_MAPPING_ACTION);

        return actions.toArray(new AnAction[0]);
    }

    public void showPopupMenu(DataContext dataContext) {
        ListPopup popup = JBPopupFactory.getInstance()
                .createActionGroupPopup(CodeWeaverBundle.message("popup.menu.title"),
                        this, dataContext, JBPopupFactory.ActionSelectionAid.ALPHA_NUMBERING, true);
        popup.showInBestPositionFor(dataContext);
    }

}
