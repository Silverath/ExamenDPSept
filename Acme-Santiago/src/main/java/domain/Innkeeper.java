
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Innkeeper extends Actor {

	// Constructors -----------------------------------------------------------

	public Innkeeper() {
		super();
	}


	// Attributes -------------------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Inn>	inns;


	@OneToMany(mappedBy = "innkeeper")
	public Collection<Inn> getInns() {
		return this.inns;
	}

	public void setInns(final Collection<Inn> inns) {
		this.inns = inns;
	}
}
