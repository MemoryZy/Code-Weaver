package cn.memoryzy.code.constant;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;

/**
 * @author Memory
 * @since 2025/4/25
 */
public interface ActionHolder {

    AnAction OBSERVE_PROPERTIES_MATCH_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.OBSERVE_PROPERTIES_MATCH_ACTION_ID);
    AnAction GENERATE_PROPERTIES_MAPPING_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_PROPERTIES_MAPPING_ACTION_ID);
    AnAction GENERATE_METHOD_REFERENCE_GROUP = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_METHOD_REFERENCE_GROUP_ID);
    AnAction GENERATE_ANNOTATION_GROUP = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_ANNOTATION_GROUP_ID);
    AnAction GENERATE_GETTER_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_GETTER_ACTION_ID);
    AnAction GENERATE_SETTER_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_SETTER_ACTION_ID);
    AnAction GENERATE_SETTER_WITH_DEFAULT_VALUE_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_SETTER_WITH_DEFAULT_VALUE_ACTION_ID);
    AnAction GENERATE_JSON_ANNOTATION_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_JSON_ANNOTATION_ACTION_ID);
    AnAction GENERATE_MP_ANNOTATION_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_MP_ANNOTATION_ACTION_ID);
    AnAction GENERATE_SWAGGER_ANNOTATION_ACTION = ActionManager.getInstance().getAction(PluginIds.Action.GENERATE_SWAGGER_ANNOTATION_ACTION_ID);

}
