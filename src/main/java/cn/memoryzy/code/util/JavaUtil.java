package cn.memoryzy.code.util;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

/**
 * @author Memory
 * @since 2025/4/27
 */
public class JavaUtil {

    /**
     * 是否处于Java文件中
     *
     * @param dataContext 数据上下文
     * @return true -> 处于；false -> 不处于
     */
    public static boolean isJavaFile(DataContext dataContext) {
        // 获取当前选中的 PsiClass
        PsiFile psiFile = dataContext.getData(CommonDataKeys.PSI_FILE);
        return psiFile instanceof PsiJavaFile;
    }

}
