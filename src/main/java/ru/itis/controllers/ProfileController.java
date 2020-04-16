package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.UserDto;
import ru.itis.models.AppUser;
import ru.itis.services.UserService;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/profile")
    public ModelAndView getProfilePage() {
        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        AppUser user = userService.findUserByLogin(name);

        UserDto userDto = UserDto.from(user);

        modelAndView.addObject("user", userDto);
        modelAndView.setViewName("profile");
        return modelAndView;
    }
}
