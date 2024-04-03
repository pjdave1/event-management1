package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.UserDto;
import entity.User;
import repository.UserRepository;
import service.UserService;
import utils.JwtUtil;

@RestController
@RequestMapping(value="/user")
public class UserController {
	   
		@Autowired
	    private UserService userService;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @Autowired
	    private AuthenticationManager authenticationManager;

	    @PostMapping("/signup")
	    public ResponseEntity<?> registerUserAccount(@RequestBody UserDto userDto) {
	        try {
	            if (userService.emailExists(userDto.getEmail())) {
	                return ResponseEntity.badRequest().body("Email already in use.");
	            }
	            userService.registerNewUserAccount(userDto);
	            return ResponseEntity.ok("User registered successfully");
	        } catch (Exception e) {
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
	    }

	    @PostMapping("/login")
	    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
	        try {
	            Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
	            );
	            // Generate JWT token after successful authentication
	            String jwt = jwtUtil.generateToken(authentication);
	            // Return the token
	            return ResponseEntity.ok(jwt);
	        } catch (AuthenticationException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
	        }
    }
}
	
