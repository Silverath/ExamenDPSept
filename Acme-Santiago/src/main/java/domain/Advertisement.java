
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Advertisement extends DomainEntity {

	// Attributes -------------------------------------------------------------------

	private String		title;
	private String		banner;
	private String		targetPage;
	private CreditCard	creditCard;
	private Integer		daysDisplayed;


	@SafeHtml
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@URL
	@SafeHtml
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@URL
	@SafeHtml
	public String getTargetPage() {
		return this.targetPage;
	}

	public void setTargetPage(final String targetPage) {
		this.targetPage = targetPage;
	}

	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Min(0)
	@NotNull
	public Integer getDaysDisplayed() {
		return this.daysDisplayed;
	}

	public void setDaysDisplayed(final Integer daysDisplayed) {
		this.daysDisplayed = daysDisplayed;
	}


	//Relationships----------------------------------------------------------------

	private Agent	agent;
	private Hike	hike;


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(final Agent agent) {
		this.agent = agent;
	}

	@ManyToOne(optional = true)
	@Valid
	public Hike getHike() {
		return this.hike;
	}

	public void setHike(final Hike hike) {
		this.hike = hike;
	}

}
