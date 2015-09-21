package be.kdg.repaircafe.frontend.config;

import be.kdg.repaircafe.frontend.controllers.resources.formatters.DateFormatter;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

/**
 * Handles configuration of all presentation layer oriented components and helper classes such
 * as formatters and validators, internationalization (i18n), view resolvers, static resources (CSS, HTML, js, images).
 */
@Configuration
@EnableWebMvc
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@ComponentScan(basePackages = "be.kdg.repaircafe.frontend",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "be.kdg.repaircafe.frontend.config.security.rest.*")}
)
@Import(RestConfig.class)
public class WebContextConfig extends WebMvcConfigurerAdapter
{
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/web-context-config/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/springs-integratie-met-webserver/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/validation-en-formatting/
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/testing/

    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        registry.addFormatter(new DateFormatter());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters)
    {
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        super.configureMessageConverters(messageConverters);
    }

    @Override
    public Validator getValidator()
    {
        // Create global validator bean for Hibernate Validation (JSR-303)
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        // pass MessageSource Bean so error messages have i18n support
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource()
    {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:bundles/messages");
        return messageSource;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        registry.jsp("/WEB-INF/pages/", ".jsp")
                .viewClass(JstlView.class);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/style/**")
                .addResourceLocations("/style/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("/images/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/js/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
    {
        configurer.enable();
    }

}
