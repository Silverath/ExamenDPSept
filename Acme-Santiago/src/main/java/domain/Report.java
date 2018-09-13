
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	private Date	moment;
	private String	title;
	private String	ticker;
	private String	description;
	private Integer	gauge;
	private Boolean	approved;
	private Boolean	rejected;
	private Boolean	finalMode;
	private String	comment;


	@NotBlank
	@Length(min = 1, max = 250)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotNull
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public Boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Future
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public Boolean getApproved() {
		return this.approved;
	}

	public void setApproved(final Boolean approved) {
		this.approved = approved;
	}

	public Boolean getRejected() {
		return this.rejected;
	}

	public void setRejected(final Boolean rejected) {
		this.rejected = rejected;
	}

	@NotBlank
	@Length(min = 1, max = 250)
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotNull
	@Range(min = 1, max = 3)
	public Integer getGauge() {
		return this.gauge;
	}

	public void setGauge(final Integer gauge) {
		this.gauge = gauge;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@Length(min = 1, max = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	private User			user;
	private Route			route;
	private Administrator	administrator;


	//	private Follower	follower;

	@ManyToOne(optional = true)
	@Valid
	public Administrator getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(final Administrator administrator) {
		this.administrator = administrator;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@ManyToOne(optional = true)
	@Valid
	public Route getRoute() {
		return this.route;
	}

	public void setRoute(final Route route) {
		this.route = route;
	}

}
