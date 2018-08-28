package pl.coderslab.domain;

public class User {
	  private long id;
	  private String username;
	  private String email;
	  private String password;
	  private int userGroupId;

	  public User(String username, String email, String password) {
	    this.username = username;
	    this.email = email;
	    setPassword(password);
	  }

	  public void setPassword(String password) {
	    this.password = password; 
//	    		BCrypt.hashpw(password, BCrypt.gensalt());
	  }

	  @Override
	  public String toString() {
	    return "User{"
	        + "id="
	        + id
	        + ", username='"
	        + username
	        + '\''
	        + ", email='"
	        + email
	        + '\''
	        + ", password='"
	        + password
	        + '\''
	        + ", userGroupId="
	        + userGroupId
	        + '}';
	  }

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getUserGroupId() {
		return userGroupId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserGroupId(int userGroupId) {
		this.userGroupId = userGroupId;
	}
	  
	 
}