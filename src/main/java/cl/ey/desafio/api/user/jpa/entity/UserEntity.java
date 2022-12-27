package cl.ey.desafio.api.user.jpa.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cl.ey.desafio.api.user.jpa.security.CryptoConverter;

@Entity
@Table(name = "users")
public class UserEntity {
	
	 	@Id	   	   
	    private String id;
	    
	    @Column(name="NAME", length=50, nullable=false, unique=true)
	    private String name;
	    
	    @Column(name="email", length=256, nullable=false, unique=true)
	    private String email;
	    
	    
	    @Convert(converter = CryptoConverter.class)
	    @Column(name="password", length=32, nullable=false, unique=false)
	    private String password;
	   
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="created")
	    private Date created;
	    
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="modified")
	    private Date modified;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="last_login")
	    private Date last_login;
	    
	    @Column(name="token")
	    private String token;	    
	    
	    @OneToMany(mappedBy ="idUser")
	   // @JoinColumn(name = "user_d", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	    private Set<PhoneEntity> phones;

	    private boolean active;

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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Date getCreated() {
			return created;
		}

		public void setCreated(Date created) {
			this.created = created;
		}

		public Date getModified() {
			return modified;
		}

		public void setModified(Date modified) {
			this.modified = modified;
		}

		public Date getLast_login() {
			return last_login;
		}

		public void setLast_login(Date last_login) {
			this.last_login = last_login;
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

		public Set<PhoneEntity> getPhones() {
			if(phones == null)
				phones = new HashSet<>();
			return phones;
		}

		public void setPhones(Set<PhoneEntity> phones) {
			this.phones = phones;
		}

		
}
