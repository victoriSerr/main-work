package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.OrganisationDto;
import ru.itis.models.AppUser;
import ru.itis.models.Organisation;
import ru.itis.models.Post;
import ru.itis.security.details.UserDetailsImpl;
import ru.itis.services.OrganisationService;
import ru.itis.services.PostService;
import ru.itis.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping(value = "/organisations")
    public ModelAndView getOrganisations() {
        List<Organisation> list = organisationService.findAll();
        ModelAndView modelAndView = new ModelAndView();

        System.out.println(list.get(0).getAnimalsInHome());
        System.out.println(list.get(0).getAnimalsNeedHome());
        modelAndView.addObject("organisations", list);
        modelAndView.setViewName("organisations");

        return modelAndView;
    }

//    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping(value = "/organisations/add")
    public ModelAndView getFormPage(@ModelAttribute(name = "form") OrganisationDto form) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-organisation");
        modelAndView.addObject("form", form);
        return modelAndView;
    }

    @PostMapping(value = "/organisations/add")
    public String addOrganisation(@Valid @ModelAttribute(name = "form") OrganisationDto form, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "add-organisation";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String moderatorName = auth.getName();
        AppUser user = userService.findUserByLogin(moderatorName);
        form.setModerator(user);
        organisationService.save(form);

        return "redirect:/organisations";
    }

    @GetMapping(value = "/organisations/{orgId:.+}")
    public ModelAndView getOrganisationPage(@PathVariable("orgId") Long id) {
        Organisation organisation = organisationService.find(id);
        List<Post> posts = postService.fingOrganisationPosts(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("o", organisation);
        modelAndView.addObject("user", name);
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName("organisation");
        return modelAndView;
    }
}
