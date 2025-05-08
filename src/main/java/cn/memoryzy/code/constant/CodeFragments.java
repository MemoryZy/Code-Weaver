package cn.memoryzy.code.constant;

/**
 * @author Memory
 * @since 2025/4/28
 */
public interface CodeFragments {

    /**
     * 类型-变量名-参数名-方法名
     */
    String GETTER_TEMPLATE = "{} {} = {}.{}();";

    /**
     * 参数名-方法名-默认值(如有)
     */
    String SETTER_TEMPLATE = "{}.{}({});";

    /**
     * 类型-变量名-类型
     */
    String NEW_TEMPLATE = "{} {} = new {}();";

}
