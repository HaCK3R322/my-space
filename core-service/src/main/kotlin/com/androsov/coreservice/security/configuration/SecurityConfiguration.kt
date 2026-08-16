package com.androsov.coreservice.security.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfiguration {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            // Разрешаем анонимный доступ ко всем запросам
            .authorizeHttpRequests { matcher ->
                matcher.anyRequest().permitAll()
            }
            // Отключаем CSRF, чтобы пропускать POST-запросы без токена
            .csrf { csrf ->
                csrf.disable()
            }

        return http.build()
    }
}
