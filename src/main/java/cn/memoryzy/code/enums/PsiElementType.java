package cn.memoryzy.code.enums;

/**
 * @author Memory
 * @since 2025/5/7
 */
public enum PsiElementType {

    /**
     * 方法参数（如：public void update(UserDTO dto)）
     */
    PARAMETER,

    /**
     * 方法内的局部变量（如：UserDTO dto = new UserDTO()）
     */
    LOCAL_VARIABLE,

    /**
     * 被引用的外部类（如：userService.convert(UserDTO) 中的 UserDTO）
     */
    REFERENCED_CLASS

}
