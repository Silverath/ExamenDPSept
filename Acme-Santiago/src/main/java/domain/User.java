
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	private Boolean	isBanned;
	private Boolean	isSuspicious;


	public User() {
		super();
	}

	public Boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final Boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------

	private Collection<Route>		routes;
	private Collection<Comment>		comments;
	private Collection<Chirp>		chirps;
	private Collection<Follower>	follower;	//Seguidores que tienen los usuarios
	private Collection<Follower>	follow;	//Personas que siguen los usuarios


	@Valid
	@OneToMany(mappedBy = "user")
	public Collection<Route> getRoutes() {
		return this.routes;
	}

	public void setRoutes(final Collection<Route> routes) {
		this.routes = routes;
	}

	@Valid
	@OneToMany(mappedBy = "user")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	@Valid
	@OneToMany(mappedBy = "user")
	public Collection<Chirp> getChirps() {
		return this.chirps;
	}

	public void setChirps(final Collection<Chirp> chirps) {
		this.chirps = chirps;
	}

	@OneToMany(mappedBy = "follow")
	public Collection<Follower> getFollower() {
		return this.follower;
	}

	public void setFollower(final Collection<Follower> follower) {
		this.follower = follower;
	}

	@OneToMany(mappedBy = "followed")
	public Collection<Follower> getFollow() {
		return this.follow;
	}

	public void setFollow(final Collection<Follower> follow) {
		this.follow = follow;
	}

}
