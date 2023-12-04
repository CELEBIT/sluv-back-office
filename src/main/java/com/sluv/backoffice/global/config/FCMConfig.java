package com.sluv.backoffice.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
public class FCMConfig {

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/celebit-sluv-firebase-adminsdk-4ycho-1b06070369.json");

        try (InputStream refreshToken = resource.getInputStream()) {
            FirebaseApp firebaseApp = findOrCreateFirebaseApp(refreshToken);
            return FirebaseMessaging.getInstance(firebaseApp);
        }
    }

    private FirebaseApp findOrCreateFirebaseApp(InputStream refreshToken) throws IOException {
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(refreshToken))
                .build();

        List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
        if (firebaseAppList != null) {
            return firebaseAppList.stream()
                    .filter(app -> app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
                    .findFirst()
                    .orElseGet(() -> FirebaseApp.initializeApp(options));
        } else {
            return FirebaseApp.initializeApp(options);
        }
    }
}
