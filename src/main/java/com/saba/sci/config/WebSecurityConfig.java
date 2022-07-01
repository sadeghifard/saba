package com.saba.sci.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.saba.sci.security.AbstractBaseConstants;
import com.saba.sci.security.CORSFilter;
import com.saba.sci.security.CustomAuthenticationProvider;
import com.saba.sci.security.ProxyAuthenticationFilter;
import com.saba.sci.security.RESTAuthenticationFailureHandler;
import com.saba.sci.security.RESTAuthenticationSuccessHandler;
import com.saba.sci.security.RESTLogoutSuccessHandler;
import com.saba.sci.security.SecurityAuthenticationFilter;
import com.saba.sci.security.UserDetailsServiceImpl;
import com.saba.sci.utile.SabaPasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	private CustomAuthenticationProvider authProvider;
	private RESTAuthenticationFailureHandler authenticationFailureHandler;
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;
	private RESTLogoutSuccessHandler logoutSuccessHandler;
	private ServletContext servletContext;
	private SecurityAuthenticationFilter securityAuthenticationFilter;
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {

		auth.authenticationProvider(authProvider);
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	protected ProxyAuthenticationFilter proxyAuthenticationFilter() {
		return new ProxyAuthenticationFilter();
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.addFilterBefore(securityAuthenticationFilter, BasicAuthenticationFilter.class);
    	http.csrf().disable();
    
    	http.headers().frameOptions().sameOrigin().disable();
    	    	
    	http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.sessionFixation().migrateSession().maximumSessions(20)
		.sessionRegistry(sessionRegistry());
    	
        http
        	.httpBasic()
        	 .disable()
        	.authorizeRequests()
            .antMatchers("/", "/sci/registration", "/sci/login", "/sci/logout", "/sci/check").permitAll()
            .antMatchers("/sci/private/**").hasAuthority("ADMIN")
			.anyRequest().fullyAuthenticated();
            
        http.exceptionHandling().accessDeniedPage(
				AbstractBaseConstants.ACCESS_DENIED_PAGE);
        http.logout().logoutUrl("/sci/logout").invalidateHttpSession(true).permitAll(); 
          
        http.formLogin()
			.defaultSuccessUrl(
					servletContext.getContextPath()
							+ AbstractBaseConstants.ROUTE_DASHBOARD,true)
			.loginPage(AbstractBaseConstants.ROUTE_LOGIN_PAGE)
			.usernameParameter("userName")
			.passwordParameter("password")
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler)
			.and()
			.logout()
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID", "SESSION")
			.logoutUrl(AbstractBaseConstants.ROUTE_LOGOUT)
			.logoutSuccessHandler(logoutSuccessHandler)
			.and()
			.sessionManagement()
			.invalidSessionUrl(AbstractBaseConstants.ROUTE_ROOT)
			.sessionFixation().changeSessionId().maximumSessions(1)
			.expiredUrl(AbstractBaseConstants.ROUTE_ROOT);

	    http.rememberMe().key("unique-and-secret")
			.rememberMeCookieName("remember-me-cookie-name")
			.tokenValiditySeconds(24 * 60 * 60);
            
    }
	
    @Bean(name = "myAuthenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public SecurityContextRepository securityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}