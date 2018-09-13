
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Comment extends DomainEntity {

	private Date				moment;
	private String				description;
	private Collection<String>	pictures;
	private Integer				rating;


	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@SafeHtml
	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@NotNull
	@Range(min = 0, max = 3)
	public Integer getRating() {
		return this.rating;
	}

	public void setRating(final Integer rating) {
		this.rating = rating;
	}


	// Relationships ----------------------------------------------------------

	private User	user;
	private Hike	hike;
	private Route	route;


	@ManyToOne(optional = true)
	@Valid
	public Hike getHike() {
		return this.hike;
	}

	public void setHike(final Hike hike) {
		this.hike = hike;
	}

	@ManyToOne(optional = true)
	@Valid
	public Route getRoute() {
		return this.route;
	}

	public void setRoute(final Route route) {
		this.route = route;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

}
