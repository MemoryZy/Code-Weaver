package cn.memoryzy.code.constant;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;

/**
 * @author Memory
 * @since 2025/4/25
 */
public interface ActionHolder {

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

    AnAction OBSERVE_PROPERTIES_MATCH_ACTION = ActionManager.getInstance().getAction(OBSERVE_PROPERTIES_MATCH_ACTION_ID);
    AnAction GENERATE_PROPERTIES_MAPPING_ACTION = ActionManager.getInstance().getAction(GENERATE_PROPERTIES_MAPPING_ACTION_ID);
    AnAction GENERATE_METHOD_REFERENCE_GROUP = ActionManager.getInstance().getAction(GENERATE_METHOD_REFERENCE_GROUP_ID);
    AnAction GENERATE_ANNOTATION_GROUP = ActionManager.getInstance().getAction(GENERATE_ANNOTATION_GROUP_ID);
    AnAction GENERATE_GETTER_ACTION = ActionManager.getInstance().getAction(GENERATE_GETTER_ACTION_ID);
    AnAction GENERATE_SETTER_ACTION = ActionManager.getInstance().getAction(GENERATE_SETTER_ACTION_ID);
    AnAction GENERATE_SETTER_WITH_DEFAULT_VALUE_ACTION = ActionManager.getInstance().getAction(GENERATE_SETTER_WITH_DEFAULT_VALUE_ACTION_ID);
    AnAction GENERATE_JSON_ANNOTATION_ACTION = ActionManager.getInstance().getAction(GENERATE_JSON_ANNOTATION_ACTION_ID);
    AnAction GENERATE_MP_ANNOTATION_ACTION = ActionManager.getInstance().getAction(GENERATE_MP_ANNOTATION_ACTION_ID);
    AnAction GENERATE_SWAGGER_ANNOTATION_ACTION = ActionManager.getInstance().getAction(GENERATE_SWAGGER_ANNOTATION_ACTION_ID);

}
