package cn.memoryzy.code.model;

import cn.memoryzy.code.enums.PsiElementType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;

/**
 * @author Memory
 * @since 2025/5/7
 */
public class PsiElementContext {

    /**
     * PSI元素类型（参数/变量等）
     */
    private final PsiElementType elementType;

    /**
     * 该元素所属的类
     */
    private final PsiClass psiClass;

    /**
     * 所属类的类型
     */
    private final PsiType psiType;

    public PsiElementContext(PsiElementType elementType, PsiClass psiClass, PsiType psiType) {
        this.elementType = elementType;
        this.psiClass = psiClass;
        this.psiType = psiType;
    }

    public PsiElementType getElementType() {
        return elementType;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public PsiType getPsiType() {
        return psiType;
    }
}
