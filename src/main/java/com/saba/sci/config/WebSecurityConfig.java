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


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Autowired
	private CustomAuthenticationProvider authProvider;
	
	@Autowired
	private RESTAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private RESTAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private RESTLogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
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
		auth.inMemoryAuthentication().withUser("arash").password(passwordEncoder().encode("arash")).roles("ADMIN");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	protected ProxyAuthenticationFilter proxyAuthenticationFilter() {
		return new ProxyAuthenticationFilter();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

	
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//        auth.inMemoryAuthentication().withUser("arash").password(passwordEncoder().encode("arash")).roles("ADMIN");
//    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.addFilterBefore(securityAuthenticationFilter, BasicAuthenticationFilter.class);
//    	http.addFilterBefore(corsFilter, securityAuthenticationFilter.getClass());
    	http.csrf().disable();
    
    	http.headers().frameOptions().sameOrigin().disable();
    	http.headers().addHeaderWriter(
                new StaticHeadersWriter("Access-Control-Allow-Origin", "*"));
   
    	http.headers().addHeaderWriter(
                new StaticHeadersWriter("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With"));
    	
    	http.headers().addHeaderWriter(
                new StaticHeadersWriter("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD"));
    	
    	http.headers().addHeaderWriter(
                new StaticHeadersWriter("Access-Control-Allow-Credentials", "true"));
    	
    	
    	
    	http.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.sessionFixation().migrateSession().maximumSessions(20)
		.sessionRegistry(sessionRegistry());
    	
        http
        	.httpBasic()
        	 .disable()
        	.authorizeRequests()
            .antMatchers("/sci/registration", "/sci/login", "/sci/logout", "/sci/check", "/webjars/**").permitAll()
            .antMatchers("/sci/private/**").hasAuthority("ADMIN");
//            .antMatchers("/sci/private/**").hasRole("ADMIN"); 
            
        http.exceptionHandling().accessDeniedPage(
				AbstractBaseConstants.ACCESS_DENIED_PAGE);
        
//        http.formLogin()
//            .usernameParameter("userName").passwordParameter("password").permitAll()
//            .and()
//            .logout().logoutUrl("/sci/logout").invalidateHttpSession(true).permitAll(); 
          
        http.formLogin()
			// .loginProcessingUrl(
			// servletContext.getContextPath() + "dfsdfsdf" +
			// AbstractBaseConstants.ROUTE_LOGIN_REST)
			.defaultSuccessUrl(
					servletContext.getContextPath()
							+ AbstractBaseConstants.ROUTE_DASHBOARD,true)
			.loginPage(AbstractBaseConstants.ROUTE_LOGIN_PAGE)
			.usernameParameter("userName")
			.passwordParameter("password")
			.successHandler(authenticationSuccessHandler)
			// .failureUrl(BaseConstants.)
			.failureHandler(authenticationFailureHandler)
			.and()
			.logout()
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID", "SESSION")
			.logoutUrl(AbstractBaseConstants.ROUTE_LOGOUT)
			.logoutSuccessHandler(logoutSuccessHandler)
			// .logoutSuccessUrl(AbstractBaseConstants.ROUTE_HOME)
			.and().sessionManagement()
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
	
//	 @Bean
//	 public UserDetailsService userDetailsService() {
//	        return new UserDetailsServiceImpl();
//	}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
   

}

