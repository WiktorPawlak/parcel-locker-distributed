package pl.pas.as.rest.config;

import static pl.pas.as.rest.config.AuthServerBeanTestConfigurationKt.getBeanDefinitions;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

public class AuthServerTestApplicationInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(final GenericApplicationContext applicationContext) {
        getBeanDefinitions().initialize(applicationContext);
    }
}
