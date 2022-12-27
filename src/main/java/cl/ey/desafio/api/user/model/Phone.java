package cl.ey.desafio.api.user.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;



public class Phone {

	

	private String number;

	@Size(min = 1, max = 3)
	@Pattern(regexp = "^[0-9]{1-3}$")
	private String citycode;
	
	@Size(min = 2, max = 3)
	@Pattern(regexp = "^[0-9]{2-3}$")
	private String contrycode;
	
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
}
