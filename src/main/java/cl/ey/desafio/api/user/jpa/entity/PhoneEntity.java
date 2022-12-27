package cl.ey.desafio.api.user.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "phones")
public class PhoneEntity {

	@Id	 
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
	
	@Pattern(regexp ="^([0-9]{7}$)|([0-9]{8}$)|([0-9]{9}$)", message = "Number debe ser un número de entre 7 y 9 dígitos")			
	private String number;
	
	@Pattern(regexp ="^([0-9]{1}$)|([0-9]{2}$)", message = "citycode es un numero de 1 y 2 dígitos")
	private String citycode;
	
	@Pattern(regexp ="^([0-9]{2}$)|([0-9]{3}$)$", message = "countrycode es un numero de 2 y 3 dígitos")
	private String contrycode;
	
	@ManyToOne
	private UserEntity idUser;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	public String getContrycode() {
		return contrycode;
	}
	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserEntity getIdUser() {
		return idUser;
	}
	public void setIdUser(UserEntity idUser) {
		this.idUser = idUser;
	}
	
	
	
}
