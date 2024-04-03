package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import Enum.UserRole;
import dto.UserDto;
import entity.User;
import repository.UserRepository;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUserAccount(UserDto userDto) throws Exception {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new Exception("There is an account with that email address: " + userDto.getEmail());
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setContactNo(userDto.getContactNo());
        user.setAge(userDto.getAge());
        user.setStatus(userDto.getStatus());
        user.setRole(UserRole.valueOf(userDto.getRole()));
        
        return userRepository.save(user);
    }
    
    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    
    @Override
    public User authenticateUser(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new Exception("Authentication failed.");
    }
}