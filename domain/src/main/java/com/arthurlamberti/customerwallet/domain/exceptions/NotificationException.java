package com.arthurlamberti.customerwallet.domain.exceptions;


import com.arthurlamberti.customerwallet.domain.validation.handler.Notification;

public class NotificationException extends DomainException {
    public NotificationException(String message, final Notification notification) {
        super(message, notification.getErrors());
    }
}
