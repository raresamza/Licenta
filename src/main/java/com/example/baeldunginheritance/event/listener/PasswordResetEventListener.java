package com.example.baeldunginheritance.event.listener;

import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.event.PasswordRestEvent;
import com.example.baeldunginheritance.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class PasswordResetEventListener implements ApplicationListener<PasswordRestEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(PasswordRestEvent event) {
        User user=event.getUser();
        if (user!=null) {
            String token= UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user,token);
            String url=event.getApplicationUrl()+"savePassword?token="+token;
            log.info("Click to reset your password: {}",url);
            try {
                sendMail(user,url);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new UsernameNotFoundException("The user for which a password reset has been requested does not exist.");
        }
    }



    private void sendMail(User user,String url) throws MessagingException {
        MimeMessage message=javaMailSender.createMimeMessage();

        String text="<h1>Here's the requested token for resetting the password! \n</h1>" +
                "<p>Hello, dear "+user.getFirstName()+" "+user.getLastName()+"\n</p> " +
                "<p>This is your new activation link:\n</p> " +
                url+" \n" +
                "your password will bereset to <span style=\"color:red; font-weight: 700;\">123</span> upon clicking the link"+
                "<p>Please click the link to activate the account in the span of <span style=\"color:red;\">10</span>" +
                " minutes otherwise the link will be invalidated and a resubmission must be issued \n</p> " +
                "<p style style=\"color:red;\"> In case you did not request this e-mail please ignore it! \n</p>" +
                "<h2 style='color:#C4AE78;'>Thanks for using our services!";


        MimeMessageHelper messageHelper=new MimeMessageHelper(message,true);

        messageHelper.setFrom("raresamza@gmail.com");
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject("Token resent for activating the Leetik account");
        messageHelper.setText(text,true);
        javaMailSender.send(message);
    }
}
