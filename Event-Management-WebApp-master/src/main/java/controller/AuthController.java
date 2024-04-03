package controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Enum.UserRole;
import dto.LoginRequest;
import dto.LoginResponse;
import entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import repository.UserRepository;

@RestController
public class AuthController {
		
	  @Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private UserRepository userRepository;

	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    	System.out.println("api called");
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getEmail(),
	                        loginRequest.getPassword()
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String secretKey = "qwuieutuwyutewqyuteyu1233456"; // This should be secured and externalized

	        // JWT Token Generation
	        long expirationTime = 864_000_000; // 10 days in milliseconds
	        String jwt = Jwts.builder()
	                .setSubject(loginRequest.getEmail())
	                .setIssuedAt(new Date(expirationTime))
	                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
	                .signWith(SignatureAlgorithm.HS512, secretKey)
	                .compact();

	        User user = userRepository.findByEmail(loginRequest.getEmail())
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	        //UserRole role = UserRole.fromInt(user.getRole());
	        UserRole role = user.getRole();
	        
	        
	        return ResponseEntity.ok(new LoginResponse(jwt, role));
	    }
	}