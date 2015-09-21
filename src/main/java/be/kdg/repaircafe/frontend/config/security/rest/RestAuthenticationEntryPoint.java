package be.kdg.repaircafe.frontend.config.security.rest;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class that handles improper request for resource that needs authentication
 * For a call to a regular webresouce a redirect to the login page would occur.
 * In the case of a Rest call an error message is sent back to indicate authentication is necessary.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/frontend-spring-security/

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException
    {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getOutputStream().println("{ \"error\": \"" + e.getMessage() + "\" }");
    }
}
