package com.example.baeldunginheritance.event;


import com.example.baeldunginheritance.collection.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ResendVerificationTokenEvent extends ApplicationEvent {

    private User user;
    private String applicationUrl;

    public ResendVerificationTokenEvent(User user,String applicationUrl) {
        super(user);
        this.user=user;
        this.applicationUrl=applicationUrl;
    }
}
