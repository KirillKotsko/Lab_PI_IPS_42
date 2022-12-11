package ua.kotsko.project.Examinator.configs;

import java.util.Locale;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.userdetails.UserDetailsService;

//localhost:8189/examinator/

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/login", "/registration").permitAll();

		http.authorizeRequests().antMatchers("/", "/profile").hasAnyRole("STUDENT", "TEACHER", "ADMIN");
		
		http.authorizeRequests().antMatchers("/exam/**", "/teacher/**").hasAnyRole("TEACHER", "ADMIN");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		
		http.authorizeRequests().antMatchers("/student/**").hasRole("STUDENT");

		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		http.authorizeRequests().and().formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

		http.authorizeRequests().and() 
				.rememberMe().tokenRepository(this.persistentTokenRepository()) 
				.tokenValiditySeconds(1 * 24 * 60 * 60); 
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userService);
		return authenticationProvider;
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
	
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    messageSource.setDefaultLocale(Locale.US);
	    messageSource.setBasename("classpath:validation");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}

}
