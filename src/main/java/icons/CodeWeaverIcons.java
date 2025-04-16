package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Memory
 * @since 2025/4/16
 */
public interface CodeWeaverIcons {



    static Icon load(String iconPath) {
        return IconLoader.getIcon(iconPath, CodeWeaverIcons.class);
    }

}
