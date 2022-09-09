package kr.co.postbox.config.security

import kr.co.ats.camping.config.security.UserAuthenticationFilter
import kr.co.postbox.config.handler.PostBoxAccessDeniedHandler
import kr.co.postbox.config.jwt.JwtAuthenticationEntryPoint
import kr.co.postbox.config.jwt.JwtAuthenticationFilter
import kr.co.postbox.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

@Configurable
@EnableWebSecurity
class SecurityConfig(
    private val userService: UserService
) : WebSecurityConfigurerAdapter(){


    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


    @Autowired
    @Lazy
    lateinit var messageSource: MessageSource

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    @Lazy
    fun configureAuthentication(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder
            .userDetailsService(this.userService)
            .passwordEncoder(this.passwordEncoder())
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers("/swagger-ui/**","/swagger-resources/**","/v3/**","/h2-console/**")
    }

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/member/**").authenticated()
            .antMatchers("/api/login", "/api/sign/**","/imageView/**","/fileDownload/**").permitAll()
            .anyRequest().authenticated()
            .and().cors()
            .and().addFilter(JwtAuthenticationFilter(authenticationManagerBean(), userService))
            .addFilter(userAuthenticationFilter())
            .exceptionHandling()
                .authenticationEntryPoint(JwtAuthenticationEntryPoint(messageSource))
                .accessDeniedHandler(PostBoxAccessDeniedHandler(messageSource))
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    }

    /**
     * 인증 필터
     */
    @Bean
    fun userAuthenticationFilter(): UserAuthenticationFilter {
        val filter = UserAuthenticationFilter(authenticationManagerBean())
        filter.setAuthenticationSuccessHandler(userSuccessHandler())
        filter.setAuthenticationFailureHandler(userFailureHandler())
        return filter
    }

    fun userFailureHandler():AuthenticationFailureHandler{
        return UserAuthenticationFailureHandler(messageSource)
    }

    fun userSuccessHandler():AuthenticationSuccessHandler{
        return UserAuthenticationSuccessHandler()
    }
}
