
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Hike extends DomainEntity {

	// Attributes -------------------------------------------------------------------

	private String				name;
	private String				description;
	private Double				length;
	private String				originCity;
	private String				destinyCity;
	private String				difficulty;
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

	@NotBlank
	@SafeHtml
	public String getOriginCity() {
		return this.originCity;
	}

	public void setOriginCity(final String originCity) {
		this.originCity = originCity;
	}

	@NotBlank
	@SafeHtml
	public String getDestinyCity() {
		return this.destinyCity;
	}

	public void setDestinyCity(final String destinyCity) {
		this.destinyCity = destinyCity;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@Pattern(regexp = "^(EASY|MEDIUM|DIFFICULT)$")
	@NotBlank
	@SafeHtml
	public String getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(final String difficulty) {
		this.difficulty = difficulty;
	}


	//Relationships----------------------------------------------------------------

	private Collection<Comment>			comments;

	private Collection<Route>			routes;

	private Collection<Advertisement>	advertisements;


	@ManyToMany
	public Collection<Route> getRoutes() {
		return this.routes;
	}

	public void setRoutes(final Collection<Route> routes) {
		this.routes = routes;
	}

	@OneToMany(mappedBy = "hike")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(mappedBy = "hike", cascade = CascadeType.REMOVE)
	@Valid
	public Collection<Advertisement> getAdvertisements() {
		return this.advertisements;
	}
	public void setAdvertisements(final Collection<Advertisement> advertisements) {
		this.advertisements = advertisements;
	}

}
