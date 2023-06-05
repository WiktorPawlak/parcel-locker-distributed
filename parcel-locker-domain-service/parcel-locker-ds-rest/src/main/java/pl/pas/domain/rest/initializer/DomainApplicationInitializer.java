package pl.pas.domain.rest.initializer;

import static pl.pas.domain.rest.initializer.DomainBeanConfigurationKt.getBeanDefinitions;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;


public class DomainApplicationInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(final GenericApplicationContext applicationContext) {
        getBeanDefinitions().initialize(applicationContext);
    }
}
