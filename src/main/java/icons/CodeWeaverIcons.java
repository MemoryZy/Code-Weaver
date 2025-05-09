package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Memory
 * @since 2025/4/16
 */
public interface CodeWeaverIcons {

    Icon logo = load("/icons/logo.svg");

    Icon at = load("/icons/at.svg");

    Icon fold_collapse = load("/icons/fold_collapse.svg");

    Icon fold_expand = load("/icons/fold_expand.svg");


    static Icon load(String iconPath) {
        return IconLoader.getIcon(iconPath, CodeWeaverIcons.class);
    }

}
