package ru.itis.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignInController {
    @PreAuthorize("permitAll()")
    @GetMapping(value = "/signIn")
    public ModelAndView getSignInPage(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView();
        if (authentication == null) {

            modelAndView.setViewName("sign_in");
        }else {
            modelAndView.setViewName("redirect:/profile");
        }
        return modelAndView;
    }

}
