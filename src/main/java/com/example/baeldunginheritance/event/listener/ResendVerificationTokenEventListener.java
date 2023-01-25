package com.example.baeldunginheritance.event.listener;


import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.collection.VerificationToken;
import com.example.baeldunginheritance.event.ResendVerificationTokenEvent;
import com.example.baeldunginheritance.repository.VerificationTokenRepository;
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
public class ResendVerificationTokenEventListener implements ApplicationListener<ResendVerificationTokenEvent> {


    @Autowired
    private UserService userService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(ResendVerificationTokenEvent event) {
        User user=event.getUser();
        VerificationToken verificationToken=verificationTokenRepository.findByUser(user);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(verificationToken);
        String url=event.getApplicationUrl()+"verifyResend?token="+verificationToken.getToken();
        log.info("Click to verify your account: {}",url);
        try {
            sendMail(user,url);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendMail(User user,String url) throws MessagingException {
        MimeMessage message=javaMailSender.createMimeMessage();

        String text="<h1>Here's the requested token! \n</h1>" +
                "<p>Hello, dear "+user.getFirstName()+" "+user.getLastName()+"\n</p> " +
                "<p>This is your new activation link:\n</p> " +
                url+" \n" +
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
