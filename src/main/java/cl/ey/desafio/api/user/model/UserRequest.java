package cl.ey.desafio.api.user.model;

import java.util.List;

import cl.ey.desafio.api.user.config.ValidPassword;

public class UserRequest {
	
	private String name;
	private String email;
	
	@ValidPassword
	private String password;
	private boolean active;
	private List<Phone> phones;
	
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	
}
