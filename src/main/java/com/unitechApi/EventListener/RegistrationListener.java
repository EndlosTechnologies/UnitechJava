package com.unitechApi.EventListener;

import com.unitechApi.user.Repository.UserRepository;
import com.unitechApi.user.model.User;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@ToString
public class RegistrationListener implements ApplicationListener<OnRegisterEvent> {

    @Autowired
    private UserRepository userRepository;

    List<SseEmitter> emitters=new ArrayList<>();
    private boolean complete;


    @Override
    public void onApplicationEvent(OnRegisterEvent event) {
        System.out.println(event.getUser());
        String name = null;
        this.ConfirmRegistration(event, null);

    }

    private void ConfirmRegistration(OnRegisterEvent event,String name) {
        User user=event.getUser();
        List<SseEmitter> deadEmmitter=new ArrayList<>();
        emitters.forEach(emitter->{
            try {
                log.info("notification  {} for user {}",event,name);
                emitter.send(SseEmitter.event().name(name).data(user));
                System.out.println("enter ");
                for (SseEmitter user1:emitters)
                {
                    System.out.println(user1);
                }

            } catch (IOException e) {
                deadEmmitter.add(emitter);
            }
        });
        System.out.println("success");


    }



}
