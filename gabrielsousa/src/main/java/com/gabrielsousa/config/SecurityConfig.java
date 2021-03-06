package com.gabrielsousa.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gabrielsousa.security.JWTAuthenticationFilter;
import com.gabrielsousa.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTUtil jWTUtil;

	private static final String[] PUBLIC_MATCHERS = { 
			"/h2-console/**",
			"/products/**", 
			"/categories/**", 
			"/clients/**",
			"/requests/**",
			"/login/**"
	};

	private static final String[] PUBLIC_MATCHERS_GET = {
			"/products/**", 
			"/categories/**", 
			"/clients/**",
			"/requests/**" ,
			"/states/**"
	};

	private static final String[] PUBLIC_MATCHERS_POST = {
			"/clients/**",
			"/requests/**",
			"/auth/forgot/**"
	};
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     
    	if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
    		http.headers().frameOptions().disable();
    	}
    	http.cors().and().csrf().disable();
        http.authorizeRequests()
        		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS).permitAll()
        		.antMatchers(HttpMethod.GET,PUBLIC_MATCHERS_GET).permitAll()
        		.antMatchers(HttpMethod.POST,PUBLIC_MATCHERS_POST).permitAll()
                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
//                .hasAnyAuthority("Admin", "Editor", "Salesperson")
//                .hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .anyRequest().authenticated()
//                .and().formLogin()
//                .loginPage("/login")
//                    .usernameParameter("email")
//                    .permitAll()
//                .and()
//                .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
//                .and()
//                .logout().permitAll()
                ;
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(null), jWTUtil));
        //TODO-Aten????o JWT n??o est?? funcionando
//        http.addFilterBefore(new JWTAuthenticationFilter(jWTUtil), UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().sameOrigin();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        return http.build();
    }
    
	//TODO-forma nova
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
    }
	
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
 
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
    
    @Bean
    public WebMvcConfigurer corsConfigurer() 
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:8000");
            	registry.addMapping("/**").allowedOrigins("https://gabrielsousa-spring-ionic-back.herokuapp.com/");
            }
        };
    }
    
}
