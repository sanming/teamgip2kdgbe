package be.kdg.repaircafe.frontend.config.security;

import be.kdg.repaircafe.backend.services.api.UserService;
import be.kdg.repaircafe.frontend.config.security.rest.RestAuthenticationEntryPoint;
import be.kdg.repaircafe.frontend.config.security.rest.RestAuthenticationFailureHandler;
import be.kdg.repaircafe.frontend.config.security.rest.RestAuthenticationSuccessHandler;
import be.kdg.repaircafe.frontend.config.security.rest.RestLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configures web security and rest api security aspects.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MultiHttpSecurityConfig
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/frontend-spring-security/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/root-context-config/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/springs-integratie-met-webserver/

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.
                userDetailsService(userService).
                passwordEncoder(passwordEncoder);
    }

    @Configuration
    @Order(2)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter
    {
        @Autowired
        private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

        @Autowired
        private RestAuthenticationSuccessHandler restAuthenticationSuccesHandler;

        @Autowired
        private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

        @Autowired
        private RestLogoutSuccessHandler restLogoutSuccessHandler;

        protected void configure(HttpSecurity http) throws Exception
        {
            // If you want basic HTTP authentication use this
            // example usage with curl:
            // curl -i --user scott.tiger@live.com:scott http://localhost:8080/repaircafe/api/
            /*
                http
                    .antMatcher("/api*//**")
         .authorizeRequests()
         .anyRequest().authenticated()
         .and()
         .httpBasic();
         */

            // If you want "normal" login on Rest API use this
            // example usages with curl:
            // login: curl -i -X POST -d username=scott.tiger@live.com -d password=scott -c ~/cookies.txt http://localhost:8080/repaircafe/api
            // access resources: curl -i -X GET -b ~/cookies.txt http://localhost:8080/repaircafe/api/
            // logout: curl -i -X GET -b ~/cookies.txt http://localhost:8080/repaircafe/api/logout

            http.authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/logout").authenticated()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .successHandler(restAuthenticationSuccesHandler).loginProcessingUrl("/api/login")
                    .failureHandler(restAuthenticationFailureHandler)
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(restLogoutSuccessHandler)
                    .and()
                    .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and()
                    .csrf().disable();
        }
    }

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter
    {
        @Override
        protected void configure(HttpSecurity http) throws Exception
        {
            http.requestMatcher(request -> {
                final String url = request.getServletPath() + request.getPathInfo();
                return !(url.startsWith("/api/"));
            });

            http.authorizeRequests().antMatchers("/resources/**", "/signup.jsp", "/logout").permitAll()
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/repairer/**").access("hasRole('ROLE_REPAIRER')")
                    .antMatchers("/client/**").access("hasRole('ROLE_CLIENT')")
                    .anyRequest().authenticated()      // remaining URL's require authentication
                    .and()
                    .formLogin()
                    .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .and()
                    .csrf();
        }
    }
}