package cl.ey.desafio.api.user.jpa.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import cl.ey.desafio.api.user.jpa.security.ConverterCrypto;

@Entity
@Table(name = "users")
public class UserEntity {
	
	 	@Id	   
	    @Column(columnDefinition = "VARCHAR(36)", unique = true)
	    private String id;
	    
	    @Column(name="NAME", length=50, nullable=false, unique=true)
	    private String name;

	    @NotEmpty
	    @Email
	    @Size(max = 255)
	    @Column(name="email", unique = true)
	    private String email;
	    
	    @Column(name="password", length=32, nullable=true, unique = false)	   
	    @Convert(converter = ConverterCrypto.class)
	    private String password;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="CREATIONDATE")
	    private Date creationDate;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date modificationDate;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="LASTLOGIN")
	    private Date lastLogin;
	    
	    @Column(columnDefinition = "VARCHAR(512)", unique = true)
	    private String token;
	    
	    @Column(columnDefinition = "boolean default false")
	    private Boolean active;

	    @OneToMany(mappedBy="user")
	    private Set<PhoneEntity> phones;
	    
	    public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name.toUpperCase(Locale.US);
		}

		public void setName(String name) {
			this.name = name.toUpperCase(Locale.US);
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

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		public Date getModificationDate() {
			return modificationDate;
		}

		public void setModificationDate(Date modificationDate) {
			this.modificationDate = modificationDate;
		}

		public Date getLastLogin() {
			return lastLogin;
		}

		public void setLastLogin(Date lastLogin) {
			this.lastLogin = lastLogin;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public Boolean getActive() {
			return active;
		}

		public void setActive(Boolean active) {
			this.active = active;
		}

		public Set<PhoneEntity> getPhones() {
			if(phones == null)
				phones = new HashSet<>();
			return phones;
		}

		public void setPhones(Set<PhoneEntity> phones) {
			this.phones = phones;
		}
	        
			    
}
