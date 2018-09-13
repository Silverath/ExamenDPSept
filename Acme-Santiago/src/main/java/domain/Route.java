
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Route extends DomainEntity {

	// Attributes -------------------------------------------------------------------

	private String				name;
	private Double				length;
	private String				description;
	private Collection<String>	pictures;


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
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(0)
	@NotNull
	public Double getLength() {
		return this.length;
	}

	public void setLength(final Double length) {
		this.length = length;
	}

	@Column(length = 3000)
	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}


	//Relationships----------------------------------------------------------------

	private Collection<Hike>	hikes;
	private Collection<Comment>	comments;
	private User				user;


	@ManyToMany(mappedBy = "routes")
	public Collection<Hike> getHikes() {
		return this.hikes;
	}

	public void setHikes(final Collection<Hike> hikes) {
		this.hikes = hikes;
	}

	@OneToMany(mappedBy = "route")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
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

}
