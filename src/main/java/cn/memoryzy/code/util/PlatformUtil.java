package cn.memoryzy.code.util;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.registry.Registry;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Memory
 * @since 2025/4/21
 */
public class PlatformUtil {

    private static final Logger LOG = Logger.getInstance(PlatformUtil.class);


    public static boolean isChineseLocale() {
        Locale locale = Locale.getDefault();
        return Locale.CHINESE.getLanguage().equals(locale.getLanguage())
                && (Objects.equals(locale.getCountry(), "") || Objects.equals(locale.getCountry(), "CN"));
    }

    public static boolean isNewUi() {
        int baselineVersion = ApplicationInfo.getInstance().getBuild().getBaselineVersion();
        boolean isBeNewUi = true;
        try {
            isBeNewUi = Registry.is("ide.experimental.ui", true);
        } catch (Exception e) {
            LOG.warn(e);
        }

        return baselineVersion >= 222 && isBeNewUi;
    }

}
