package com.sluv.backoffice.global.common.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sluv.backoffice.domain.user.entity.User;
import com.sluv.backoffice.domain.user.exception.UserFCMTokenNotFoundException;
import com.sluv.backoffice.domain.user.exception.UserNotFoundException;
import com.sluv.backoffice.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    public void sendFCMNotification(Long userId, String title, String body) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getFcmToken() == null) {
            throw new UserFCMTokenNotFoundException();
        }

        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(user.getFcmToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}