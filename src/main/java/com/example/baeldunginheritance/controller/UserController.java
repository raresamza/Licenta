package com.example.baeldunginheritance.controller;

import com.example.baeldunginheritance.DTO.*;
import com.example.baeldunginheritance.collection.Course;
import com.example.baeldunginheritance.collection.Mapper;
import com.example.baeldunginheritance.collection.User;
import com.example.baeldunginheritance.collection.VerificationToken;
import com.example.baeldunginheritance.event.PasswordRestEvent;
import com.example.baeldunginheritance.event.RegistrationCompleteEvent;
import com.example.baeldunginheritance.event.ResendVerificationTokenEvent;
import com.example.baeldunginheritance.service.UserService;
import com.example.baeldunginheritance.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
//@CrossOrigin(origins = {"https://sssssss.herokuapp.com",
//                        "http://localhost:3000/",
//                        "https://sssssss.herokuapp.com/",
//                        "http://localhost:3000/sign-up/teacher",
//                        "https://sssssss.herokuapp.com/sign-up/teacher"})

@CrossOrigin(origins = {"https://sssssss.herokuapp.com",
        "https://frotnend.vercel.app/",
        "http://localhost:3000/",
        "http://localhost:3000/courses-tab",
        "http://localhost:3000/courses-tab/",
        "http://localhost:3000/courses-tab/*",
        "http://localhost:3000/*",
        "http://localhost:3000/user-profile",
        "http://localhost:3000/user-profile/*",
        "https://frotnend.vercel.app/user-profile",
        "http://localhost:3000/change-email",
        "https://frotnend.vercel.app/change-email",
        "http://localhost:3000/sign-up/teacher",
        "https://frotnend.vercel.app/sign-up/teacher",
        "http://localhost:3000/sign-up/teacherP",
        "https://frotnend.vercel.app/sign-up/teacherP",
        "https://frotnend.vercel.app/sign-up/studentP",
        "http://localhost:3000/sign-up/studentP"})
@RequestMapping("/user")
public class UserController {

    private final Mapper mapper;

    private final VerificationTokenService verificationTokenService;
    private final UserService userService;

    private final ApplicationEventPublisher publisher;

    public UserController(Mapper mapper, VerificationTokenService verificationTokenService, UserService userService, ApplicationEventPublisher publisher) {
        this.mapper = mapper;
        this.verificationTokenService = verificationTokenService;
        this.userService = userService;
        this.publisher = publisher;
    }

    @GetMapping
    @ResponseBody
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/student")
    @ResponseBody
    public List<User> getStudents() {
        return userService.getStudents();
    }

    @PostMapping("/register/student")
    @ResponseBody
    public User saveStudent(@RequestBody UserCreationDTO userCreationDTO, final HttpServletRequest httpServletRequest) {
        User student = mapper.toStudentUser(userCreationDTO);
        publisher.publishEvent(new RegistrationCompleteEvent(
                student,
                applicationUrl(httpServletRequest)
        ));
        return userService.saveStudent(userCreationDTO);
    }

    @PostMapping("/register/studentP")
    @ResponseBody
    public User saveStudentPhoto(@RequestBody UserCreationDTO userCreationDTO, final HttpServletRequest httpServletRequest) {
        User student = mapper.toStudentUserPhoto(userCreationDTO);
        publisher.publishEvent(new RegistrationCompleteEvent(
                student,
                applicationUrl(httpServletRequest)
        ));
        System.out.println(student.getPhotoURL());
        return userService.saveStudentPhoto(userCreationDTO);
    }

    @PostMapping("/register/teacher")
    @ResponseBody
    public User saveTeacher(@RequestBody UserCreationDTO userCreationDTO, final HttpServletRequest httpServletRequest) {
        User teacher = mapper.toTeacherUser(userCreationDTO);
        publisher.publishEvent(new RegistrationCompleteEvent(
                teacher,
                applicationUrl(httpServletRequest)
        ));
        return userService.saveTeacher(userCreationDTO);
    }

    @PostMapping("/register/teacherP")
    @ResponseBody
    public User saveTeacherPhoto(@RequestBody UserCreationDTO userCreationDTO, final HttpServletRequest httpServletRequest) {
        User teacher = mapper.toTeacherUserPhoto(userCreationDTO);
        System.out.println(teacher.getPhotoURL());
        publisher.publishEvent(new RegistrationCompleteEvent(
                teacher,
                applicationUrl(httpServletRequest)
        ));
        System.out.println(teacher.getPhotoURL());
        return userService.saveTeacherPhoto(userCreationDTO);
    }

    private String applicationUrl(HttpServletRequest httpServletRequest) {
        return "http://" + httpServletRequest.getServerName() + ":"
                + httpServletRequest.getServerPort() + "/user/" + httpServletRequest.getContextPath();
    }

    @GetMapping("/active/{email}")
    public boolean isUserActive(@PathVariable String email) {
        return userService.isUserActive(email);
    }

    @GetMapping("/quiz-code/{email}")
    public String getQuizCodeFromUser(@PathVariable String email) {
return userService.getQuizCodeFromUser(email);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }


    //    @PutMapping("/updateEmail/{email}")
//    @ResponseBody
//    public User updateEmail(@PathVariable String email,@RequestBody String newEmail) {
//        System.out.println(email+" EMAIL");
//        System.out.println(newEmail+" NEW EMAIL");
//        return  userService.updateEmail(email,newEmail);
//    }
//    @PatchMapping("/update")
//    public void updateBio(String email,@RequestBody bio) {
//        userService.updateBio(email);
//    }
    @PutMapping("/updateEmail")
    @ResponseBody
    public User updateEmail(@RequestBody UpdateEmailDTO newEmail) {
        return userService.updateEmail(newEmail);
    }

    //    @PatchMapping("/update")
//    public void updateBio(String email,@RequestBody bio) {
//        userService.updateBio(email);
//    }
//refa functia cu update DTO care contine doar request body si are ca obiect inauntru mail vechi si mailu nou.
    @GetMapping("/{email}")
    public User getByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }


    @CrossOrigin
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        System.out.println("result is: " + result);
        if (result.toLowerCase().contains("valid")) {
            return "User verified successfully";
        } else {
            return "Bad user or token has expired (check if 10+ minutes have passed since you received th email)";
        }
    }

    @PutMapping("/add/course")
    @ResponseBody
    public User addCourseToUser(@RequestBody AddUserToCourseDTO addUserToCourseDTO) {
        return userService.addCourseToUser(addUserToCourseDTO);
    }

    @GetMapping("/resendToken")
    public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest httpServletRequest) {
//        String result = userService.validateVerificationToken(oldToken);
        VerificationToken verificationToken = verificationTokenService.findByToken(oldToken);
        User user = verificationToken.getUser();
        publisher.publishEvent(new ResendVerificationTokenEvent(
                user,
                applicationUrl(httpServletRequest)
        ));
        return "Mail re-sent";
    }


    @GetMapping("/verifyResend")
    public String verifyResentToken(@RequestParam("token") String token) {
        String result = userService.validateVerificationToken(token);
        System.out.println("result is: " + result);
        if (result.toLowerCase().contains("valid")) {
            return "User verified successfully with the resent token";
        } else {
            return "Bad user or token has expired (check if 10+ minutes have passed since you received th email)";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest httpServletRequest) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        publisher.publishEvent(new PasswordRestEvent(
                user,
                applicationUrl(httpServletRequest)
        ));
        return "Mail sent for password reset!";
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
        String result = userService.validatePasswordResetToken(token);
        if (!result.toLowerCase().contains("valid")) {
            return "Bad user or token has expired (check if 10+ minutes have passed since you received th email)";
        }
        Optional<User> user = userService.getUserByPasswordResetToken(token);
        if (user.isPresent()) {
            userService.changePassword(user.get(), passwordModel.getNewPassword());
            return "Password reset Successfuly";
        } else {
            return "Bad user";
        }
    }

    @PutMapping("/add/bio")
    public String addBio(@RequestBody AddBioDTO addBioDTO )  {
        return userService.addBio(addBioDTO);
    }


    @PostMapping("/changePassword")
    public String changePassword(@RequestBody PasswordModel passwordModel) {
        User user = userService.findUserByEmail(passwordModel.getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordModel.getOldPassword())) {
            return "Invalid old password";
        }

        userService.changePassword(user, passwordModel.getNewPassword());
        return "Password changed successfully";
    }


    @DeleteMapping
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    @DeleteMapping("/token")
    public void deleteAllTokens() {
        verificationTokenService.deleteAllTokens();
    }



    @GetMapping("/photourl/{email}")
    public String getPhotoURLByEmail(@PathVariable String email) {
        return userService.getPhotoURLByEmail(email);
    }
}
