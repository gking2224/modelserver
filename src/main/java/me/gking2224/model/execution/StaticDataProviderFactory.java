package me.gking2224.model.execution;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class StaticDataProviderFactory implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;
    private Collection<StaticDataProvider> staticDataProviders;

//    public StaticDataProvider get(String type) {
//        return staticDataProviders.get(type).get(0);
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.staticDataProviders = applicationContext.getBeansOfType(StaticDataProvider.class).values();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    public Set<StaticDataProvider> getStaticDataProviders(Set<String> natures) {
        return staticDataProviders.stream().filter(s->natures.contains(s.getIdentifier())).collect(Collectors.toSet());
    }
}
