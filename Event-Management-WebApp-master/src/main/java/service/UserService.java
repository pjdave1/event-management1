package service;

import dto.UserDto;
import entity.User;

public interface UserService {
    User registerNewUserAccount(UserDto userDto) throws Exception; // Customize the exception as needed
    User authenticateUser(String email, String password) throws Exception; // Customize the exception as needed
    boolean emailExists(String email);
}
