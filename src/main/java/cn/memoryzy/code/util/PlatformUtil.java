package cn.memoryzy.code.util;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.editor.ReadOnlyFragmentModificationException;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

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

    /**
     * 获取结构化文件
     *
     * @param dataContext 数据上下文
     * @return 结构化文件
     */
    public static PsiFile getPsiFile(DataContext dataContext) {
        try {
            return dataContext.getData(CommonDataKeys.PSI_FILE);
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 获取编辑器
     *
     * @param dataContext 数据上下文
     * @return 编辑器
     */
    public static Editor getEditor(DataContext dataContext) {
        return dataContext.getData(CommonDataKeys.EDITOR);
    }

    /**
     * 通过当前光标的偏移量获取当前所在的Psi元素
     * <p>亦可配合 PsiTreeUtil.getParentOfType(element, PsiClass.class)方法来获取该PsiElement所处的区域</p>
     *
     * @return Psi元素
     */
    public static PsiElement getPsiElementByOffset(DataContext dataContext) {
        PsiFile psiFile = getPsiFile(dataContext);
        Editor editor = getEditor(dataContext);
        return (psiFile != null && editor != null) ? getPsiElementByOffset(editor, psiFile) : null;
    }

    /**
     * 通过当前光标的偏移量获取当前所在的Psi元素
     * <p>亦可配合 PsiTreeUtil.getParentOfType(element, PsiClass.class)方法来获取该PsiElement所处的区域</p>
     *
     * @param editor  编辑器
     * @param psiFile Psi文件
     * @return Psi元素
     */
    public static PsiElement getPsiElementByOffset(Editor editor, PsiFile psiFile) {
        return psiFile.findElementAt(editor.getCaretModel().getOffset());
    }

    /**
     * 在对应光标位置插入指定文本
     *
     * @param editor                 编辑器
     * @param text                   文本
     * @param toProcessOverwriteMode 是否覆盖选中的文本
     */
    public static void insertStringAtCaret(@NotNull Editor editor, @NotNull String text, boolean toProcessOverwriteMode) {
        Document document = editor.getDocument();
        document.startGuardedBlockChecking();

        try {
            EditorModificationUtil.insertStringAtCaret(editor, text, toProcessOverwriteMode, true);
        } catch (ReadOnlyFragmentModificationException ex) {
            EditorActionManager.getInstance().getReadonlyFragmentModificationHandler(document).handle(ex);
        } finally {
            document.stopGuardedBlockChecking();
        }
    }

}
