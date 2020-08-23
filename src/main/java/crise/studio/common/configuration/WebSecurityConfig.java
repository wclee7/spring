package crise.studio.common.configuration;

import crise.studio.common.auth.AjaxSessionTimeoutFilter;
import crise.studio.common.auth.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/**/*.jsp", "/api/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new AjaxSessionTimeoutFilter(), ExceptionTranslationFilter.class).authorizeRequests()
                .antMatchers("/bnd/**").authenticated()
                .and().formLogin().loginPage("/bnd/login").loginProcessingUrl("/login").defaultSuccessUrl("/bnd/welcome").failureUrl("/bnd/login?error=true").usernameParameter("account").passwordParameter("password").permitAll()
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/bnd/login").deleteCookies("JSESSIONID").invalidateHttpSession(true).permitAll()
                .and().exceptionHandling().accessDeniedPage("/bnd/notAllow");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
