package net.thiki.rest.sample.config

import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.CustomScopeConfigurer
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.context.support.SimpleThreadScope
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*

/**
 */
@SpringBootConfiguration
@Configuration
open class ApplicationContextConfig : ApplicationContextAware {


    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        //		((AnnotationConfigEmbeddedWebApplicationContext) applicationContext).scan("net.thiki");

    }

    /**
     * add thread scope to support multi data sources in none-request-scope
     * scenario.
     *
     * @return
     */
    @Bean
    open fun customScopeConfigurer(): CustomScopeConfigurer {
        val bean = CustomScopeConfigurer()
        val scopes = HashMap<String, Any>()
        scopes["thread"] = SimpleThreadScope()
        bean.setScopes(scopes)
        return bean
    }


    @Bean
    open fun mvcConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization",
                                "productId", "serverId", "adminUserId")
                        .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS").allowCredentials(true).maxAge(3600)
            }
        }
    }


    @Bean
    open fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasename("classpath:i18n/messages")
        messageSource.setDefaultEncoding("utf-8")
        return messageSource
    }

    @Bean
    open fun validator(): LocalValidatorFactoryBean {
        val factory = LocalValidatorFactoryBean()
        factory.setValidationMessageSource(messageSource())
        return factory
    }


}
