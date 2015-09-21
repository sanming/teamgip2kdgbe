package be.kdg.repaircafe.frontend.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Implementation of Spring's ServletInitializer class so application can start with start of webserver
 */
public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    // https://programmeren3-repaircafe.rhcloud.com/spring-concepten/spring-mvc/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/springs-integratie-met-webserver/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/testing/

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class<?>[]{RootContextConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class<?>[]{WebContextConfig.class};
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/"};
    }
}
