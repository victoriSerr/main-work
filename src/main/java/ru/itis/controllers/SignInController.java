package ru.itis.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.services.SignInService;

@Controller
public class SignInController {

    @Autowired
    private SignInService signInService;

//    @PreAuthorize("permitAll()")
    @GetMapping(value = "/signIn")
    public ModelAndView getSignInPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sign_in");
        return modelAndView;
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView signIn(HttpServletRequest request) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("sign_in");
//        String login = request.getParameter("login");
//        String password = request.getParameter("password");
//
//        SignInDto signInDto = SignInDto.builder().login(login).password(password).build();
//
////        User user = signInService.signIn(signInDto);
//        User user = signInService.signIn(signInDto);
//        if (user != null) {
//            System.out.println(user);
//            request.getSession().setAttribute("email", user.getEmail());
//        }
//
////        System.out.println(request.getSession().getAttribute("email"));
//        return modelAndView;
//
//    }

}
