package Enum;

public enum UserRole {
	 	SUPER_ADMIN(1),
	    ADMIN(2),
	    CLIENT(3);

	    private final int value;

	    UserRole(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }

	    
	    public static UserRole valueOf(int value) {
	        for (UserRole role : UserRole.values()) {
	            if (role.getValue() == value) {
	                return role;
	            }
	        }
	        throw new IllegalArgumentException("Invalid UserRole value: " + value);
	    }
}
