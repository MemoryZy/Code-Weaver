package cn.memoryzy.code.action.method;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.memoryzy.code.action.GenerateMethodCodeAbstractAction;
import cn.memoryzy.code.bundle.CodeWeaverBundle;
import cn.memoryzy.code.constant.CodeFragments;
import cn.memoryzy.code.model.JavaPropertyMeta;
import cn.memoryzy.code.model.MethodParameter;
import cn.memoryzy.code.util.JavaUtil;
import cn.memoryzy.code.util.PlatformUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Memory
 * @since 2025/4/24
 */
public class GenerateSetterAction extends GenerateMethodCodeAbstractAction {

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    public GenerateSetterAction() {
        super(CodeWeaverBundle.message("action.listSetter.noArgs.text"), CodeWeaverBundle.messageOnSystem("action.listSetter.noArgs.description"));
    }

    @Override
    protected List<String> generateCodeFragment(MethodParameter methodParameter) {
        return generateCodeFragment(methodParameter, false);
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public void update(@NotNull AnActionEvent e) {
        boolean enabled = false;
        DataContext dataContext = e.getDataContext();
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        PsiFile psiFile = PlatformUtil.getPsiFile(dataContext);
        if (null != project && null != psiFile) {
            PsiElement element = PlatformUtil.getPsiElementByOffset(dataContext);
            MethodParameter methodParameter = resolveMethodParameter(element);
            if (null != methodParameter) {
                // 不能不存在 Setter
                enabled = methodParameter.getPropertyMetas().stream().anyMatch(el -> Objects.nonNull(el.getSetterMethod()));
            }
        }

        e.getPresentation().setEnabled(enabled);
    }

    public static List<String> generateCodeFragment(MethodParameter methodParameter, boolean needDefault) {
        List<String> codeFragments = new ArrayList<>();
        String varName = methodParameter.getVarName();

        for (JavaPropertyMeta propertyMeta : methodParameter.getPropertyMetas()) {
            PsiType type = propertyMeta.getField().getType();
            PsiMethod setter = propertyMeta.getSetterMethod();
            if (Objects.isNull(setter)) {
                continue;
            }

            String methodName = setter.getName();
            String defaultValue = needDefault ? getCodeDefaultValue(type.getCanonicalText()) : "";
            codeFragments.add(StrUtil.format(CodeFragments.SETTER_TEMPLATE, varName, methodName, defaultValue));
        }

        return codeFragments;
    }

    public static String getCodeDefaultValue(String typeName) {
        // 先去除泛型标签
        typeName = JavaUtil.removeGenericTags(typeName);

        switch (typeName) {
            // 基本类型及其包装类
            case "boolean":
            case "java.lang.Boolean":
                return String.valueOf(random.nextBoolean());
            case "byte":
            case "java.lang.Byte":
                return "(byte) " + random.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE + 1);
            case "short":
            case "java.lang.Short":
                return "(short) " + random.nextInt(Short.MIN_VALUE, Short.MAX_VALUE + 1);
            case "int":
            case "java.lang.Integer":
                return String.valueOf(random.nextInt());
            case "long":
            case "java.lang.Long":
                return random.nextLong() + "L";
            case "float":
            case "java.lang.Float":
                return random.nextFloat() + "F";
            case "double":
            case "java.lang.Double":
                return random.nextDouble() + "D";
            case "char":
            case "java.lang.Character":
                return "'" + (char) (random.nextInt(26) + 97) + "'";

            // 字符串和大数字类型
            case "String":
            case "java.lang.String":
                return StrUtil.wrap(IdUtil.simpleUUID().substring(0, 8), "\"");
            case "java.math.BigDecimal":
                return StrUtil.format("new BigDecimal(\"{}\")", random.nextDouble());
            case "java.math.BigInteger":
                return StrUtil.format("new BigInteger(\"{}\")", random.nextInt(1000));

            // 日期时间类型
            case "java.util.Date":
                return StrUtil.format("new Date(System.currentTimeMillis() - {}L)", random.nextLong(1000000));
            case "java.time.LocalDate":
                return StrUtil.format("LocalDate.now().minusDays({})", random.nextInt(365));
            case "java.time.LocalTime":
                return StrUtil.format("LocalTime.now().minusMinutes({})", random.nextInt(1440));
            case "java.time.LocalDateTime":
                return StrUtil.format("LocalDateTime.now().minusHours({})", random.nextInt(24));
            case "java.time.ZonedDateTime":
                return "ZonedDateTime.now().withZoneSameInstant(ZoneId.of(\"UTC\"))";
            case "java.time.Instant":
                return StrUtil.format("Instant.now().minusSeconds({})", random.nextInt(3600));
            case "java.sql.Date":
                return StrUtil.format("new java.sql.Date(System.currentTimeMillis() - {}L)", random.nextLong(1000000));
            case "java.sql.Time":
                return StrUtil.format("new java.sql.Time(System.currentTimeMillis() - {}L)", random.nextLong(1000000));
            case "java.sql.Timestamp":
                return StrUtil.format("new Timestamp(System.currentTimeMillis() - {}L)", random.nextLong(1000000));

            // 集合类型
            case "java.util.Collection":
            case "java.util.List":
            case "java.util.ArrayList":
                return "new ArrayList<>()";
            case "java.util.LinkedList":
            case "java.util.Queue":
                return "new LinkedList<>()";
            case "java.util.Set":
            case "java.util.HashSet":
                return "new HashSet<>()";
            case "java.util.LinkedHashSet":
                return "new LinkedHashSet<>()";
            case "java.util.Map":
            case "java.util.HashMap":
                return "new HashMap<>()";
            case "java.util.LinkedHashMap":
                return "new LinkedHashMap<>()";
            case "java.util.concurrent.ConcurrentHashMap":
                return "new ConcurrentHashMap<>()";
            case "java.util.Deque":
                return "new ArrayDeque<>()";

            // 数组和特殊类型
            case "byte[]":
                return "new byte[] { " +
                        String.join(", ",
                                String.valueOf(random.nextInt(127)),
                                String.valueOf(random.nextInt(127)),
                                String.valueOf(random.nextInt(127))) + " }";
            case "java.util.UUID":
                return "UUID.randomUUID()";
            case "java.util.concurrent.atomic.AtomicInteger":
                return StrUtil.format("new AtomicInteger({})", random.nextInt());

            // 默认情况
            default:
                return "null";
        }
    }

}
