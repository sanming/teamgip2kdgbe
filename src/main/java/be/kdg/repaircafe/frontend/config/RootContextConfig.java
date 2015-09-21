package be.kdg.repaircafe.frontend.config;

import be.kdg.repaircafe.backend.config.BackendContextConfig;
import be.kdg.repaircafe.frontend.config.security.MultiHttpSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {BackendContextConfig.class, MultiHttpSecurityConfig.class})
@ComponentScan(basePackages = "be.kdg.repaircafe.frontend.config.security.rest",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)}
)
public class RootContextConfig
{
    // just import other context configurations.
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/root-context-config/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/springs-integratie-met-webserver/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/testing/

}
