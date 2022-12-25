package cl.ey.desafio.api.user.model;

import java.util.ArrayList;
import java.util.List;

public class UserResponse extends UserRequest{

	private String id;
	private String creationDate;
	private String modificationDate;
	private String lastLogin;
	private String token;
	private boolean active;
	private List<Phone> phones;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getModificationDate() {
		return modificationDate;
	}
	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Phone> getPhones() {
		if(phones == null)
		{
			phones = new ArrayList<>();
		}
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
		
	
}
