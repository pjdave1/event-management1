package security;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import entity.User;
import repository.UserRepository;
import Enum.UserRole;

public class SecurityConfig {
	    @Bean
	     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    	http 
	    	 .csrf(csrf -> csrf
	    		      .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	    		      .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())) 
	    	.authorizeHttpRequests(authz -> authz
	    	.requestMatchers("/user/signup").permitAll()
	    	.requestMatchers("/user/signup").hasAnyAuthority(UserRole.ADMIN.name())
	    	.anyRequest().authenticated())
	    	.sessionManagement(session ->
	    	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); 
	    	
	    	return http.build(); }
		 
	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	        return authenticationConfiguration.getAuthenticationManager();
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	    @Bean
	    public UserDetailsService userDetailsService(UserRepository userRepository) {
	        return email -> {
	            User user = userRepository.findByEmail(email);
	            if (user != null) {
	                // Since there's only one role, no need to stream and map
	                List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
	                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	            } else {
	                throw new UsernameNotFoundException("User not found with email: " + email);
	            }
	        };
	    // ... (Other beans like UserDetailsService and JwtUtil)
	}
}	    
