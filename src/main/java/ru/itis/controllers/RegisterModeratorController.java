package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.models.AppUser;
import ru.itis.models.Role;
import ru.itis.services.UserService;

@Controller
public class RegisterModeratorController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/confirm/moder")
    public String confirmModerator(@RequestParam("id") Long id) {

        AppUser user = userService.findUser(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(auth.getAuthorities());

        userService.setModeratorRole(user);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());
        return "redirect:/organisations/add";
    }


}
