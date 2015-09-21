package be.kdg.repaircafe.frontend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

/**
 * Extra Spring HATEOAS Configuration file to enable HATEOAS
 * to expose URI templates for RestControllers.
 */
@Configuration
public class RestConfig extends RepositoryRestMvcConfiguration
{
    @Override
    public RepositoryRestConfiguration config()
    {
        RepositoryRestConfiguration config = super.config();
        config.setBaseUri("/api");
        return config;
    }
}