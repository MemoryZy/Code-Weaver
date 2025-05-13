package cn.memoryzy.code.constant;

/**
 * @author Memory
 * @since 2025/5/8
 */
public interface PluginIds {

    interface ToolWindow {
        String PROPERTIES_COMPARISON_TOOL_WINDOW_ID = "CodeWeaver.ToolWindow.PropertiesComparison";
    }

    interface Action {
        String OBSERVE_PROPERTIES_MATCH_ACTION_ID = "CodeWeaver.Action.ObservePropertiesComparisonAction";
        String GENERATE_PROPERTIES_MAPPING_ACTION_ID = "CodeWeaver.Action.GeneratePropertiesMappingAction";
        String GENERATE_METHOD_REFERENCE_GROUP_ID = "CodeWeaver.Group.GenerateMethodReferenceGroup";
        String GENERATE_ANNOTATION_GROUP_ID = "CodeWeaver.Group.GenerateAnnotationGroup";
        String GENERATE_GETTER_ACTION_ID = "CodeWeaver.Action.GenerateGetterAction";
        String GENERATE_SETTER_ACTION_ID = "CodeWeaver.Action.GenerateSetterAction";
        String GENERATE_SETTER_WITH_DEFAULT_VALUE_ACTION_ID = "CodeWeaver.Action.GenerateSetterWithDefaultValueAction";
        String GENERATE_JSON_ANNOTATION_ACTION_ID = "CodeWeaver.Action.GenerateJsonAnnotationAction";
        String GENERATE_MP_ANNOTATION_ACTION_ID = "CodeWeaver.Action.GenerateMpAnnotationAction";
        String GENERATE_SWAGGER_ANNOTATION_ACTION_ID = "CodeWeaver.Action.GenerateSwaggerAnnotationAction";
    }



}
