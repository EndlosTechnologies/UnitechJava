package com.unitechApi.user.service;

import com.unitechApi.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserNotificationService {
    List<SseEmitter> ListOfEmmitters=new ArrayList<>();

    public void AddEmmiiters(SseEmitter sseEmitter)
    {
        sseEmitter.onCompletion(()->ListOfEmmitters.add(sseEmitter));
        sseEmitter.onTimeout(()->ListOfEmmitters.remove(sseEmitter));
        ListOfEmmitters.add(sseEmitter);
    }
    public void pushNotification(String username, User user)
    {
        log.info("info  {} notification for user {} ",username,user);
        List<SseEmitter > dead=new ArrayList<>();
        Notification payload=Notification.builder().username(username).user(user).build();
        System.out.println(payload.toString());
        ListOfEmmitters.forEach(sseEmitter -> {
            try {
                log.info("inner in notification ");
                sseEmitter.send(SseEmitter.event().name(username).data(payload));
                log.info("Success notification ");
            } catch (IOException e) {
                dead.add(sseEmitter);
            }
        });
    ListOfEmmitters.removeAll(dead);
    }

}
