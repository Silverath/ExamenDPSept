
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Inn extends DomainEntity {

	// Attributes -------------------------------------------------------------------

	private String		name;
	private String		badge;
	private String		address;
	private String		phone;
	private String		email;
	private String		website;
	private CreditCard	creditCard;


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
	public String getBadge() {
		return this.badge;
	}

	public void setBadge(final String badge) {
		this.badge = badge;
	}

	@NotBlank
	@SafeHtml
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	@SafeHtml
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@NotBlank
	@SafeHtml
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}
	@NotBlank
	@SafeHtml
	public String getWebsite() {
		return this.website;
	}

	public void setWebsite(final String website) {
		this.website = website;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}


	//Relationships----------------------------------------------------------------

	private Innkeeper	innkeeper;


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Innkeeper getInnkeeper() {
		return this.innkeeper;
	}

	public void setInnkeeper(final Innkeeper innkeeper) {
		this.innkeeper = innkeeper;
	}

}
