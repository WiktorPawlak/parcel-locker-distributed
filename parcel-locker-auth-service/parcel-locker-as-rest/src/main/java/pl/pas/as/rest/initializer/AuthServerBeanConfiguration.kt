package pl.pas.`as`.rest.initializer

import jakarta.ws.rs.client.Client
import org.glassfish.jersey.client.JerseyClientBuilder
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans
import pl.pas.`as`.core.services.AuthServiceImpl
import pl.pas.`as`.core.services.UserServiceImpl
import pl.pas.`as`.infrastructure.adapters.JerseyDomainServiceCaller
import pl.pas.`as`.infrastructure.adapters.UserRepositoryAdapter
import pl.pas.`as`.infrastructure.repositories.hibernate.UserRepositoryHibernate
import pl.pas.`as`.rest.controllers.ClientController
import pl.pas.`as`.rest.security.UserDetailsServiceImpl

fun getBeanDefinitions() = beans {
    registerControllers()
    registerServices()
}

private fun BeanDefinitionDsl.registerControllers() {
    bean<ClientController>()
}

private fun BeanDefinitionDsl.registerServices() {
    bean<JerseyDomainServiceCaller>()
    bean<Client> {
        JerseyClientBuilder.createClient()
    }
    bean<AuthServiceImpl>()
    bean<UserServiceImpl>()
    bean<UserDetailsServiceImpl>()
    bean<UserRepositoryAdapter>()
    bean<UserRepositoryHibernate>()
}
