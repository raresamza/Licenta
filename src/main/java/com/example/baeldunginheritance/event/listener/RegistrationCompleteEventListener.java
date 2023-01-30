package com.example.baeldunginheritance.event.listener;

import com.example.baeldunginheritance.collection.Student;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.event.RegistrationCompleteEvent;
import com.example.baeldunginheritance.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {


    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the Verification Token for the user with link
        User user=event.getUser();
        String token= UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(token,user);
        String url=event.getApplicationUrl()+"verifyRegistration?token="+token;
        log.info("Click to verify your account: {}",url);
        try {
            sendMail(user,url);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        //Send mail to user
    }



    private void sendMail(User user,String url) throws MessagingException {
        MimeMessage message=javaMailSender.createMimeMessage();

        String style="<style> .random {color:red}</style>";


        String text=style + "<h1>You're almost done! \n</h1>" +
                "<p class='random'>Hello, dear "+user.getFirstName()+" "+user.getLastName()+"\n</p> " +
                "<p>This is your activation link:\n</p> " +
                url+" \n" +
                "<p>Please click the link to activate the account in the span of <span style=\"color:red;\">10</span>" +
                " minutes otherwise the link will be invalidated and a resubmission must be issued</p>" +
                "<h2 style='color:#C4AE78;'>Thanks for using our services!";


        MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);

        messageHelper.setFrom("raresamza@gmail.com");
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject("Activation link for the Leetik account");
        messageHelper.setText(text,true);
        javaMailSender.send(message);
    }
}
