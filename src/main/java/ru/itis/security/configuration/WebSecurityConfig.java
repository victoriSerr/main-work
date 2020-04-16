package ru.itis.security.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@ComponentScan(basePackages = "ru.itis")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(value = "customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier(value = "rememberMeKey")
    private String rememberMeKey;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable();

        http.authorizeRequests()
//                .antMatchers("/signIn").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/files").authenticated()
                .antMatchers("/").authenticated()
                .antMatchers("/chat/*").authenticated()
                .antMatchers("/profile").authenticated()
                .antMatchers("/organisations/add").hasAuthority("MODERATOR")
                .and()
                .formLogin()
                .loginPage("/signIn")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/profile", true)
                .failureUrl("/signIn?error")
                .permitAll()
                .and()
                .rememberMe()
                .key(rememberMeKey);

//
//        http.formLogin()
//                .loginPage("/signIn")
//                .usernameParameter("login")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/files")
//                .failureForwardUrl("/signIn?error")
//                .permitAll();
    }

//
//    @Override
//    public void configure(WebSecurity web){
//        web
//                .ignoring()
//                .antMatchers("/styles/**");
//    }
}
