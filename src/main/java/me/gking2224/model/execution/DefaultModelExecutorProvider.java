package me.gking2224.model.execution;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import me.gking2224.model.jpa.Model;
import me.gking2224.model.service.ModelExecutionRequest;

@Component
public class DefaultModelExecutorProvider implements ModelExecutorProvider, ApplicationContextAware, InitializingBean {


    private ApplicationContext applicationContext;
    private Collection<ModelExecutor> modelExecutors;

    @Override
    public ModelExecutor getExecutor(Model model, ModelExecutionRequest request) {
        Optional<ModelExecutor> executor = findMatchingExecutor(model, request);
        return executor.orElseThrow(()->new ModelExecutionException(200, "No matching executor"));
    }

    private Optional<ModelExecutor> findMatchingExecutor(Model model, ModelExecutionRequest request) {
        Optional<ModelExecutor> rv = modelExecutors.stream()
                .filter(e->executorSupports(e, model))
                .max((ModelExecutor e1, ModelExecutor e2)->new ModelNaturePreferenceComparator(request).compare(e1, e2));
        
        return rv;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.modelExecutors = applicationContext.getBeansOfType(ModelExecutor.class).values();
    }
    
    protected boolean executorSupports(ModelExecutor e, Model model) {
        return model.getNatures().stream().anyMatch(n->e.canExecuteNature(n));
    }
    
    public class ModelNaturePreferenceComparator implements Comparator<ModelExecutor> {

        private ModelExecutionRequest request;

        public ModelNaturePreferenceComparator(ModelExecutionRequest request) {
            this.request = request;
        }

        @Override
        public int compare(ModelExecutor o1, ModelExecutor o2) {
            return 0;
        }

    }

}
