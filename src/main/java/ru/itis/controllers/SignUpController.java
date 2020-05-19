package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.SignUpDto;
import ru.itis.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

//    @PreAuthorize("permitAll()")
    @GetMapping(value = "/registration")
    public ModelAndView getSignUpPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign_up");
        return modelAndView;
    }

//    @PreAuthorize("permitAll()")
    @PostMapping(value = "/registration")
    public ModelAndView signUp(HttpServletRequest req, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        SignUpDto signUpDto = SignUpDto.builder().email(email).login(login).password(password).build();

        userService.signUp(signUpDto);

        modelAndView.setViewName("redirect:/signIn");
        return modelAndView;
    }
}
