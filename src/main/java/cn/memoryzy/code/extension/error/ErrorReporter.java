package cn.memoryzy.code.extension.error;

import cn.memoryzy.code.bundle.CodeWeaverBundle;
import com.intellij.openapi.diagnostic.ErrorReportSubmitter;
import com.intellij.openapi.util.NlsActions;
import org.jetbrains.annotations.NotNull;

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



}
