package be.kdg.repaircafe.frontend.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Activates Spring's Filter to enable Spring Security
 */
public class SpringSecurityFilterInitializer extends AbstractSecurityWebApplicationInitializer
{
    /*
    Equivalent from following old school XML - config fragment.

    <filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>org.springframework.web.filter.DelegatingFilterProxy
        </filter-class>
    </filter>

    <filter-mapping>
    <filter-name>springSecurityFilterChain</filter-name>
    <url-pattern>*//*</url-pattern>
    </filter-mapping>

    */
}
