package pl.pas.`as`.rest.config

import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans
import pl.pas.`as`.core.services.AuthServiceImpl
import pl.pas.`as`.core.services.UserServiceImpl
import pl.pas.`as`.infrastructure.adapters.UserRepositoryAdapter
import pl.pas.`as`.infrastructure.repositories.hibernate.UserRepositoryHibernate
import pl.pas.`as`.rest.config.mock.JerseyDomainServiceCallerMock
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
    bean<JerseyDomainServiceCallerMock>()
    bean<AuthServiceImpl>()
    bean<UserServiceImpl>()
    bean<UserDetailsServiceImpl>()
    bean<UserRepositoryAdapter>()
    bean<UserRepositoryHibernate>()

}
