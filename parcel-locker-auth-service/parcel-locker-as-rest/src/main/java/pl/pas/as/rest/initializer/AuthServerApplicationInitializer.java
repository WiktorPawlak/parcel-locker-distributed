package pl.pas.as.rest.initializer;

import static pl.pas.as.rest.initializer.AuthServerBeanConfigurationKt.getBeanDefinitions;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

public class AuthServerApplicationInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(final GenericApplicationContext applicationContext) {
        getBeanDefinitions().initialize(applicationContext);
    }
}
