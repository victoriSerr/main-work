package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.OrganisationDto;
import ru.itis.models.AppUser;
import ru.itis.models.Organisation;
import ru.itis.models.Post;
import ru.itis.services.OrganisationService;
import ru.itis.services.PostService;
import ru.itis.services.UserService;

import javax.servlet.http.HttpServletRequest;
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

        modelAndView.addObject("organisations", list);
        modelAndView.setViewName("organisations");

        return modelAndView;
    }

//    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping(value = "/organisations/add")
    public ModelAndView getFormPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("add-organisation");
        return modelAndView;
    }

    @PostMapping(value = "/organisations/add")
    public String addOrganisation(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String moderatorName = auth.getName();

        AppUser user = userService.findUserByLogin(moderatorName);


//        modelAndView.setViewName("organisations");

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("addr");
        String description = request.getParameter("description");

        OrganisationDto organisationDto = OrganisationDto.builder()
                .name(name)
                .phoneNumber(phone)
                .address(address)
                .description(description)
                .moderator(user)
                .build();
        organisationService.save(organisationDto);

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
//    @GetMapping(value = "/organisations/{orgId:.+}")
//    public String getOrganisationPage(@PathVariable("orgId") Long id) {
//        return "organisation";
//    }
}
