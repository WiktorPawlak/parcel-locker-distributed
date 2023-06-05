package pl.pas.as.infrastructure.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
@EnableJpaRepositories(basePackages = "pl.pas.as.infrastructure.repositories.hibernate")
public class PersistenceUtil {
    public static String UNIT_NAME = "parcel-locker-as-unit";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        em.setPersistenceUnitName(UNIT_NAME);
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        return em;
    }
    //raczej niepotrzebne, bo w EntityMangerUtil entityManager instancjonowany jest ręcznie
    //zostawiam jako przykład

}
