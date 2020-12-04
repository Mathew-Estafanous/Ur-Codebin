package com.urcodebin.helpers;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationUtil {

    public static void showNotification(String message, NotificationVariant variant) {
        Notification notification = Notification.show(message);
        notification.addThemeVariants(variant);
    }

    public static void showNotification(String message) {
        showNotification(message, NotificationVariant.LUMO_PRIMARY);
    }
}
