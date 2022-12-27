package cl.ey.desafio.api.user.model;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {

	private String id;
	private String name;
	private String email;
	private boolean active;
	private String created;
	private String modified;
	private String lastLogin;
	private String token;	
	private List<Phone> phones;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public List<Phone> getPhones() {
		if(phones == null)
			phones =  new ArrayList<>();
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
    
	
	
}
