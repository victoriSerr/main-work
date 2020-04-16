package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.models.AppUser;
import ru.itis.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ConfirmationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/confirmation/{link:.+}", method = RequestMethod.GET)
    public ModelAndView confirm(HttpServletRequest request, @PathVariable String link) {
        ModelAndView modelAndView = new ModelAndView();
        String link1 = String.valueOf(request.getRequestURL());
        System.out.println(link1);


        AppUser appUser = userService.findUserByLink(link1);
        if (appUser != null) {
            if (appUser.getIsConfirmed()) {
                modelAndView.addObject("message", "you already confirm your account");
            } else {
                appUser.setIsConfirmed(true);
                userService.update(appUser);
                modelAndView.addObject("message", "thank you for confirming registration");
            }
        } else {
            modelAndView.addObject("message", "404");
        }

        modelAndView.setViewName("confirm");
        return modelAndView;

    }
 }
