package pl.pas.domain.rest.initializer

import jakarta.ws.rs.client.Client
import org.glassfish.jersey.client.JerseyClientBuilder
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans
import pl.pas.domain.core.service.DeliveryServiceImpl
import pl.pas.domain.core.service.LockerServiceImpl
import pl.pas.domain.core.service.UserServiceImpl
import pl.pas.domain.infrastructure.adapters.DeliveryRepositoryAdapter
import pl.pas.domain.infrastructure.adapters.JerseyServiceCaller
import pl.pas.domain.infrastructure.adapters.LockerRepositoryAdapter
import pl.pas.domain.infrastructure.adapters.UserRepositoryAdapter
import pl.pas.domain.infrastructure.repositories.hibernate.DeliveryRepositoryHibernate
import pl.pas.domain.infrastructure.repositories.hibernate.LockerRepositoryHibernate
import pl.pas.domain.infrastructure.repositories.hibernate.UserRepositoryHibernate
import pl.pas.domain.rest.controllers.ClientController
import pl.pas.domain.rest.controllers.DeliveryController
import pl.pas.domain.rest.controllers.LockerController

fun getBeanDefinitions() = beans {
    registerControllers()
    registerServices()
    registerRepositories()
}

private fun BeanDefinitionDsl.registerControllers() {
    bean<ClientController>()
    bean<DeliveryController>()
    bean<LockerController>()
}

private fun BeanDefinitionDsl.registerServices() {
    bean<JerseyServiceCaller>()
    bean<Client> {
        JerseyClientBuilder.createClient()
    }

    bean<UserServiceImpl>()
    bean<DeliveryServiceImpl>()
    bean<LockerServiceImpl>()
}

private fun BeanDefinitionDsl.registerRepositories() {
    bean<UserRepositoryAdapter>()
    bean<DeliveryRepositoryAdapter>()
    bean<LockerRepositoryAdapter>()
    bean<UserRepositoryHibernate>()
    bean<LockerRepositoryHibernate>()
    bean<DeliveryRepositoryHibernate>()
}
