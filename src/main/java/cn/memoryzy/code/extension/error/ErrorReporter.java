package cn.memoryzy.code.extension.error;

import cn.hutool.core.util.StrUtil;
import cn.memoryzy.code.CodeWeaverPlugin;
import cn.memoryzy.code.bundle.CodeWeaverBundle;
import cn.memoryzy.code.constant.Urls;
import cn.memoryzy.code.util.CodeWeaverUtil;
import cn.memoryzy.code.util.PlatformUtil;
import com.intellij.ide.BrowserUtil;
import com.intellij.ide.IdeBundle;
import com.intellij.openapi.application.ApplicationNamesInfo;
import com.intellij.openapi.application.ex.ApplicationInfoEx;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Memory
 * @since 2025/4/16
 */
public class ErrorReporter extends ErrorReportSubmitter {

    private static final String GITHUB_ISSUE_BUG_LABEL = "bug";
    private static final String GITHUB_ISSUE_BUG_TEMPLATE = "bug_report.yml";
    private static final String GITHUB_ISSUE_BUG_CN_TEMPLATE = "bug_report_zh.yml";
    private static final int stacktraceLen = 6500;

    private static final String MESSAGE_QUALIFIED_NAME = "com.intellij.diagnostic.AbstractMessage";

    @Override
    public @NlsActions.ActionText @NotNull String getReportActionText() {
        return CodeWeaverBundle.messageOnSystem("report.to.vendor");
    }

    /**
     * 执行提交逻辑
     *
     * @return true，报告程序可用；false，报告程序不可用
     */
    @Override
    public boolean submit(IdeaLoggingEvent @NotNull [] events, @Nullable String additionalInfo, @NotNull Component parentComponent, @NotNull Consumer<? super SubmittedReportInfo> consumer) {
        IdeaLoggingEvent event = ArrayUtil.getFirstElement(events);
        if (event == null) {
            return false;
        }

        String title = getTitle(event.getData());
        Map<String, String> parameters = getParameterMap(event, title, additionalInfo);
        String url = com.intellij.util.Urls.newFromEncoded(Urls.GITHUB_ISSUE).addParameters(parameters).toExternalForm();

        BrowserUtil.browse(url);
        consumer.consume(new SubmittedReportInfo(null, "GitHub issue", SubmittedReportInfo.SubmissionStatus.NEW_ISSUE));

        return true;
    }


    private static String getTitle(@Nullable Object messageData) {
        String errorMessage = null;
        if (CodeWeaverUtil.isInheritedFrom(messageData, MESSAGE_QUALIFIED_NAME)) {
            Object throwable = CodeWeaverUtil.invokeMethod(messageData, "getThrowable");
            if (throwable != null) {
                errorMessage = throwable.toString();
            }
        }

        return StrUtil.isBlank(errorMessage) ? "" : "Exception: " + errorMessage;
    }


    private static @NotNull Map<String, String> getParameterMap(IdeaLoggingEvent event, String title, @Nullable String additionalInfo) {
        String version = CodeWeaverPlugin.getVersion();
        String runtimeEnv = getRuntimeEnv();
        String stacktrace = StringUtil.first(event.getThrowableText(), stacktraceLen, true);

        Map<String, String> parameters = new LinkedHashMap<>();
        parameters.put("labels", GITHUB_ISSUE_BUG_LABEL);
        parameters.put("template", PlatformUtil.isChineseLocale() ? GITHUB_ISSUE_BUG_CN_TEMPLATE : GITHUB_ISSUE_BUG_TEMPLATE);
        parameters.put("title", title);
        parameters.put("version", version);
        parameters.put("runtime_env", runtimeEnv);
        parameters.put("stacktrace", stacktrace);

        if (StrUtil.isNotBlank(additionalInfo)) {
            parameters.put("description", additionalInfo);
        }

        return parameters;
    }


    private static String getRuntimeEnv() {
        ApplicationInfoEx appInfo = ApplicationInfoEx.getInstanceEx();
        String appName = appInfo.getFullApplicationName(); // NON-NLS
        String edition = ApplicationNamesInfo.getInstance().getEditionName();
        if (edition != null) appName += " (" + edition + ")";
        String buildInfo = IdeBundle.message("about.box.build.number", appInfo.getBuild().asString());

        Properties properties = System.getProperties();
        String javaVersion = properties.getProperty("java.runtime.version", properties.getProperty("java.version", "unknown"));
        String arch = properties.getProperty("os.arch", "");
        String runtimeInfo = MessageFormat.format("Runtime version: {0} {1}", javaVersion, arch);

        String vmVersion = properties.getProperty("java.vm.name", "unknown");
        String vmVendor = properties.getProperty("java.vendor", "unknown");
        String vmInfo = MessageFormat.format("VM: {0} by {1}", vmVersion, vmVendor);

        String osName = SystemInfo.OS_NAME;

        java.util.List<String> infoList = java.util.List.of(appName, buildInfo, runtimeInfo, vmInfo, osName);
        return StrUtil.join("\n", infoList);
    }

}
