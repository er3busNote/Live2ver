package com.clone.youtube.global.configs

import com.clone.youtube.global.configs.filter.JwtAccessDeniedHandler
import com.clone.youtube.global.configs.filter.JwtAuthenticationEntryPoint
import com.clone.youtube.global.configs.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig (
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(
        http: HttpSecurity,
        jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
        jwtAccessDeniedHandler: JwtAccessDeniedHandler
    ): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/auth/**").permitAll() // 로그인, 토큰 재발급은 허용
                    .anyRequest().authenticated() // 나머지는 인증 필요
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                it.accessDeniedHandler(jwtAccessDeniedHandler)
            }

        return http.build()
    }
}