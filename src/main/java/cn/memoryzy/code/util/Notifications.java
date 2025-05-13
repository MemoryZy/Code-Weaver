package cn.memoryzy.code.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.notification.impl.NotificationFullContent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Memory
 * @since 2025/4/16
 */
public class Notifications {

    /**
     * 按需获取服务实例
     *
     * @return 通知组管理
     */
    private static NotificationGroupManager getNotificationGroupManager() {
        return NotificationGroupManager.getInstance();
    }

    /**
     * 普通气泡通知
     */
    public static NotificationGroup getBalloonNotificationGroup() {
        return getNotificationGroupManager().getNotificationGroup("CodeWeaver Plugin");
    }

    /**
     * 普通气泡通知（记录性）
     */
    public static NotificationGroup getBalloonLogNotificationGroup() {
        return getNotificationGroupManager().getNotificationGroup("CodeWeaver Plugin Log");
    }

    /**
     * 粘性气泡通知
     */
    public static NotificationGroup getStickyNotificationGroup() {
        return getNotificationGroupManager().getNotificationGroup("CodeWeaver Plugin Sticky");
    }

    /**
     * 粘性气泡通知（记录性）
     */
    public static NotificationGroup getStickyLogNotificationGroup() {
        return getNotificationGroupManager().getNotificationGroup("CodeWeaver Plugin Sticky Log");
    }


    /**
     * 展示通知（瞬态通知）
     */
    public static void showNotification(String content, NotificationType notificationType, Project project) {
        // 使用通知组创建通知
        getBalloonNotificationGroup().createNotification(content, notificationType).notify(project);
    }

    /**
     * 展示通知（瞬态通知）
     */
    public static void showNotification(String title, String content, NotificationType notificationType, Project project) {
        // 使用通知组创建通知
        getBalloonNotificationGroup().createNotification(content, notificationType).setTitle(title).notify(project);
    }

    /**
     * 展示通知（瞬态通知）
     */
    public static void showNotification(String title, String content, NotificationType notificationType, List<? extends @NotNull AnAction> actions, Project project) {
        // 使用通知组创建通知
        Notification notification = getBalloonNotificationGroup().createNotification(content, notificationType).setTitle(title);
        actions.forEach(notification::addAction);
        notification.notify(project);
    }

    /**
     * 展示通知（通知记录在 Event Log 或 Notifications 中）
     */
    public static void showLogNotification(String content, NotificationType notificationType, Project project) {
        // 使用通知组创建通知
        getBalloonLogNotificationGroup().createNotification(content, notificationType).notify(project);
    }

    /**
     * 展示通知（通知记录在 Event Log 或 Notifications 中）
     */
    public static void showLogNotification(String title, String content, NotificationType notificationType, Project project) {
        // 使用通知组创建通知
        getBalloonLogNotificationGroup().createNotification(content, notificationType).setTitle(title).notify(project);
    }

    /**
     * 展示不被折叠的通知
     */
    public static void showFullNotification(String title, String content, NotificationType notificationType, Project project) {
        new FullContentNotification(getBalloonLogNotificationGroup().getDisplayId(), title, content, notificationType).notify(project);
    }

    /**
     * 展示不被折叠的通知
     */
    public static void showFullNotification(String title, String content, NotificationType notificationType, List<? extends @NotNull AnAction> actions, Project project) {
        FullContentNotification notification = new FullContentNotification(getBalloonLogNotificationGroup().getDisplayId(), title, content, notificationType);
        actions.forEach(notification::addAction);
        notification.notify(project);
    }

    /**
     * 展示不被折叠的通知
     */
    public static void showFullStickyNotification(String title, String content, NotificationType notificationType, Project project) {
        new FullContentNotification(getStickyLogNotificationGroup().getDisplayId(), title, content, notificationType).notify(project);
    }

    /**
     * 展示不被折叠的通知
     */
    public static void showFullStickyNotification(String title, String content, NotificationType notificationType,
                                                  List<? extends @NotNull AnAction> actions, Project project) {
        FullContentNotification notification = new FullContentNotification(getStickyLogNotificationGroup().getDisplayId(), title, content, notificationType);
        actions.forEach(notification::addAction);
        notification.notify(project);
    }


    @SuppressWarnings({"deprecation", "DuplicatedCode"})
    // public static void showWelcomeNotification(Project project) {
    //     Notification notification = Notifications.getBalloonLogNotificationGroup()
    //             .createNotification(JsonAssistantBundle.messageOnSystem("notification.welcome.content",
    //                             Urls.GITHUB_LINK,
    //                             UrlType.DONATE.getId()
    //                     ) + "<br/>",
    //                     NotificationType.INFORMATION)
    //             .setTitle(JsonAssistantBundle.messageOnSystem("notification.welcome.title", JsonAssistantPlugin.getVersion()))
    //             .setImportant(true)
    //             .setListener(new NotificationListenerImpl())
    //             .addAction(new QuickStartAction())
    //             .addAction(new DonateAction(JsonAssistantBundle.messageOnSystem("action.donate.welcome.text")));
    //
    //     IdeFrame window = (IdeFrame) NotificationsManagerImpl.findWindowForBalloon(project);
    //     if (window != null) {
    //         Balloon balloon = NotificationsManagerImpl.createBalloon(window,
    //                 notification,
    //                 false,
    //                 false,
    //                 BalloonLayoutData.fullContent(),
    //                 UIManager.getInstance());
    //
    //         JComponent component = window.getComponent();
    //         balloon.show(getUpperRightRelativePoint(component, (BalloonImpl) balloon), Balloon.Position.above);
    //     }
    // }


    public static class FullContentNotification extends Notification implements NotificationFullContent {
        public FullContentNotification(@NotNull @NonNls String groupId, @NotNull String title, @NotNull String content, @NotNull NotificationType type) {
            super(groupId, title, content, type);
        }
    }

}
