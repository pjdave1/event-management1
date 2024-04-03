package dto;

import Enum.UserRole;

public class LoginResponse {
	private String jwt;
    private UserRole role;

    public LoginResponse(String jwt, UserRole role) {
        this.jwt = jwt;
        this.role = role;
    }

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
