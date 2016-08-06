package me.gking2224.model.mvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import me.gking2224.model.ModelObjectConverters;
import me.gking2224.model.jpa.Version.StringToVersion;
import me.gking2224.model.mvc.ContextInitFinishListener;
import me.gking2224.model.mvc.LocaleInterceptor;
import me.gking2224.model.service.ModelExecutionRequest.StringToModelExecutionRequest;

@ComponentScan({"me.gking2224.model"})
@ImportResource("classpath:test-demo-webapp-config.xml")
@Configuration
@EnableWebMvc
public class WebAppTestConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    ModelObjectConverters modelObjectConverters;
    
    @Bean
    public ApplicationListener<ContextRefreshedEvent> contextRefreshed() {
        return new ContextInitFinishListener();
    }
    
    @Bean
    public FreeMarkerConfigurer getFreeMarkerConfig() {
        FreeMarkerConfigurer fmc = new FreeMarkerConfigurer();
        fmc.setTemplateLoaderPath("/WEB-INF/ftl");
        return fmc; 
    }
    
    @Bean
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfigFactory() {
        FreeMarkerConfigurationFactoryBean f = new FreeMarkerConfigurationFactoryBean();
        f.setTemplateLoaderPath("/WEB-INF/ftl");
        return f;
    }
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.freeMarker().prefix("").suffix(".ftl");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleInterceptor());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToVersion());
        registry.addConverter(new StringToModelExecutionRequest());
        modelObjectConverters.getConverters().stream()
            .forEach(b->registry.addConverter((Converter<?,?>)b));
    }
    
}
