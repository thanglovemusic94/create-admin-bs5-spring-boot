package com.example.createadminbs5springboot.configs;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Collections;

@Configuration
public class ThymeleafConfig {

    private static final String TEMPLATE_ENCODING = "UTF-8";
    private static final String TEMPLATE_PREFIX = "/templates/";
    private static final String TEMPLATE_SUFFIX = ".html";
    private static final String TEMPLATE_SUFFIX_TEXT = ".txt";
    private static final Boolean TEMPLATE_CACHEABLE = false;
    private static final Integer HTML_ORDER = 1;
    private static final Integer TEXT_ORDER = 2;
    private static final String TEMPLATE_PREFIX_EMAIL = "/email/";

    @Bean
    public Context getContext() {
        return new Context();
    }

    @Bean
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateEngine.addTemplateResolver(htmlTemplateResolver());
        templateEngine.addTemplateResolver(textTemplateResolver());

//        templateEngine.addDialect(new LayoutDialect(new GroupingStrategy()));
        templateEngine.setTemplateEngineMessageSource(getMessageResource());
        return templateEngine;
    }

    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }

    public ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(HTML_ORDER);
        templateResolver.setPrefix(TEMPLATE_PREFIX);
        templateResolver.setSuffix(TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(TEMPLATE_CACHEABLE);
        templateResolver.setCharacterEncoding(TEMPLATE_ENCODING);
        return templateResolver;
    }

    private ITemplateResolver textTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(TEXT_ORDER);
        templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
        templateResolver.setPrefix(TEMPLATE_PREFIX_EMAIL);
        templateResolver.setSuffix(TEMPLATE_SUFFIX_TEXT);
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding(TEMPLATE_ENCODING);
        templateResolver.setCacheable(TEMPLATE_CACHEABLE);
        return templateResolver;
    }

}
