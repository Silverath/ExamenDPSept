
package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import security.Authority;

public class ActorAccountForm {

	private Integer		id;
	private String		username;
	private String		password;
	private Authority	authorities;
	private String		password2;

	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private String		address;
	private String		picture;
	private Boolean		terms;


	@SafeHtml
	@Size(min = 5, max = 32)
	public String getUsername() {
		return this.username;
	}
	public void setUsername(final String username) {
		this.username = username;
	}
	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}
	public void setPassword(final String password) {
		this.password = password;
	}
	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	@NotBlank
	@SafeHtml
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}
	@Email
	@NotBlank
	@SafeHtml
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	@Pattern(regexp = "^(\\+?\\d+)?$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	@SafeHtml
	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	public Authority getAuthorities() {
		return this.authorities;
	}
	public void setAuthorities(final Authority authorities) {
		this.authorities = authorities;
	}

	public Integer getId() {
		return this.id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}

	public String getPassword2() {
		return this.password2;
	}
	public void setPassword2(final String password2) {
		this.password2 = password2;
	}

	@SafeHtml
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public Boolean getTerms() {
		return this.terms;
	}

	public void setTerms(final Boolean terms) {
		this.terms = terms;
	}

}
